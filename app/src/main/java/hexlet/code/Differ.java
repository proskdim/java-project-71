package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;

public final class Differ {
    public static String generate(String filePath1, String filePath2, String format) throws Exception {
        JsonNode node1 = Parser.parse(filePath1);
        JsonNode node2 = Parser.parse(filePath2);

        DiffItem diff = DiffCreator.create(node1, node2);
        return Formatter.format(diff, format);
    }
}
