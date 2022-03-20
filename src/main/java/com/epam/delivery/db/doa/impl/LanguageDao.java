package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.doa.EntityMapper;
import com.epam.delivery.entities.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class LanguageDao extends AbstractDao <Language,Long> {
    private static final long serialVersionUID = 475113985684365786L;

    private static final String INSERT = "INSERT INTO delivery.`language` (id, short_name, full_name) VALUES (DEFAULT,?,?)";

    private static final String UPDATE = "UPDATE delivery.`language` SET short_name = ?, full_name = ? WHERE id = ?";

    private static final String SELECT_BY_ID = "SELECT id, short_name, full_name FROM delivery.`language` WHERE id = ?";

    private static final String EXIST = "SELECT id FROM delivery.`language` WHERE id=?";

    private static final String SELECT_ALL = "SELECT id, short_name, full_name FROM delivery.`language`";

    private static final String DELETE = "DELETE FROM delivery.`language` WHERE id=?";

    public LanguageDao(ConnectionBuilder builder) {
        super(builder);
    }

    @Override
    public boolean insert(Language entity) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(INSERT, RETURN_GENERATED_KEYS)) {
            stat.setString(1, entity.getShortName());
            stat.setString(2, entity.getFullName());
            if (stat.executeUpdate() > 0) {
                try (ResultSet rs = stat.getGeneratedKeys()) {
                    while (rs.next()) {
                        long genID = rs.getLong(1);
                        entity.setId(genID);
                    }
                }
                return true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while insert language " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return false;
    }

    @Override
    public boolean update(Language entity) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            stat.setString(1, entity.getShortName());
            stat.setString(2, entity.getFullName());
            stat.setLong(3, entity.getId());
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while update language " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return false;
    }

    public Optional<Language> findById(Long id) {
        Language language = null;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    LanguageMapper mapper = new LanguageMapper();
                    language = mapper.mapRow(rs);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while getById language " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return Optional.ofNullable(language);
    }

    @Override
    public boolean existsById(Long id) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(EXIST)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while exist language " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return false;
    }

    @Override
    public Iterable<Language> findAll() {
        List<Language> languageList = new ArrayList<>();
        Connection connection = builder.getConnection();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    LanguageMapper mapper = new LanguageMapper();
                    Language language = mapper.mapRow(rs);
                    languageList.add(language);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while findAll language " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return languageList;
    }

    @Override
    public boolean deleteById(Long id) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(DELETE)) {
            stat.setLong(1, id);
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while deleteById language " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return false;
    }

    /**
     * Extracts a language from the result set row.
     */
    private static class LanguageMapper implements EntityMapper<Language> {

        @Override
        public Language mapRow(ResultSet rs) {
            try {
                long id = rs.getLong("id");
                String shortName = rs.getString("short_name");
                String fullName = rs.getString("full_name");
                Language language = Language.createLanguage(shortName, fullName);
                language.setId(id);
                return language;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }

    }
}
