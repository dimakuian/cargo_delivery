package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionWithDriverManager;
import com.epam.delivery.db.entities.Language;
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

class LanguageDaoTest {

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

    private static LanguageDao dao;

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
        dao = new LanguageDao(builder);
        ConnectionWithDriverManager.createDataBase();
    }

    @AfterEach
    void tearDown() throws SQLException {
        builder.getConnection().createStatement().executeUpdate(DROP_TABLE);
    }

    @Test
    void insert() {
        Language language = Language.createLanguage("PL", "Poland");
        assertTrue(dao.insert(language));
        assertEquals(3, language.getId());
    }

    @Test
    void update() {
        Language language = dao.findById(1L).orElse(null);
        language.setShortName("DE");
        language.setFullName("Germany");
        assertTrue(dao.update(language));
        Language afterUpdate = dao.findById(1L).orElse(null);
        assertEquals("DE", afterUpdate.getShortName());
        assertEquals("Germany", afterUpdate.getFullName());
    }

    @Test
    void findById() {
        Language language = dao.findById(2L).orElse(null);
        assertNotNull(language);
        assertEquals("UA", language.getShortName());
        assertEquals("Ukraine", language.getFullName());
        Language language1 = dao.findById(4L).orElse(null);
        assertNull(language1);
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
        ArrayList<Language> languages = (ArrayList<Language>) dao.findAll();
        assertFalse(languages.isEmpty());
        assertEquals(2,languages.size());
        assertEquals("EN",languages.get(0).getShortName());
        assertEquals("Ukraine",languages.get(1).getFullName());
    }

    @Test
    void deleteById() {
        ArrayList<Language> languages = (ArrayList<Language>) dao.findAll();
        assertTrue(dao.deleteById(1L));
        assertFalse(dao.deleteById(10L));
        ArrayList<Language> afterDelete = (ArrayList<Language>) dao.findAll();
        assertEquals(Collections.singletonList(languages.get(1)),afterDelete);
    }
}