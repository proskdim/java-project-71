package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileParser {
    public static JsonNode parse(String filePath) throws IOException {
        Path normalizedPath = Path.of(filePath).normalize().toAbsolutePath();
        String content = Files.readString(normalizedPath);
        ObjectMapper mapper = new JsonMapper();

        return mapper.readTree(content);
    }
}
