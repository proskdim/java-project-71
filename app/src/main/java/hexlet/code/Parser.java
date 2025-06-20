package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.nio.file.Files;
import java.nio.file.Path;

public final class Parser {
    public static JsonNode parse(Path path) throws Exception {
        String content = Files.readString(path);
        String fileName = path.toString();

        final int index = fileName.lastIndexOf('.');
        String extension = (index > 0) ? fileName.substring(index) : "";

        switch (extension) {
            case ".json":
                return new ObjectMapper().readTree(content);
            case ".yml":
                return new YAMLMapper().readTree(content);
            default:
                throw new RuntimeException("Unknown file format: " + extension);
        }
    }

    public static JsonNode parse(String filePath) throws Exception {
        return parse(FileUtils.getFullPath(filePath));
    }
}
