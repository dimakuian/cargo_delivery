package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.doa.EntityMapper;
import com.epam.delivery.db.entities.Language;

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
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Language insert. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
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
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Language update. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
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
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Language findById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
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
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Language existsById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return false;
    }

    @Override
    public List<Language> findAll() {
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
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Language findAll. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
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
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Language deleteById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
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
            } catch (SQLException exception) {
                logger.error("SQLException while Language mapRaw. " + exception.getMessage());
                throw new IllegalStateException(exception);
            }
        }

    }
}
