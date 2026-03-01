package riri.dao;

import riri.util.AppPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class BaseFileDAO {

    protected final Path file;

    public BaseFileDAO(String fileName) {
        this.file = AppPath.dataFile(fileName);

        try {
            if (Files.notExists(file)) {
                Files.createFile(file);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create data file: " + fileName, e);
        }
    }

    protected void backup() {
        try {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            Path backupFile = AppPath.BACKUP.resolve(
                    file.getFileName() + "_" + time + ".bak"
            );

            Files.copy(file, backupFile, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new RuntimeException("Backup failed", e);
        }
    }
}
