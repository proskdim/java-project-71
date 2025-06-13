package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public final class Differ {
    public static String generate(String filePath1, String filePath2) throws IOException {
        try {
            JsonNode node1 = Utils.parse(filePath1);
            JsonNode node2 = Utils.parse(filePath2);

            var children = getDiffChildren(null, node1, node2);
            var diff = DiffItem.builder().state("OBJECT").children(children).build();

            return format(diff, 0);
        } catch (IOException e) {
            throw new IOException("Failed to parse file: " + e.getMessage(), e);
        }
    }

    private static DiffItem.Builder createDiffItem(String fieldName, JsonNode node1, JsonNode node2) {
        JsonNode oldValue = node1.get(fieldName);
        JsonNode newValue = node2.get(fieldName);

        DiffItem.Builder diffItem = DiffItem.builder().fieldName(fieldName);

        if (!node1.has(fieldName)) {
            return diffItem.state("ADDED").newValue(newValue);
        }

        if (!node2.has(fieldName)) {
            return diffItem.state("REMOVED").oldValue(oldValue);
        }

        if (bothAreObjects(oldValue, newValue)) {
            var children = getDiffChildren(fieldName, oldValue, newValue);
            return diffItem.state("OBJECT").children(children);
        }

        if (oldValue.equals(newValue)) {
            return diffItem.state("UNCHANGED").oldValue(oldValue);
        }

        return diffItem.state("CHANGED").oldValue(oldValue).newValue(newValue);
    }

    private static List<DiffItem> getDiffChildren(String fieldName, JsonNode node1, JsonNode node2) {
        TreeSet<String> keys = new TreeSet<>();

        node1.fieldNames().forEachRemaining(keys::add);
        node2.fieldNames().forEachRemaining(keys::add);

        return keys.stream()
                .map(key -> createDiffItem(key, node1, node2).build())
                .toList();
    }

    private static String format(DiffItem diff, int depth) {
        final String gap = " ";
        final int indentCount = 2;
        final String indent = gap.repeat(depth * indentCount);

        return switch (diff.getState()) {
            case "ADDED" -> formatAdded(diff, indent);
            case "REMOVED" -> formatRemoved(diff, indent);
            case "CHANGED" -> formatChanged(diff, indent);
            case "UNCHANGED" -> formatUnchanged(diff, indent);
            case "OBJECT" -> formatObject(diff, indent, depth);
            default -> throw new IllegalArgumentException("Invalid state: " + diff.getState());
        };
    }

    private static String formatAdded(DiffItem diff, String indent) {
        var newValue = diff.getNewValue().asText();
        return indent + "+ " + diff.getFieldName() + ": " + newValue;
    }

    private static String formatRemoved(DiffItem diff, String indent) {
        var oldValue = diff.getOldValue().asText();
        return indent + "- " + diff.getFieldName() + ": " + oldValue;
    }

    private static String formatChanged(DiffItem diff, String indent) {
        var oldValue = diff.getOldValue().asText();
        var newValue = diff.getNewValue().asText();
        return indent + "- " + diff.getFieldName() + ": " + oldValue + "\n"
                + indent + "+ " + diff.getFieldName() + ": " + newValue;
    }

    private static String formatUnchanged(DiffItem diff, String indent) {
        var oldValue = diff.getOldValue().asText();
        return indent + "  " + diff.getFieldName() + ": " + oldValue;
    }

    private static String formatObject(DiffItem diff, String indent, int depth) {
        var sb = new StringBuilder();
        var fieldName = diff.getFieldName();

        sb.append(indent);
        if (fieldName != null) {
            sb.append(fieldName).append(": ");
        }
        sb.append("{\n");

        var nestedObjectFields = diff.getChildren().stream()
                .map(childDiff -> format(childDiff, depth + 1))
                .collect(Collectors.joining("\n"));

        sb.append(nestedObjectFields);
        sb.append("\n").append(indent).append("}");
        return sb.toString();
    }

    private static boolean bothAreObjects(JsonNode val1, JsonNode val2) {
        return val1.isObject() && val2.isObject();
    }
}
