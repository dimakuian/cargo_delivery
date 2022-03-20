package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionWithDriverManager;
import com.epam.delivery.entities.Locality;
import com.epam.delivery.entities.Order;
import org.junit.jupiter.api.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class OrderDaoTest {

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

    private static OrderDao dao;

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
        dao = new OrderDao(builder);
        ConnectionWithDriverManager.createDataBase();
    }

    @AfterEach
    void tearDown() throws SQLException {
        builder.getConnection().createStatement().executeUpdate(DROP_TABLE);
    }

    @Test
    void insert() {
        LocalityDao localityDao = new LocalityDao(builder);
        Locality locality1 = localityDao.findById(1L).orElse(null);
        Locality locality2 = localityDao.findById(2L).orElse(null);
        Double distance = localityDao.calcDistanceBetweenTwoLocality(locality1, locality2).orElse(null);
        Order order = Order.createOrder();
        Order.Builder builder = order.new Builder(order);
        builder.withShippingAddress(1L)
                .withDeliveryAddress(2L)
                .withCreationTimestamp(new Timestamp(System.currentTimeMillis()))
                .withClient(1L)
                .withConsignee("Consignee")
                .withDescription("description")
                .withDistance(distance)
                .withLength(12)
                .withHeight(10)
                .withWidth(2)
                .withWeight(3)
                .withVolume(10 * 12 * 2)
                .withFare(100)
                .withShippingStatus(1L);
        order = builder.build();
        assertTrue(dao.insert(order));
        assertEquals(2,order.getId());
    }

    @Test
    void update() {
        Order order = dao.findById(1L).orElse(null);
        order.setStatusID(2);
        assertTrue(dao.update(order));
        Order afterUpdate = dao.findById(1L).orElse(null);
        assertEquals(2,afterUpdate.getStatusID());
    }

    @Test
    void findById() {
        Order order = dao.findById(1L).orElse(null);
        assertNotNull(order);
        assertEquals(1,order.getShippingAddressID());
        assertEquals(2,order.getDeliveryAddressID());
        assertEquals(1,order.getClientID());
        Order order2 = dao.findById(2L).orElse(null);
        assertNull(order2);
    }

    @Test
    void existsById() {
        assertTrue(dao.existsById(1L));
        assertFalse(dao.existsById(2L));
        assertFalse(dao.existsById(20L));
    }

    @Test
    void findAll() {
        ArrayList<Order> orders = (ArrayList<Order>) dao.findAll();
        assertFalse(orders.isEmpty());
        assertEquals(1,orders.size());
        assertEquals(1,orders.get(0).getShippingAddressID());
        assertEquals(2,orders.get(0).getDeliveryAddressID());
        assertEquals(1,orders.get(0).getClientID());
    }

    @Test
    void deleteById() {
        assertTrue(dao.deleteById(1L));
        ArrayList<Order> orders = (ArrayList<Order>) dao.findAll();
        assertTrue(orders.isEmpty());
    }

    @Test
    void findAllByUserID() {
        ArrayList<Order> orders = (ArrayList<Order>) dao.findAllByUserID(1L);
        assertFalse(orders.isEmpty());
        assertEquals(1,orders.size());
        assertEquals(1,orders.get(0).getShippingAddressID());
        assertEquals(2,orders.get(0).getDeliveryAddressID());
        assertEquals(1,orders.get(0).getClientID());
        ArrayList<Order> orders2 = (ArrayList<Order>) dao.findAllByUserID(2L);
        assertTrue(orders2.isEmpty());
    }
}