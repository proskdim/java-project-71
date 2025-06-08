package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class Differ {
    public static void generate(String filePath1, String filePath2) throws IOException {
        JsonNode node1 = FileParser.parse(filePath1);
        JsonNode node2 = FileParser.parse(filePath2);

        System.out.println(node1);
        System.out.println(node2);
    }
}
