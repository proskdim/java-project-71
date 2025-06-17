package hexlet.code;

import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

public final class Formatter {
    public static String format(DiffItem diff, String format) {
        var formatter = switch (format) {
            case "plain" -> new Plain();
            default -> new Stylish();
        };

        return formatter.format(diff);
    }
}
