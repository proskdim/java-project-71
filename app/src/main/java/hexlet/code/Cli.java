package hexlet.code;

import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.util.concurrent.Callable;

@Command(
        name = "gendiff",
        description = "Compares two configuration files and shows a difference.",
        sortOptions = false,
        version = "gendiff 0.0.1"
)

public final class Cli implements Callable<Integer> {
    @Option(
            names = { "-f", "--format" },
            description = "output format [default: ${DEFAULT-VALUE}]",
            defaultValue = "stylish",
            paramLabel = "format"
    )
    private String format;

    @Option(
            names = { "-h", "--help" },
            description = "Show this help message and exit.",
            usageHelp = true
    )
    private boolean help;

    @Option(
            names = { "-V", "--version" },
            description = "Print version information and exit.",
            versionHelp = true
    )
    private boolean version;

    @Parameters(
            index = "0",
            description = "path to first file",
            paramLabel = "filePath1"
    )
    private String filePath1;

    @Parameters(
            index = "1",
            description = "path to second file",
            paramLabel = "filePath2"
    )
    private String filePath2;

    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
