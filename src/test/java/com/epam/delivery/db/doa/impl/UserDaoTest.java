package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionWithDriverManager;
import com.epam.delivery.db.entities.Role;
import com.epam.delivery.db.entities.User;
import com.epam.delivery.service.PasswordEncoder;
import org.junit.jupiter.api.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private static final String URL_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static String userDefinedAppContent;
    private static final String APP_PROPS_FILE = "app.properties";
    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS delivery.`invoice`;" +
                    "DROP TABLE IF EXISTS delivery.`invoice_status`;" +
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
        properties.setProperty("connection.url", userDefinedAppContent);
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
        User user = new User();
        user.setLogin("test1");
        user.setPassword("pass123");
        user.setRoleID(0);
        assertTrue(dao.insert(user));
        assertEquals(8, user.getId());
    }

    @Test
    void update() {
        User user = dao.findById(1L).orElse(null);
        user.setLogin("test");
        assertTrue(dao.update(user));
        User userAfterUpdate = dao.findById(1L).orElse(null);
        assertEquals("test", userAfterUpdate.getLogin());
    }

    @Test
    void findById() {
        User user = dao.findById(1L).orElse(null);
        assertNotNull(user);
        assertEquals("user1", user.getLogin());
    }

    @Test
    void existsById() {
        assertTrue(dao.existsById(1L));
        assertFalse(dao.existsById(133L));
    }

    @Test
    void findAll() {
        ArrayList<User> users = (ArrayList<User>) dao.findAll();
        assertFalse(users.isEmpty());
        assertEquals(7, users.size());
        assertEquals("user1", users.get(0).getLogin());
        assertEquals(1, users.get(0).getId());
    }

    @Test
    void deleteById() {
        ArrayList<User> users = (ArrayList<User>) dao.findAll();
        assertTrue(dao.deleteById(2L));
        ArrayList<User> afterDelete = (ArrayList<User>) dao.findAll();
        assertEquals(Arrays.asList(users.get(0), users.get(2), users.get(3), users.get(4), users.get(5), users.get(6)), afterDelete);
    }

    @Test
    void getByLogin() {
        User user = dao.getByLogin("admin1").orElse(null);
        assertNotNull(user);
        assertEquals(5, user.getId());
    }

    @Test
    void existsByLogin() {
        assertTrue(dao.existsByLogin("admin1"));
        assertFalse(dao.existsByLogin("test"));
    }
}