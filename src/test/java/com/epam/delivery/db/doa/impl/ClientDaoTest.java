package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionWithDriverManager;
import com.epam.delivery.db.entities.Client;
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

class ClientDaoTest {

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

    private static ClientDao dao;

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
        dao = new ClientDao(builder);
        ConnectionWithDriverManager.createDataBase();
    }

    @AfterEach
    void tearDown() throws SQLException {
        builder.getConnection().createStatement().executeUpdate(DROP_TABLE);
    }

    @Test
    void insert() {
        Client client = Client.createClient(2, "Name", "Surname");
        client.setPatronymic("Patronymic");
        client.setPhone("+380666666666");
        client.setEmail("mail1@example.com.ua");
        assertTrue(dao.insert(client));
        assertEquals(4, client.getId());
    }

    @Test
    void update() {
        Client client = dao.findById(1L).orElse(null);
        client.setName("Test");
        client.setSurname("Surname");
        assertTrue(dao.update(client));
        Client client2 = dao.findById(1L).orElse(null);
        assertEquals("Test", client2.getName());
        assertEquals("Surname", client2.getSurname());
    }

    @Test
    void findById() {
        Client client = dao.findById(3L).orElse(null);
        assertNotNull(client);
        assertEquals("Zoya", client.getName());
        assertEquals("Bozhko", client.getSurname());
        assertEquals("Havrylivna", client.getPatronymic());
        assertEquals("mail4@gmail.com", client.getEmail());
        assertEquals("+380671111114", client.getPhone());
        Client client2 = dao.findById(11L).orElse(null);
        assertNull(client2);
    }

    @Test
    void existsById() {
        assertTrue(dao.existsById(1L));
        assertFalse(dao.existsById(6L));
    }

    @Test
    void findAll() {
        ArrayList<Client> clients = (ArrayList<Client>) dao.findAll();
        assertFalse(clients.isEmpty());
        assertEquals(3, clients.size());
        assertEquals("Marta", clients.get(1).getName());
    }

    @Test
    void deleteById() {
        ArrayList<Client> clients = (ArrayList<Client>) dao.findAll();
        assertTrue(dao.deleteById(2L));
        ArrayList<Client> afterDelete = (ArrayList<Client>) dao.findAll();
        assertEquals(Arrays.asList(clients.get(0), clients.get(2)), afterDelete);
    }

    @Test
    void getByUserId() {
        Client client = dao.getByUserId(1L).orElse(null);
        assertNotNull(client);
        assertEquals("Borys", client.getName());
        assertEquals("Horbenko", client.getSurname());
        assertEquals("Stefanovych", client.getPatronymic());
        assertEquals("mail1@example.com", client.getEmail());
        assertEquals("+380671111111", client.getPhone());
        Client client2 = dao.getByUserId(2L).orElse(null);
        assertNull(client2);
    }

    @Test
    void existsEmail() {
        assertTrue(dao.existsEmail("mail1@example.com"));
        assertFalse(dao.existsEmail("mail@gmail.com.ua"));
    }

    @Test
    void existsPhone() {
        assertTrue(dao.existsPhone("+380671111113"));
        assertFalse(dao.existsPhone("+380661111113"));
    }
}