package com.epam.delivery.doa.impl;


import com.epam.delivery.entities.Admin;
import com.epam.delivery.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminDao extends AbstractDao<Admin, Integer> {

    private static final String INSERT = "INSERT INTO manager (id, user_id, name, surname) VALUES (DEFAULT,?,?,?)";
    private static final String UPDATE = "UPDATE manager SET user_id = ?, name = ?, surname = ? WHERE id = ?";
    private static final String SELECT_BY_ID = "SELECT id, user_id, name, surname FROM manager WHERE id = ?";
    private static final String SELECT_BY_USER_ID = "SELECT id, user_id, name, surname FROM manager WHERE user_id = ?";
    private static final String EXIST = "SELECT id FROM manager WHERE id=?";
    private static final String SELECT_ALL = "SELECT id, user_id, name, surname FROM manager";
    private static final String DELETE = "DELETE FROM manager WHERE id=?";

    protected AdminDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean insert(Admin entity) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stat.setInt(1, entity.getUser().getId());
            stat.setString(2, entity.getName());
            stat.setString(3, entity.getSurname());
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
            System.err.println("SQLException while insert manager " + exception.getMessage());
            exception.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Admin entity) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            stat.setInt(1, entity.getUser().getId());
            stat.setString(2, entity.getName());
            stat.setString(3, entity.getSurname());
            stat.setInt(4, entity.getId());
            if (stat.executeUpdate() > 0) result = true;
        } catch (SQLException exception) {
            System.err.println("SQLException while update manager " + exception.getMessage());
            exception.printStackTrace();
        }
        return result;
    }


    public Optional<Admin> findById(Integer id) {
        Admin admin = null;
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    int userID = rs.getInt("user_id");
                    UserDao userDao = new UserDao(super.connection);
                    User user = userDao.findById(userID).orElseThrow(() ->
                            new SQLException("Can't find User for Manager while Manager getById"));
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    admin = Admin.createManager(user, name, surname);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while getById manager " + exception.getMessage());
            exception.printStackTrace();
        }
        return Optional.ofNullable(admin);
    }

    @Override
    public boolean existsById(Integer id) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(EXIST)) {
            stat.setInt(1, id);
            ResultSet rs = stat.executeQuery();
            if (rs.next()) result = true;
        } catch (SQLException exception) {
            System.err.println("SQLException while existsById manager " + exception.getMessage());
            exception.printStackTrace();
        }
        return result;
    }

    @Override
    public Iterable<Admin> findAll() {
        List<Admin> admins = new ArrayList<>();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    int managerID = rs.getInt("id");
                    int userID = rs.getInt("user_id");
                    UserDao userDao = new UserDao(super.connection);
                    User user = userDao.findById(userID).orElseThrow(() ->
                            new SQLException("Can't find User for Manager while Manager findAll"));
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    Admin admin = Admin.createManager(user, name, surname);
                    admin.setId(managerID);
                    admins.add(admin);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while findAll manager " + exception.getMessage());
            exception.printStackTrace();
        }
        return admins;
    }

    @Override
    public boolean deleteById(Integer id) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(DELETE)) {
            stat.setInt(1, id);
            if (stat.executeUpdate() > 0) result = true;
        } catch (SQLException exception) {
            System.err.println("SQLException while deleteById manager " + exception.getMessage());
            exception.printStackTrace();
        }
        return result;
    }

    public Optional<Admin> getByUserId(Integer userID) {
        Admin admin = null;
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_USER_ID)) {
            stat.setInt(1, userID);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    UserDao userDao = new UserDao(super.connection);
                    User user = userDao.findById(userID).orElseThrow(() ->
                            new SQLException("Can't find User for Manager while Manager getByUserId"));
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    admin = Admin.createManager(user, name, surname);
                    admin.setId(id);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while getById manager " + exception.getMessage());
            exception.printStackTrace();
        }
        return Optional.ofNullable(admin);
    }
}
