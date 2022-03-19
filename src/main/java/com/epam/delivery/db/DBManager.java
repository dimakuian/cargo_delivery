package com.epam.delivery.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    // //////////////////////////////////////////////////////////
    // singleton
    // //////////////////////////////////////////////////////////

    private static final String CONNECTION_URL = "jdbc:mysql://127.0.0.1:3306/delivery";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static DBManager instance;

    public static synchronized DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();
        return instance;
    }

    /**
     * Returns a DB connection from the Pool Connections. Before using this
     * method you must configure the Date Source and the Connections Pool in your
     * WEB_APP_ROOT/META-INF/context.xml file.
     *
     * @return A DB connection.
     */
    public Connection getConnection(){
        Connection con = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");

            // ST4DB - the name of data source
            DataSource ds = (DataSource) envContext.lookup("jdbc/delivery");
            con = ds.getConnection();
        } catch (NamingException | SQLException ex) {
            System.out.println("Cannot obtain a connection from the pool");
        }
        return con;
    }

    private DBManager() {
    }

    // //////////////////////////////////////////////////////////
    // DB util methods
    // //////////////////////////////////////////////////////////

    /**
     * Returns a DB connection. This method is just for an example how to use the
     * DriverManager to obtain a DB connection. It does not use a pool
     * connections and not used in this project. It is preferable to use
     * {@link #getConnection()} method instead.
     *
     * @return A DB connection.
     */
    public Connection getConnectionWithDriverManager() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
        } catch (SQLException exception) {
            System.err.println("some problem with connection: " + exception);
        }
        return connection;
    }

    /**************************************************************/

    public static void createDataBase() {
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(Paths.get("sql/db-create.sql"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        String str = new String(bytes, StandardCharsets.UTF_8);
        String[] sqlCommand = str.trim().split(";");
        try (Connection con = DBManager.getInstance().getConnectionWithDriverManager()) {
            try (Statement stat = con.createStatement()) {
                for (String command : sqlCommand) {
                    stat.addBatch(command);
                }
                stat.executeBatch();
            }
        } catch (SQLException sqlException) {
            System.out.println("some problem with creating DB: " + sqlException.getMessage());
            sqlException.printStackTrace();
        }
    }

}
