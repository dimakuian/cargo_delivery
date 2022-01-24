package com.epam.delivery.doa.impl;

import com.epam.delivery.entities.Role;
import com.epam.delivery.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao extends AbstractDao<User, Integer> {

    private static final String INSERT = "INSERT INTO user (id,login,password,role_id) VALUES (DEFAULT,?,?,?)";
    private static final String UPDATE = "UPDATE user SET login = ?, password = ?, role_id = ? WHERE id = ?";
    private static final String SELECT_BY_ID = "SELECT id, login, password, role_id FROM user WHERE id = ?";
    private static final String SELECT_BY_LOGIN = "SELECT id, login, password, role_id FROM user WHERE login = ?";
    private static final String EXIST = "SELECT id FROM user WHERE id=?";
    private static final String SELECT_ALL = "SELECT id, login, password, role_id FROM user";
    private static final String DELETE = "DELETE FROM user WHERE id =?";

    protected UserDao(Connection connection) {
        super(connection);
    }

    @Override
    boolean insert(User entity) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stat.setString(1, entity.getLogin());
            stat.setString(2, entity.getPassword());
            stat.setInt(3, entity.getRole().getId());
            if (stat.executeUpdate() > 0) {
                try (ResultSet rs = stat.getGeneratedKeys()) {
                    if (rs.next()) {
                        int genID = rs.getInt(1);
                        entity.setId(genID);
                        result = true;
                    }
                }
            }
        } catch (SQLException exception) {
            System.out.println("SQLException while insert user " + exception.getMessage());
            exception.printStackTrace();
        }
        return result;
    }

    @Override
    boolean update(User entity) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            stat.setString(1, entity.getLogin());
            stat.setString(2, entity.getPassword());
            stat.setInt(3, entity.getRole().getId());
            stat.setInt(4, entity.getId());
            if (stat.executeUpdate() > 0) result = true;
        } catch (SQLException exception) {
            System.err.println("SQLException while update user " + exception.getMessage());
            exception.printStackTrace();
        }
        return result;
    }

    @Override
    Optional<User> getById(Integer id) {
        User user = null;
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    String login = rs.getString("login");
                    String password = rs.getString("password");
                    int roleID = rs.getInt("role_id");
                    RoleDao roleDao = new RoleDao(super.connection);
                    Role role = roleDao.getById(roleID).orElseThrow(() ->
                            new SQLException("Can't find Role for User while User getById"));
                    user = User.createUser(login, password, role);
                    user.setId(id);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while select user by id ==> " + exception.getMessage());
            exception.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    @Override
    boolean existsById(Integer id) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(EXIST)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) result = true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while check if exist user by id ==> " + exception.getMessage());
            exception.printStackTrace();
        }
        return result;
    }

    @Override
    Iterable<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    RoleDao roleDao = new RoleDao(super.connection); //edit connection
                    int userID = rs.getInt("id");
                    String login = rs.getString("login");
                    String password = rs.getString("password");
                    int roleID = rs.getInt("role_id");
                    Role role = roleDao.getById(roleID).orElseThrow(() ->
                            new SQLException("Can't find Role for User while User getById"));
                    User user = User.createUser(login, password, role);
                    user.setId(userID);
                    users.add(user);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while find all users ==> " + exception.getMessage());
            exception.printStackTrace();
        }
        return users;
    }

    @Override
    boolean deleteById(Integer id) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(DELETE)) {
            stat.setInt(1, id);
            if (stat.executeUpdate() > 0) result = true;
        } catch (SQLException exception) {
            System.err.println("SQLException while delete all users ==> " + exception.getMessage());
            exception.printStackTrace();
        }
        return result;
    }

    Optional<User> getByLogin(String login) {
        User user = null;
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_LOGIN)) {
            stat.setString(1, login);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String password = rs.getString("password");
                    int roleID = rs.getInt("role_id");
                    RoleDao roleDao = new RoleDao(super.connection);
                    Role role = roleDao.getById(roleID).orElseThrow(() ->
                            new SQLException("Can't find Role for User while User getById"));
                    user = User.createUser(login, password, role);
                    user.setId(id);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while select user by login ==> " + exception.getMessage());
            exception.printStackTrace();
        }
        return Optional.ofNullable(user);
    }
}
