package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

public final class Differ {
    public static String generate(String filePath1, String filePath2) throws IOException {
        try {
            JsonNode node1 = FileUtils.parse(filePath1);
            JsonNode node2 = FileUtils.parse(filePath2);

            DiffItem diff = createDiffTree(node1, node2);
            return Formatter.format(diff, 0);
        } catch (IOException e) {
            throw new IOException("Failed to parse file: " + e.getMessage(), e);
        }
    }

    private static DiffItem createDiffTree(JsonNode node1, JsonNode node2) {
        List<DiffItem> children = getDiffChildren(null, node1, node2);
        return DiffItem.builder().state("OBJECT").children(children).build();
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

        if (oldValue.isObject() && newValue.isObject()) {
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
}
