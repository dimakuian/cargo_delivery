package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.doa.EntityMapper;
import com.epam.delivery.db.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.delivery.db.Fields.*;
import static com.epam.delivery.db.doa.SqlQuery.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UserDao extends AbstractDao<User, Long> {
    private static final long serialVersionUID = -6256490762577543035L;

    public UserDao(ConnectionBuilder builder) {
        super(builder);
    }

    @Override
    public boolean insert(User entity) {
        boolean result = false;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__USER_INSERT, RETURN_GENERATED_KEYS)) {
            stat.setString(1, entity.getLogin());
            stat.setString(2, entity.getPassword());
            stat.setInt(3, entity.getRoleID());
            if (stat.executeUpdate() > 0) {
                try (ResultSet rs = stat.getGeneratedKeys()) {
                    if (rs.next()) {
                        long genID = rs.getLong(1);
                        entity.setId(genID);
                        result = true;
                    }
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while User insert. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return result;
    }

    @Override
    public boolean update(User entity) {
        boolean result = false;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__USER_UPDATE)) {
            stat.setString(1, entity.getLogin());
            stat.setString(2, entity.getPassword());
            stat.setInt(3, entity.getRoleID());
            stat.setLong(4, entity.getId());
            if (stat.executeUpdate() > 0) result = true;
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while User update. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return result;
    }

    public Optional<User> findById(Long id) {
        User user = null;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__USER_SELECT_BY_ID)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    UserMapper mapper = new UserMapper();
                    user = mapper.mapRow(rs);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while User findById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public boolean existsById(Long id) {
        boolean result = false;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__USER_EXIST)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) result = true;
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while User existsById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return result;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Connection connection = builder.getConnection();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SQL_QUERY__USER_SELECT_ALL)) {
                while (rs.next()) {
                    UserMapper mapper = new UserMapper();
                    User user = mapper.mapRow(rs);
                    users.add(user);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while User findAll. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return users;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean result = false;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__USER_DELETE)) {
            stat.setLong(1, id);
            if (stat.executeUpdate() > 0) result = true;
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while User deleteById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return result;
    }

    public Optional<User> getByLogin(String login) {
        User user = null;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__USER_SELECT_BY_LOGIN)) {
            stat.setString(1, login);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    long id = rs.getLong("id");
                    String password = rs.getString("password");
                    int roleID = rs.getInt("role_id");
                    user = new User(login, password, roleID);
                    user.setId(id);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while User getByLogin. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return Optional.ofNullable(user);
    }

    public boolean existsByLogin(String userName) {
        boolean result = false;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__USER_EXIST_BY_LOGIN)) {
            stat.setString(1, userName);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) result = true;
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while User existsByLogin. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return result;
    }

    /**
     * Extracts a user from the result set row.
     */
    private static class UserMapper implements EntityMapper<User> {

        @Override
        public User mapRow(ResultSet rs) {
            try {
                long id = rs.getLong(USER__ID);
                String login = rs.getString(USER__LOGIN);
                String password = rs.getString(USER__PASSWORD);
                int roleID = rs.getInt(USER__ROLE_ID);
                User user = new User(login, password, roleID);
                user.setId(id);
                return user;
            } catch (SQLException exception) {
                logger.error("SQLException while User mapRow. " + exception.getMessage());
                throw new IllegalStateException(exception);
            }
        }
    }
}
