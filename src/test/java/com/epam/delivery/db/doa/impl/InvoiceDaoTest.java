package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionWithDriverManager;
import com.epam.delivery.db.entities.Invoice;
import com.epam.delivery.db.entities.Order;
import com.epam.delivery.db.entities.User;
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

class InvoiceDaoTest {

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

    private static InvoiceDao dao;

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
        dao = new InvoiceDao(builder);
        ConnectionWithDriverManager.createDataBase();
    }

    @AfterEach
    void tearDown() throws SQLException {
        builder.getConnection().createStatement().executeUpdate(DROP_TABLE);
    }


    @Test
    void insert() {
        Invoice invoice = new Invoice(1, new Timestamp(System.currentTimeMillis()), 2L, 80, 1);
        assertTrue(dao.insert(invoice));
        assertEquals(2, invoice.getId());

    }

    @Test
    void update() {
        Invoice invoice = dao.findById(1L).orElse(null);
        assertNotNull(invoice);
        invoice.setClientID(2L);
        assertTrue(dao.update(invoice));
        Invoice afterUpdate = dao.findById(1L).orElse(null);
        assertEquals(2L,afterUpdate.getClientID());

    }

    @Test
    void findById() {
        Invoice invoice1 = dao.findById(1L).orElse(null);
        assertNotNull(invoice1);
        assertEquals(30.0,invoice1.getSum());
        Invoice invoice2 = dao.findById(2L).orElse(null);
        assertNull(invoice2);
    }

    @Test
    void existsById() {
        assertTrue(dao.existsById(1L));
        assertFalse(dao.existsById(13L));
    }

    @Test
    void findAll() {
        ArrayList<Invoice> invoices = new ArrayList<>(dao.findAll());
        assertFalse(invoices.isEmpty());
        assertEquals(1, invoices.size());
        assertEquals(30.0, invoices.get(0).getSum());
        assertEquals(1, invoices.get(0).getId());
        assertEquals(1, invoices.get(0).getOrderID());
    }

    @Test
    void deleteById() {
        assertTrue(dao.deleteById(1L));
        assertFalse(dao.deleteById(2L));
        ArrayList<Invoice> invoices = new ArrayList<>(dao.findAll());
        assertTrue(invoices.isEmpty());
    }

    @Test
    void findClientInvoices() {
        ArrayList<Invoice> invoices1 = new ArrayList<>(dao.findClientInvoices(1L));
        assertFalse(invoices1.isEmpty());
        assertEquals(1, invoices1.size());
        assertEquals(30.0, invoices1.get(0).getSum());
        assertEquals(1, invoices1.get(0).getId());
        assertEquals(1, invoices1.get(0).getOrderID());
        ArrayList<Invoice> invoices2 = new ArrayList<>(dao.findClientInvoices(10L));
        assertTrue(invoices2.isEmpty());
    }
}