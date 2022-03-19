package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionWithDriverManager;
import com.epam.delivery.entities.User;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

class UserDaoTest {

    private static final String URL_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static String userDefinedAppContent;
    private static final String APP_PROPS_FILE = "app.properties";
    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS delivery.`order`;\n" +
                    "DROP TABLE IF EXISTS delivery.`description_locality`;\n" +
                    "DROP TABLE IF EXISTS delivery.`locality`;" +
                    "DROP TABLE IF EXISTS delivery.`shipping_status_description`;" +
                    "DROP TABLE IF EXISTS delivery.`shipping_status`;" +
                    "DROP TABLE IF EXISTS delivery.`client`;" +
                    "DROP TABLE IF EXISTS delivery.`admin`;" +
                    "DROP TABLE IF EXISTS delivery.`user`;" +
                    "DROP TABLE IF EXISTS delivery.`role`;" +
                    "DROP TABLE IF EXISTS delivery.`language`;" +
                    "DROP SCHEMA IF EXISTS `delivery`;";

    private static ConnectionBuilder builder;

    private static UserDao dao;

    @BeforeAll
    static void globalSetUp() throws IOException {
        Properties properties = new Properties();
        InputStream in = Files.newInputStream(Paths.get(APP_PROPS_FILE));
        properties.load(in);
        userDefinedAppContent = properties.getProperty("connection.url");

        OutputStream output = new FileOutputStream(APP_PROPS_FILE);
        properties.setProperty("connection.url", URL_CONNECTION);
        properties.store(output, null);

    }

    @AfterAll
    static void globalTearDown() throws IOException {
        Properties properties = new Properties();
        OutputStream output = new FileOutputStream(APP_PROPS_FILE);
        properties.setProperty("connection.url",userDefinedAppContent);
        properties.store(output, null);
    }

    @BeforeEach
    void setUp() {
        builder = new ConnectionWithDriverManager();
        dao = new UserDao(builder);
        ConnectionWithDriverManager.createDataBase();
    }

    @AfterEach
    void tearDown() throws SQLException {
        builder.getConnection().createStatement().executeUpdate(DROP_TABLE);
    }


    @Test
    void insert() {
        User user = User.createUser("test1", "pass123", 0);
        Assertions.assertTrue(dao.insert(user));
        Assertions.assertEquals(3, user.getId());
    }

    @Test
    void update() {
        User user = dao.findById(1L).orElse(null);
        user.setLogin("test");
        Assertions.assertTrue(dao.update(user));
        User userAfterUpdate = dao.findById(1L).orElse(null);
        Assertions.assertEquals("test", userAfterUpdate.getLogin());
    }

    @Test
    void findById() {
        User user = dao.findById(1L).orElse(null);
        Assertions.assertNotNull(user);
        Assertions.assertEquals("dimakuian", user.getLogin());
    }

    @Test
    void existsById() {

        Assertions.assertTrue(dao.existsById(1L));
        Assertions.assertFalse(dao.existsById(133L));
    }

    @Test
    void findAll() {
        ArrayList<User> users = (ArrayList<User>) dao.findAll();
        Assertions.assertFalse(users.isEmpty());
        Assertions.assertEquals(2, users.size());
        Assertions.assertEquals("dimakuian", users.get(0).getLogin());
        Assertions.assertEquals(1, users.get(0).getId());
    }

    @Test
    void deleteById() {
        ArrayList<User> users = (ArrayList<User>) dao.findAll();
        Assertions.assertTrue(dao.deleteById(2L));
        ArrayList<User> afterDelete = (ArrayList<User>) dao.findAll();
        Assertions.assertEquals(Collections.singletonList(users.get(0)), afterDelete);
    }

    @Test
    void getByLogin() {
        User user = dao.getByLogin("admin").orElse(null);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(2,user.getId());
    }

    @Test
    void existsByLogin() {
        Assertions.assertTrue(dao.existsByLogin("admin"));
        Assertions.assertFalse(dao.existsByLogin("test"));
    }
}