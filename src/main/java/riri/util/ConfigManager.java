package riri.util;

import java.io.*;
import java.util.Properties;

public class ConfigManager {

    private static final File CONFIG_FILE =
            AppPath.configFile("app.properties").toFile();

    private static final Properties props = new Properties();

    static {
        try {
            if (!CONFIG_FILE.exists()) {
                CONFIG_FILE.createNewFile();
            }

            try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
                props.load(fis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public static void set(String key, String value) {
        props.setProperty(key, value);
        save();
    }

    private static void save() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            props.store(fos, "Riri Book Store Config");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
