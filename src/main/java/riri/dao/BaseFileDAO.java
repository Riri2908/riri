package riri.dao;

import riri.util.AppPath;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class BaseFileDAO {

    protected final Path file;

    public BaseFileDAO(String fileName) {
        this.file = AppPath.dataFile(fileName);

        try {
            if (Files.notExists(file)) {
                createFromTemplate(fileName);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to init data file: " + fileName, e);
        }
    }

    private void createFromTemplate(String fileName) throws IOException {

        Files.createDirectories(file.getParent());

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("database/" + fileName)) {

            if (is == null) {
                Files.createFile(file);
                return;
            }

            Files.copy(is, file, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    protected void backup() {
        try {
            String time = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            Path backupFile = AppPath.BACKUP.resolve(
                    file.getFileName() + "_" + time + ".bak"
            );

            Files.copy(file, backupFile, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new RuntimeException("Backup failed", e);
        }
    }
}