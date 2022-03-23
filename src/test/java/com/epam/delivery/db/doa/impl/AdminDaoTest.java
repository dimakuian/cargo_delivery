package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionWithDriverManager;
import com.epam.delivery.entities.Admin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class AdminDaoTest {
    static final Logger logger = LogManager.getLogger();
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

    private static AdminDao dao;

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
        dao = new AdminDao(builder);
        ConnectionWithDriverManager.createDataBase();
    }

    @AfterEach
    void tearDown() throws SQLException {
        builder.getConnection().createStatement().executeUpdate(DROP_TABLE);
    }

    @Test
    void insert() {
        Admin admin = Admin.createAdmin(5, "Test", "Surname");
        assertTrue(dao.insert(admin));
        assertEquals(3, admin.getId());
    }

    @Test
    void update() {
        Admin admin = dao.findById(1L).orElse(null);
        assertEquals("John", admin.getName());
        admin.setName("Test");
        assertTrue(dao.update(admin));
        Admin afterUpdate = dao.findById(1L).orElse(null);
        assertEquals("Test", afterUpdate.getName());
    }

    @Test
    void findById() {
        Admin admin = dao.findById(1L).orElse(null);
        assertNotNull(admin);
        assertEquals("John", admin.getName());
        assertEquals("Jonson", admin.getSurname());
        assertEquals(6, admin.getUserID());
        Admin admin1 = dao.findById(9L).orElse(null);
        assertNull(admin1);
    }

    @Test
    void existsById() {
        assertTrue(dao.existsById(1L));
        assertTrue(dao.existsById(2L));
        assertFalse(dao.existsById(3L));
        assertFalse(dao.existsById(10L));
    }

    @Test
    void findAll() {
        ArrayList<Admin> admins = (ArrayList<Admin>) dao.findAll();
        assertFalse(admins.isEmpty());
        assertEquals(2, admins.size());
        assertEquals("Anatolij", admins.get(1).getName());
        assertEquals(6, admins.get(0).getUserID());
    }

    @Test
    void deleteById() {
        ArrayList<Admin> admins = (ArrayList<Admin>) dao.findAll();
        assertTrue(dao.deleteById(2L));
        ArrayList<Admin> afterDelete = (ArrayList<Admin>) dao.findAll();
        assertEquals(1, afterDelete.size());
        assertEquals(Collections.singletonList(admins.get(0)), afterDelete);
    }

    @Test
    void getByUserId() {
        Admin admin = dao.getByUserId(6L).orElse(null);
        assertNotNull(admin);
        assertEquals(1, admin.getId());
        assertEquals("John", admin.getName());
        Admin admin1 = dao.getByUserId(5L).orElse(null);
        assertNull(admin1);
    }
}