package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionWithDriverManager;
import com.epam.delivery.db.entities.Entity;
import com.epam.delivery.db.entities.ShippingStatus;
import com.epam.delivery.db.entities.bean.StatusDescriptionBean;
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
import java.util.Comparator;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ShippingStatusDaoTest {

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

    private static ShippingStatusDao dao;

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
        dao = new ShippingStatusDao(builder);
        ConnectionWithDriverManager.createDataBase();
    }

    @AfterEach
    void tearDown() throws SQLException {
        builder.getConnection().createStatement().executeUpdate(DROP_TABLE);
    }


    @Test
    void insert() {
        ShippingStatus test = ShippingStatus.createShippingStatus("test");
        ShippingStatus paid = ShippingStatus.createShippingStatus("paid");
        assertTrue(dao.insert(test));
        assertEquals(8, test.getId());
        assertFalse(dao.insert(paid));
    }

    @Test
    void update() {
        ShippingStatus status = dao.findById(1L).orElse(null);
        status.setName("test");
        assertTrue(dao.update(status));
        ShippingStatus afterUpdate = dao.findById(1L).orElse(null);
        assertEquals("test", afterUpdate.getName());
    }

    @Test
    void findById() {
        ShippingStatus status1 = dao.findById(1L).orElse(null);
        assertNotNull(status1);
        assertEquals(1, status1.getId());
        assertEquals("created", status1.getName());
        ShippingStatus status2 = dao.findById(2L).orElse(null);
        assertNotNull(status2);
        assertEquals(2, status2.getId());
        assertEquals("paid", status2.getName());
        ShippingStatus status3 = dao.findById(8L).orElse(null);
        assertNull(status3);
    }

    @Test
    void existsById() {
        assertTrue(dao.existsById(1L));
        assertTrue(dao.existsById(2L));
        assertTrue(dao.existsById(3L));
        assertTrue(dao.existsById(4L));
        assertTrue(dao.existsById(5L));
        assertTrue(dao.existsById(6L));
        assertFalse(dao.existsById(10L));
        assertFalse(dao.existsById(11L));
        assertFalse(dao.existsById(12L));
        assertFalse(dao.existsById(13L));
    }

    @Test
    void findAll() {
        ArrayList<ShippingStatus> statuses = (ArrayList<ShippingStatus>) dao.findAll();
        statuses.sort(Comparator.comparingLong(Entity::getId));
        assertFalse(statuses.isEmpty());
        assertEquals(7, statuses.size());
        assertEquals("paid", statuses.get(1).getName());
    }

    @Test
    void deleteById() {
        ArrayList<ShippingStatus> statuses = (ArrayList<ShippingStatus>) dao.findAll();
        statuses.sort(Comparator.comparingLong(Entity::getId));
        assertTrue(dao.deleteById(2L));
        assertTrue(dao.deleteById(3L));
        assertTrue(dao.deleteById(5L));
        assertFalse(dao.deleteById(10L));
        ArrayList<ShippingStatus> afterDelete = (ArrayList<ShippingStatus>) dao.findAll();
        afterDelete.sort(Comparator.comparingLong(Entity::getId));
        assertEquals(Arrays.asList(statuses.get(0), statuses.get(3), statuses.get(5), statuses.get(6)), afterDelete);
    }

    @Test
    void findTranslateByStatusId() {
        StatusDescriptionBean description1 = dao.findTranslateByStatusId(3L).orElse(null);
        assertNotNull(description1);
        assertEquals("confirmed", description1.getDescription().get("en"));
        assertEquals("підтверджений", description1.getDescription().get("ua"));
        StatusDescriptionBean description2 = dao.findTranslateByStatusId(5L).orElse(null);
        assertNotNull(description2);
        assertEquals("in the way", description2.getDescription().get("en"));
        assertEquals("в дорозі", description2.getDescription().get("ua"));
        StatusDescriptionBean description3 = dao.findTranslateByStatusId(10L).orElse(null);
        assertNull(description3);
    }
}