package riri.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class AppLogger {

    private static final Path LOG_FILE =
            AppPath.logFile("app.log");

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void log(String message) {
        String logLine = FORMATTER.format(LocalDateTime.now())
                + " - " + message;

        try {
            Files.write(
                    LOG_FILE,
                    List.of(logLine),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to write log", e);
        }
    }

    private AppLogger() {}
}
