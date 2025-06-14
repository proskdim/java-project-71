package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileUtils {
    public static JsonNode parse(Path path) throws IOException {
        String content = Files.readString(path);

        if (path.toString().endsWith(".json")) {
            return new ObjectMapper().readTree(content);
        } else if (path.toString().endsWith(".yml")) {
            return new YAMLMapper().readTree(content);
        }

        throw new RuntimeException("Unknown file format");
    }

    public static JsonNode parse(String filePath) throws IOException {
        return parse(FileUtils.getFullPath(filePath));
    }

    public static Path getFullPath(String filePath) {
        return Path.of(filePath).toAbsolutePath().normalize();
    }
}
