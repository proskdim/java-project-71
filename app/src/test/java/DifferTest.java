import hexlet.code.Differ;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class DifferTest {
    private static final String FILE1_JSON_PATH = "src/test/resources/file1.json";
    private static final String FILE2_JSON_PATH = "src/test/resources/file2.json";

    private static final String FILE1_YML_PATH = "src/test/resources/file1.yml";
    private static final String FILE2_YML_PATH = "src/test/resources/file2.yml";

    private static final String FLAT_OUTPUT_PATH = "src/test/resources/flat_output.txt";

    @Test
    @DisplayName("Test diff generate with valid JSON files")
    void testGenerateWithValidFiles() throws IOException {
        List<List<String>> files = List.of(
                List.of(FILE1_JSON_PATH, FILE2_JSON_PATH),
                List.of(FILE1_YML_PATH, FILE2_YML_PATH)
        );

        for (List<String> filePair : files) {
            String result = Differ.generate(filePair.get(0), filePair.get(1));
            assert (result).equals(getFlatOutput());
        }
    }

    @Test
    @DisplayName("Test diff generate with non-existent-file")
    void testGenerateWithNonExistentFile() {
        assertThrows(IOException.class, () -> {
            Differ.generate("non-existent-file.json", FILE2_JSON_PATH);
        });
    }

    @Test
    @DisplayName("Test diff generate with invalid JSON file")
    void testGenerateWithInvalidJSONFile() throws IOException {
        String invalidFilePath = "src/test/resources/invalid.json";
        Files.write(Paths.get(invalidFilePath), "{invalid: json}".getBytes());

        assertThrows(IOException.class, () -> {
            Differ.generate(invalidFilePath, FILE2_JSON_PATH);
        });

        Files.deleteIfExists(Paths.get(invalidFilePath));
    }

    @Test
    @DisplayName("Test diff generate with identical files")
    void testGenerateWithIdenticalFiles() throws IOException {
        String result = Differ.generate(FILE1_JSON_PATH, FILE1_JSON_PATH);
        assertFalse(result.equals(getFlatOutput()));
    }

    private String getFlatOutput() throws IOException {
        return Files.readString(Paths.get(FLAT_OUTPUT_PATH));
    }
}
