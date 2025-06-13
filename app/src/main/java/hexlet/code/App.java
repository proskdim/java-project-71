package hexlet.code;

import picocli.CommandLine;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "gendiff",
        description = "Compares two configuration files and shows a difference.",
        sortOptions = false,
        version = "gendiff 0.0.1"
)

public final class App implements Callable<Integer> {
    @CommandLine.Option(
            names = { "-f", "--format" },
            description = "output format [default: ${DEFAULT-VALUE}]",
            defaultValue = "stylish",
            paramLabel = "format"
    )
    private String format;

    @CommandLine.Option(
            names = { "-h", "--help" },
            description = "Show this help message and exit.",
            usageHelp = true
    )
    private boolean help;

    @CommandLine.Option(
            names = { "-V", "--version" },
            description = "Print version information and exit.",
            versionHelp = true
    )
    private boolean version;

    @CommandLine.Parameters(
            index = "0",
            description = "path to first file",
            paramLabel = "filePath1"
    )
    private String filePath1;

    @CommandLine.Parameters(
            index = "1",
            description = "path to second file",
            paramLabel = "filePath2"
    )
    private String filePath2;

    @Override
    public Integer call() throws Exception {
        var result = Differ.generate(filePath1, filePath2);
        System.out.println(result);
        return 0;
    }

    public static void main(String[] args) {
        new CommandLine(new App()).execute(args);
    }
}
