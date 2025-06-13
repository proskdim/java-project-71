package hexlet.code;

import java.util.stream.Collectors;

public final class Formatter {
    private static final String GAP = " ";
    private static final Integer TABSize = 2;

    public static String format(DiffItem diff, int level) {
        final String indent = GAP.repeat(level * TABSize);

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

    private static String formatObject(DiffItem diff, String indent, int level) {
        StringBuilder sb = new StringBuilder();
        String fieldName = diff.getFieldName();

        sb.append(indent);
        if (fieldName != null) {
            sb.append(fieldName).append(": ");
        }
        sb.append("{\n");

        var nestedObjectFields = diff.getChildren().stream()
                .map(childDiff -> format(childDiff, level + 1))
                .collect(Collectors.joining("\n"));

        sb.append(nestedObjectFields);
        sb.append("\n").append(indent).append("}");
        return sb.toString();
    }
}
