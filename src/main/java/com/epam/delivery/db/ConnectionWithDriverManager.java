package com.epam.delivery.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static final Logger logger = LogManager.getLogger();

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
            logger.error("Problem with read file. " + exception.getMessage());
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
            logger.error("some problem with creating DB: " + sqlException.getMessage());
        } finally {
            builder.closeConnection(con);
        }
    }
}
