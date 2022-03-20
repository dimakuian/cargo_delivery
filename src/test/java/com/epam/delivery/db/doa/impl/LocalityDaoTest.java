package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionWithDriverManager;
import com.epam.delivery.entities.Entity;
import com.epam.delivery.entities.Locality;
import org.junit.jupiter.api.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class LocalityDaoTest {

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

    private static LocalityDao dao;

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
        dao = new LocalityDao(builder);
        ConnectionWithDriverManager.createDataBase();
    }

    @AfterEach
    void tearDown() throws SQLException {
        builder.getConnection().createStatement().executeUpdate(DROP_TABLE);
    }

    @Test
    void insert() {
        Locality test = Locality.createLocality("Test", 48.444585453958084, 22.724035901090634);
        assertTrue(dao.insert(test));
        assertEquals(17, test.getId());
        assertFalse(dao.insert(test));
    }

    @Test
    void update() {
        Locality locality = dao.findById(12L).orElse(null);
        locality.setName("Test department");
        assertTrue(dao.update(locality));
        Locality afterUpdate = dao.findById(12L).orElse(null);
        assertEquals("Test department", afterUpdate.getName());
    }

    @Test
    void findById() {
        Locality locality1 = dao.findById(1L).orElse(null);
        assertNotNull(locality1);
        assertEquals("Kiev department №2", locality1.getName());
        Locality locality2 = dao.findById(12L).orElse(null);
        assertNotNull(locality2);
        assertEquals("Dnipro department №12", locality2.getName());
        Locality locality3 = dao.findById(20L).orElse(null);
        assertNull(locality3);
    }

    @Test
    void existsById() {
        assertTrue(dao.existsById(5L));
        assertTrue(dao.existsById(15L));
        assertTrue(dao.existsById(10L));
        assertTrue(dao.existsById(3L));
        assertFalse(dao.existsById(30L));
        assertFalse(dao.existsById(20L));
        assertFalse(dao.existsById(18L));
    }

    @Test
    void findAll() {
        ArrayList<Locality> localities = (ArrayList<Locality>) dao.findAll();
        assertFalse(localities.isEmpty());
        assertEquals(16,localities.size());
        assertEquals("Dnipro department №12", localities.get(11).getName());
        assertEquals("Kiev department №2", localities.get(0).getName());
    }

    @Test
    void deleteById() {
        assertTrue(dao.deleteById(1L));
        assertTrue(dao.deleteById(3L));
        assertTrue(dao.deleteById(5L));
        assertTrue(dao.deleteById(6L));
        assertTrue(dao.deleteById(7L));
        assertTrue(dao.deleteById(11L));
        assertTrue(dao.deleteById(12L));
        assertTrue(dao.deleteById(10L));
        assertFalse(dao.deleteById(17L));
        assertFalse(dao.deleteById(10L));
        assertFalse(dao.deleteById(1L));
        ArrayList<Locality> afterDelete = (ArrayList<Locality>) dao.findAll();
        assertEquals(8,afterDelete.size());
    }

    @Test
    void calcDistanceBetweenTwoLocality() {
        Locality locality1 = dao.findById(1L).orElse(null);
        Locality locality2 = dao.findById(11L).orElse(null);
        Double dist1 = dao.calcDistanceBetweenTwoLocality(locality1, locality2).orElse(null);
        assertNotNull(dist1);
        double expected = 164.0;
        assertEquals(expected,dist1);
    }
}