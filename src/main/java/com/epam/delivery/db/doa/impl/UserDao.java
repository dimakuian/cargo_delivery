package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.doa.AbstractDao;
import com.epam.delivery.db.doa.EntityMapper;
import com.epam.delivery.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao extends AbstractDao<User, Long> {
    private static final long serialVersionUID = -6256490762577543035L;

    private static final String INSERT = "INSERT INTO user (id,login,password,role_id) VALUES (DEFAULT,?,?,?)";
    private static final String UPDATE = "UPDATE user SET login = ?, password = ?, role_id = ? WHERE id = ?";
    private static final String SELECT_BY_ID = "SELECT id, login, password, role_id FROM user WHERE id = ?";
    private static final String SELECT_BY_LOGIN = "SELECT id, login, password, role_id FROM user WHERE login = ?";
    private static final String EXIST = "SELECT id FROM user WHERE id=?";
    private static final String EXIST_BY_LOGIN = "SELECT login FROM user WHERE login=?";
    private static final String SELECT_ALL = "SELECT id, login, password, role_id FROM user";
    private static final String DELETE = "DELETE FROM user WHERE id =?";


    public UserDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean insert(User entity) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
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
            System.out.println("SQLException while insert user " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    @Override
    public boolean update(User entity) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            stat.setString(1, entity.getLogin());
            stat.setString(2, entity.getPassword());
            stat.setInt(3, entity.getRoleID());
            stat.setLong(4, entity.getId());
            if (stat.executeUpdate() > 0) result = true;
        } catch (SQLException exception) {
            System.err.println("SQLException while update user " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    public Optional<User> findById(Long id) {
        User user = null;
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    UserMapper mapper = new UserMapper();
                    user = mapper.mapRow(rs);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while select user by id ==> " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public boolean existsById(Long id) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(EXIST)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) result = true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while check if exist user by id ==> " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    @Override
    public Iterable<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    UserMapper mapper = new UserMapper();
                    User user = mapper.mapRow(rs);
                    users.add(user);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while find all users ==> " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return users;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(DELETE)) {
            stat.setLong(1, id);
            if (stat.executeUpdate() > 0) result = true;
        } catch (SQLException exception) {
            System.err.println("SQLException while delete all users ==> " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    public Optional<User> getByLogin(String login) {
        User user = null;
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_LOGIN)) {
            stat.setString(1, login);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    long id = rs.getLong("id");
                    String password = rs.getString("password");
                    int roleID = rs.getInt("role_id");
                    user = User.createUser(login, password, roleID);
                    user.setId(id);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while select user by login ==> " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return Optional.ofNullable(user);
    }

    public boolean existsByLogin(String userName) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(EXIST_BY_LOGIN)) {
            stat.setString(1, userName);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) result = true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while check if exist user by id ==> " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
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
                long id = rs.getLong("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                int roleID = rs.getInt("role_id");
                User user = User.createUser(login, password, roleID);
                user.setId(id);
                return user;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
