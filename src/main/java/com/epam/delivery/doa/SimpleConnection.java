package com.epam.delivery.doa;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SimpleConnection {
    private static final String CONNECTION_URL = "jdbc:mysql://127.0.0.1:3306/delivery";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static Connection connection;

    private SimpleConnection() {
    }

    public static synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            }
        } catch (SQLException exception) {
            System.err.println("some problem with connection: " + exception);
        }
        return connection;
    }

    public static void createDataBase() {
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(Paths.get("sql/db-create.sql"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        String str = new String(bytes, StandardCharsets.UTF_8);
        String[] sqlCommand = str.trim().split(";");
        try (Connection con = getConnection()) {
            Statement stat = con.createStatement();
            for (String command : sqlCommand) {
                stat.addBatch(command);
            }
            stat.executeBatch();
            if (stat != null || !stat.isClosed()) stat.close();
        } catch (SQLException sqlException) {
            System.out.println("some problem with creating DB: " + sqlException.getMessage());
            sqlException.printStackTrace();
        }
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


