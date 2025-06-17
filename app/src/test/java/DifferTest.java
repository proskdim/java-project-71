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
    // deep
    private static final String FILE1_DEEP_JSON_PATH = "src/test/resources/deep/file1_d.json";
    private static final String FILE2_DEEP_JSON_PATH = "src/test/resources/deep/file2_d.json";
    private static final String FILE1_DEEP_YML_PATH = "src/test/resources/deep/file1_d.yml";
    private static final String FILE2_DEEP_YML_PATH = "src/test/resources/deep/file2_d.yml";

    // flat
    private static final String FILE1_FLAT_JSON_PATH = "src/test/resources/flat/file1_f.json";
    private static final String FILE2_FLAT_JSON_PATH = "src/test/resources/flat/file2_f.json";
    private static final String FILE1_FLAT_YML_PATH = "src/test/resources/flat/file1_f.yml";
    private static final String FILE2_FLAT_YML_PATH = "src/test/resources/flat/file2_f.yml";

    // result
    private static final String FLAT_OUTPUT_PATH = "src/test/resources/flat/flat_output.txt";
    private static final String DEEP_OUTPUT_PATH = "src/test/resources/deep/deep_output.txt";

    private static final String FORMAT = "stylish";

    @Test
    @DisplayName("Test diff generate with deep valid JSON files")
    void testGenerateWithValidFlatFiles() throws IOException {
        List<List<String>> files = List.of(
                List.of(FILE1_FLAT_JSON_PATH, FILE2_FLAT_JSON_PATH),
                List.of(FILE1_FLAT_YML_PATH, FILE2_FLAT_YML_PATH)
        );

        for (List<String> filePair : files) {
            String result = Differ.generate(filePair.get(0), filePair.get(1), FORMAT);
            System.out.println(result);
            assert (result).equals(getFlatOutput());
        }
    }

    @Test
    @DisplayName("Test diff generate with deep valid JSON files")
    void testGenerateWithValidDeepFiles() throws IOException {
        List<List<String>> files = List.of(
                List.of(FILE1_DEEP_JSON_PATH, FILE2_DEEP_JSON_PATH),
                List.of(FILE1_DEEP_YML_PATH, FILE2_DEEP_YML_PATH)
        );

        for (List<String> filePair : files) {
            String result = Differ.generate(filePair.get(0), filePair.get(1), FORMAT);
            System.out.println(result);
            assert (result).equals(getDeepOutput());
        }
    }

    @Test
    @DisplayName("Test diff generate with non-existent-file")
    void testGenerateWithNonExistentFile() {
        assertThrows(IOException.class, () -> {
            Differ.generate("non-existent-file.json", FILE2_FLAT_JSON_PATH, FORMAT);
        });
    }

    @Test
    @DisplayName("Test diff generate with invalid JSON file")
    void testGenerateWithInvalidJSONFile() throws IOException {
        String invalidFilePath = "src/test/resources/invalid.json";
        Files.write(Paths.get(invalidFilePath), "{invalid: json}".getBytes());

        assertThrows(IOException.class, () -> {
            Differ.generate(invalidFilePath, FILE2_FLAT_JSON_PATH, FORMAT);
        });

        Files.deleteIfExists(Paths.get(invalidFilePath));
    }

    @Test
    @DisplayName("Test diff generate with identical files")
    void testGenerateWithIdenticalFiles() throws IOException {
        String result = Differ.generate(FILE1_FLAT_JSON_PATH, FILE1_FLAT_JSON_PATH, FORMAT);
        assertFalse(result.equals(getFlatOutput()));
    }

    private String getFlatOutput() throws IOException {
        return Files.readString(Paths.get(FLAT_OUTPUT_PATH));
    }

    private String getDeepOutput() throws IOException {
        return Files.readString(Paths.get(DEEP_OUTPUT_PATH));
    }
}
