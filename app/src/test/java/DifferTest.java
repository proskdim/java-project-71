import hexlet.code.Differ;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class DifferTest {
    private static final String FILE1_PATH = "src/test/resources/file1.json";
    private static final String FILE2_PATH = "src/test/resources/file2.json";
    private static final String FLAT_OUTPUT_PATH  = "src/test/resources/flat_output.txt";

    @Test
    @DisplayName("Test diff generate with valid JSON files")
    void testGenerateWithValidFiles() throws IOException {
        String result = Differ.generate(FILE1_PATH, FILE2_PATH);
        assert(result).equals(getFlatOutput());
    }

    @Test
    @DisplayName("Test diff generate with non-existent-file")
    void testGenerateWithNonExistentFile() {
        assertThrows(IOException.class, () -> {
            Differ.generate("non-existent-file.json", FILE2_PATH);
        });
    }

    @Test
    @DisplayName("Test generate with invalid JSON file")
    void testGenerateWithInvalidJSONFile() throws IOException {
        String invalidFilePath = "src/test/resources/invalid.json";
        Files.write(Paths.get(invalidFilePath), "{invalid: json}".getBytes());

        assertThrows(IOException.class, () -> {
            Differ.generate(invalidFilePath, FILE2_PATH);
        });

        Files.deleteIfExists(Paths.get(invalidFilePath));
    }

    @Test
    @DisplayName("Test generate with identical files")
    void testGenerateWithIdenticalFiles() throws IOException {
        String result = Differ.generate(FILE1_PATH, FILE1_PATH);
        assertFalse(result.equals(getFlatOutput()));
    }

     private String getFlatOutput() throws IOException {
        return Files.readString(Paths.get(FLAT_OUTPUT_PATH));
    }
}
