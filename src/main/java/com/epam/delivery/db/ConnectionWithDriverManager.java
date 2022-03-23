package com.epam.delivery.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionWithDriverManager implements ConnectionBuilder {

    public ConnectionWithDriverManager() {
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("app.properties"))) {
            props.load(in);
            String url = props.getProperty("connection.url");
            connection = DriverManager.getConnection(url);
        } catch (IOException exception) {
            System.err.println("Cannot find file.");
        } catch (SQLException exception) {
            System.err.println("some problem with connection: " + exception);
        }
        return connection;
    }

    public static void createDataBase() {
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(Paths.get("sql/test_db-create.sql"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        ConnectionBuilder builder = new ConnectionWithDriverManager();
        Connection con = null;
        try {
            con = builder.getConnection();
            if (bytes != null) {
                String str = new String(bytes, StandardCharsets.UTF_8);
                String[] sqlCommand = str.trim().split(";");
                try (Statement stat = con.createStatement()) {
                    for (String command : sqlCommand) {
                        stat.addBatch(command);
                    }
                    stat.executeBatch();
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("some problem with creating DB: " + sqlException.getMessage());
            sqlException.printStackTrace();
        } finally {
            builder.closeConnection(con);
        }
    }
}
