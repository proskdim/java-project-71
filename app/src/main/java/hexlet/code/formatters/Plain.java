package hexlet.code.formatters;

import com.fasterxml.jackson.databind.JsonNode;
import hexlet.code.DiffItem;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public final class Plain implements BaseFormatter {
    private static final String DELIMITER = "\n";
    private static final String ADDED_MESSAGE = "Property '%s' was added with value: %s";
    private static final String REMOVED_MESSAGE = "Property '%s' was removed";
    private static final String CHANGED_MESSAGE = "Property '%s' was updated. From %s to %s";

    @Override
    public String format(DiffItem item) {
        return item.getChildren().stream()
                .map(this::formatDiffItem)
                .filter(message -> !message.isBlank())
                .collect(Collectors.joining(DELIMITER));
    }

    private String formatDiffItem(DiffItem item) {
        var result = new StringJoiner(DELIMITER);

        switch (item.getState()) {
            case "ADDED" -> {
                var newValue = nodeToString(item.getNewValue());
                var message = ADDED_MESSAGE.formatted(item.getFieldName(), newValue);
                result.add(message);
            }
            case "REMOVED" -> {
                var message = REMOVED_MESSAGE.formatted(item.getFieldName());
                result.add(message);
            }
            case "CHANGED" -> {
                var oldValue = nodeToString(item.getOldValue());
                var newValue = nodeToString(item.getNewValue());
                var message = CHANGED_MESSAGE.formatted(item.getFieldName(), oldValue, newValue);
                result.add(message);
            }
            case "OBJECT" -> {
                var messages = format(item);
                result.add(messages);
            }
            default -> {
                // no action needed for "UNCHANGED" state
            }
        }

        return result.toString();
    }

    private static String nodeToString(JsonNode node) {
        return switch (node.getNodeType()) {
            case OBJECT, ARRAY -> "[complex value]";
            case STRING -> "'" + node.asText() + "'";
            default -> node.asText();
        };
    }
}