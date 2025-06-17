import hexlet.code.App;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AppTest {
    public static final String FILE1 = "src/test/resources/deep/file1_d.json";
    public static final String FILE2 = "src/test/resources/deep/file2_d.json";

    public static final String DEEP_OUTPUT_PATH = "src/test/resources/deep/deep_output.txt";
    public static final String PLAIN_OUTPUT_PATH = "src/test/resources/plain/plain_output.txt";
    public static final String JSON_OUTPUT_PATH = "src/test/resources/json/json_output.txt";

    @Test
    @DisplayName("Test App picocli call -h option")
    public void testAppShowOptionHelp() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outContent));

        try {
            App.main(new String[]{"-h"});

            String expected = """
                    Usage: gendiff [-hV] [-f=format] filePath1 filePath2
                    Compares two configuration files and shows a difference.
                          filePath1         path to first file
                          filePath2         path to second file
                      -f, --format=format   output format [default: stylish]
                      -h, --help            Show this help message and exit.
                      -V, --version         Print version information and exit.
                    """.trim();

            assert expected.equals(outContent.toString().trim());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("Test App picocli call -F option stylish")
    public void testAppShowOptionFormatStylish() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outContent));

        try {
            App.main(new String[]{"-f", "stylish", FILE1, FILE2});

            String expected = getOutputPath(DEEP_OUTPUT_PATH);

            assert expected.equals(outContent.toString().trim());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("Test App picocli call -F option plain")
    public void testAppShowOptionFormatPlain() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outContent));

        try {
            App.main(new String[]{"-f", "plain", FILE1, FILE2});

            String expected = getOutputPath(PLAIN_OUTPUT_PATH);

            assert expected.equals(outContent.toString().trim());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("Test App picocli call -F option json")
    public void testAppShowOptionFormatJson() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outContent));

        try {
            App.main(new String[]{"-f", "json", FILE1, FILE2});

            String expected = getOutputPath(JSON_OUTPUT_PATH);

            assert expected.equals(outContent.toString().trim());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.setOut(originalOut);
        }
    }

    private String getOutputPath(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}
