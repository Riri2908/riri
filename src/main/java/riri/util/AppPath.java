package riri.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class AppPath {

    private static final String APP_NAME = "BookStore";

    public static final Path ROOT;
    public static final Path DATA;
    public static final Path CONFIG;
    public static final Path LOGS;
    public static final Path BACKUP;

    static {
        String base = System.getenv("APPDATA");

        if (base == null) {
            base = System.getProperty("user.home");
        }

        ROOT = Path.of(base, APP_NAME);
        DATA = ROOT.resolve("data");
        CONFIG = ROOT.resolve("config");
        LOGS = ROOT.resolve("logs");
        BACKUP = ROOT.resolve("backup");

        createDirectories();
    }

    private static void createDirectories() {
        try {
            Files.createDirectories(DATA);
            Files.createDirectories(CONFIG);
            Files.createDirectories(LOGS);
            Files.createDirectories(BACKUP);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create app directories", e);
        }
    }

    private AppPath() {
    }

    public static Path dataFile(String fileName) {
        return DATA.resolve(fileName);
    }

    public static Path configFile(String fileName) {
        return CONFIG.resolve(fileName);
    }

    public static Path logFile(String fileName) {
        return LOGS.resolve(fileName);
    }
}
