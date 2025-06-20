package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.TreeSet;

public final class DiffCreator {
    public static DiffItem create(JsonNode node1, JsonNode node2) {
        List<DiffItem> children = getDiffChildren(node1, node2);

        return DiffItem.builder()
                .state("OBJECT")
                .children(children)
                .build();
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
            var children = getDiffChildren(oldValue, newValue);
            return diffItem.state("OBJECT").children(children);
        }

        if (oldValue.equals(newValue)) {
            return diffItem.state("UNCHANGED").oldValue(oldValue);
        }

        return diffItem.state("CHANGED").oldValue(oldValue).newValue(newValue);
    }

    private static List<DiffItem> getDiffChildren(JsonNode node1, JsonNode node2) {
        TreeSet<String> keys = new TreeSet<>();

        node1.fieldNames().forEachRemaining(keys::add);
        node2.fieldNames().forEachRemaining(keys::add);

        return keys.stream()
                .map(key -> createDiffItem(key, node1, node2).build())
                .toList();
    }
}
