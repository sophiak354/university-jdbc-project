package com.solvd.university.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionPool {
    private static ConnectionPool instance;
    private final BlockingQueue<Connection> availableConnections;
    private final List<Connection> allConnections;

    private ConnectionPool() {
        int poolSize = DatabaseConfig.getPoolSize();
        this.availableConnections = new ArrayBlockingQueue<>(poolSize);
        this.allConnections = new ArrayList<>(poolSize);

        try {
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(
                        DatabaseConfig.getUrl(),
                        DatabaseConfig.getUsername(),
                        DatabaseConfig.getPassword()
                );
                availableConnections.put(connection);
                allConnections.add(connection);
            }
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException("Failed to initialize connection pool.", e);
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return availableConnections.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while waiting for a DB connection.", e);
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            if (!connection.isClosed()) {
                availableConnections.put(connection);
            }
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException("Failed to release DB connection back to the pool.", e);
        }
    }

    public void shutDown() {
        for (Connection connection : allConnections) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Failed to close DB connection.", e);
            }
        }
    }
}
