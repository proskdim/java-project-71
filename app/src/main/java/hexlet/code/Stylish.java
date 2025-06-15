package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public final class Stylish {
    private static final String GAP = " ";
    private static final int TAB_SIZE = 2;

    public static String format(DiffItem diff, int level) {
        final String indent = GAP.repeat(level * TAB_SIZE);

        return switch (diff.getState()) {
            case "ADDED" -> formatAdded(diff, indent);
            case "REMOVED" -> formatRemoved(diff, indent);
            case "CHANGED" -> formatChanged(diff, indent);
            case "UNCHANGED" -> formatUnchanged(diff, indent);
            case "OBJECT" -> formatObject(diff, indent, level);
            default -> throw new IllegalArgumentException("Invalid state: " + diff.getState());
        };
    }

    private static String formatAdded(DiffItem diff, String indent) {
        var newValue = formatNodeText(diff.getNewValue());
        return indent + "+ " + diff.getFieldName() + ": " + newValue;
    }

    private static String formatRemoved(DiffItem diff, String indent) {
        var oldValue = formatNodeText(diff.getOldValue());
        return indent + "- " + diff.getFieldName() + ": " + oldValue;
    }

    private static String formatChanged(DiffItem diff, String indent) {
        var oldValue = formatNodeText(diff.getOldValue());
        var newValue = formatNodeText(diff.getNewValue());
        return indent + "- " + diff.getFieldName() + ": " + oldValue + "\n"
                + indent + "+ " + diff.getFieldName() + ": " + newValue;
    }

    private static String formatUnchanged(DiffItem diff, String indent) {
        var oldValue = formatNodeText(diff.getOldValue());
        return indent + "  " + diff.getFieldName() + ": " + oldValue;
    }

    private static String formatObject(DiffItem diff, String indent, int level) {
        StringBuilder result = new StringBuilder();
        String fieldName = diff.getFieldName();

        result.append(indent);
        if (fieldName != null && !fieldName.isEmpty()) {
            result.append(fieldName).append(": ");
        }
        result.append("{\n");

        List<DiffItem> children = diff.getChildren();

        if (!children.isEmpty()) {
            String newIndent = indent + "  ";
            String nestedFields = children.stream()
                    .map(child -> format(child, level + 1))
                    .collect(Collectors.joining("\n"));
            result.append(nestedFields).append("\n");
        }

        result.append(indent).append("}");
        return result.toString();
    }

    private static String formatNodeText(JsonNode node) {
        if (node == null) {
            return "null";
        }
        if (node.isArray()) {
            return formatArray(node);
        } else if (node.isObject()) {
            return formatObject(node);
        } else if (node.isTextual()) {
            return node.asText();
        } else {
            return node.toString();
        }
    }

    private static String formatArray(JsonNode arrayNode) {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        arrayNode.elements().forEachRemaining(element -> joiner.add(formatNodeText(element)));
        return joiner.toString();
    }

    private static String formatObject(JsonNode objectNode) {
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        objectNode.fields().forEachRemaining(field ->
                joiner.add(field.getKey() + "=" + formatNodeText(field.getValue()))
        );
        return joiner.toString();
    }
}
