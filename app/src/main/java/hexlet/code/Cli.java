package hexlet.code;

import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import java.util.concurrent.Callable;

@Command(
        name = "gendiff",
        description = "Compares two configuration files and shows a difference.",
        sortOptions = false,
        version = "gendiff 0.0.1"
)

public final class Cli implements Callable<Integer> {
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

    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
