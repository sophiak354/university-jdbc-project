package com.solvd.university.config;

import java.io.IOException;
import java.util.Properties;

public final class DatabaseConfig {
    private static final String PROPERTIES_FILE = "db.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
        loadDriver();
    }

    private DatabaseConfig() {

    }

    private static void loadProperties() {
        try (var inputStream = DatabaseConfig.class
                .getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE)) {
            if (inputStream == null) {
                throw new RuntimeException("File "
                        + PROPERTIES_FILE
                        + " was not found in resources."
                );
            }
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database configuration.", e);
        }
    }

    private static void loadDriver() {
        String driver = getRequired("db.driver");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load database driver: " + driver, e);
        }
    }

    public static String getRequired(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing required property: " + key);
        }
        return value;
    }

    public static int getInt(String key) {
        return Integer.parseInt(getRequired(key));
    }

    public static String getUrl() {
        return getRequired("db.url");
    }

    public static String getUsername() {
        return getRequired("db.username");
    }

    public static String getPassword() {
        return getRequired("db.password");
    }

    public static int getPoolSize() {
        return getInt("db.pool.size");
    }
}
