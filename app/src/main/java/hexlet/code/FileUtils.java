package hexlet.code;

import java.nio.file.Path;

public final class FileUtils {
    public static Path getFullPath(String filePath) {
        return Path.of(filePath).toAbsolutePath().normalize();
    }
}
