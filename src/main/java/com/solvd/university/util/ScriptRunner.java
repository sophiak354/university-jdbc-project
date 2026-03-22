package com.solvd.university.util;

import com.solvd.university.config.ConnectionPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class ScriptRunner {
    private ScriptRunner() {

    }

    public static void runScripts(String... resourcePaths) {
        for (String resourcePath : resourcePaths) {
            runScript(resourcePath);
        }
    }

    public static void runScript(String resourcePath) {
        String scriptContent = readScript(resourcePath);
        String[] statements = scriptContent.split(";");

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (Statement statement = connection.createStatement()) {
            for (String sql : statements) {
                String trimmedSql = sql.trim();

                if (trimmedSql.isEmpty()) {
                    continue;
                }
                statement.execute(trimmedSql);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute SQL script: " + resourcePath, e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    private static String readScript(String resourcePath) {
        InputStream inputStream = ScriptRunner.class
                .getClassLoader()
                .getResourceAsStream(resourcePath);

        if (inputStream == null) {
            throw new RuntimeException("SQL script not found: " + resourcePath);
        }

        StringBuilder scriptBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();

                if (trimmedLine.isEmpty() || trimmedLine.startsWith("--")) {
                    continue;
                }
                scriptBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read SQL script: " + resourcePath, e);
        }
        return scriptBuilder.toString();
    }
}
