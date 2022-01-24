package com.epam.delivery.doa.impl;

import com.epam.delivery.entities.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class LanguageDao extends AbstractDao<Language, Integer> {

    private static final String INSERT = "INSERT INTO language (id, short_name, full_name) VALUES (DEFAULT,?,?)";

    private static final String UPDATE = "UPDATE language SET short_name = ?, full_name = ? WHERE id = ?";

    private static final String SELECT_BY_ID = "SELECT id, short_name, full_name FROM language WHERE id = ?";

    private static final String EXIST = "SELECT id FROM language WHERE id=?";

    private static final String SELECT_ALL = "SELECT id, short_name, full_name FROM language";

    public static final String DELETE = "DELETE FROM language WHERE id=?";

    protected LanguageDao(Connection connection) {
        super(connection);
    }

    @Override
    boolean insert(Language entity) {
        try (PreparedStatement stat = connection.prepareStatement(INSERT, RETURN_GENERATED_KEYS)) {
            stat.setString(1, entity.getShortName());
            stat.setString(2, entity.getFullName());
            if (stat.executeUpdate() > 0) {
                try (ResultSet rs = stat.getGeneratedKeys()) {
                    while (rs.next()) {
                        int genID = rs.getInt(1);
                        entity.setId(genID);
                    }
                }
                return true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while insert language " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    boolean update(Language entity) {
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            stat.setString(1, entity.getShortName());
            stat.setString(2, entity.getFullName());
            stat.setInt(3, entity.getId());
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while update language " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    Optional<Language> getById(Integer id) {
        Language language = null;
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    String shortName = rs.getString("short_name");
                    String fullName = rs.getString("full_name");
                    language = Language.createLanguage(shortName, fullName);
                    language.setId(id);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while getById language " + exception.getMessage());
            exception.printStackTrace();
        }
        return Optional.ofNullable(language);
    }

    @Override
    boolean existsById(Integer id) {
        try (PreparedStatement stat = connection.prepareStatement(EXIST)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while exist language " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    Iterable<Language> findAll() {
        List<Language> languageList = new ArrayList<>();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String shortName = rs.getString("short_name");
                    String fullName = rs.getString("full_name");
                    Language language = Language.createLanguage(shortName, fullName);
                    language.setId(id);
                    languageList.add(language);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while findAll language " + exception.getMessage());
            exception.printStackTrace();
        }
        return languageList;
    }

    @Override
    boolean deleteById(Integer id) {
        try (PreparedStatement stat = connection.prepareStatement(DELETE)) {
            stat.setInt(1, id);
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while deleteById language " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }
}
