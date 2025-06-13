package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileParser {
    public static JsonNode parse(Path path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(Files.readString(path));
    }

    public static JsonNode parse(String filePath) throws IOException {
        return parse(Utils.getFullPath(filePath));
    }
}
