package com.epam.delivery.db.doa.impl;


import com.epam.delivery.db.doa.AbstractDao;
import com.epam.delivery.db.doa.EntityMapper;
import com.epam.delivery.entities.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminDao extends AbstractDao<Admin, Long> {
    private static final long serialVersionUID = 3048949702578419905L;

    private static final String INSERT = "INSERT INTO manager (id, user_id, name, surname) VALUES (DEFAULT,?,?,?)";
    private static final String UPDATE = "UPDATE manager SET user_id = ?, name = ?, surname = ? WHERE id = ?";
    private static final String SELECT_BY_ID = "SELECT id, user_id, name, surname FROM manager WHERE id = ?";
    private static final String SELECT_BY_USER_ID = "SELECT id, user_id, name, surname FROM manager WHERE user_id = ?";
    private static final String EXIST = "SELECT id FROM manager WHERE id=?";
    private static final String SELECT_ALL = "SELECT id, user_id, name, surname FROM manager";
    private static final String DELETE = "DELETE FROM manager WHERE id=?";


    public AdminDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean insert(Admin entity) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stat.setLong(1, entity.getId());
            stat.setString(2, entity.getName());
            stat.setString(3, entity.getSurname());
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
            System.err.println("SQLException while insert manager " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    @Override
    public boolean update(Admin entity) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            stat.setLong(1, entity.getId());
            stat.setString(2, entity.getName());
            stat.setString(3, entity.getSurname());
            stat.setLong(4, entity.getId());
            if (stat.executeUpdate() > 0) result = true;
        } catch (SQLException exception) {
            System.err.println("SQLException while update manager " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }


    public Optional<Admin> findById(Long id) {
        Admin admin = null;
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    AdminMapper mapper = new AdminMapper();
                    admin = mapper.mapRow(rs);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while getById manager " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return Optional.ofNullable(admin);
    }

    @Override
    public boolean existsById(Long id) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(EXIST)) {
            stat.setLong(1, id);
            ResultSet rs = stat.executeQuery();
            if (rs.next()) result = true;
        } catch (SQLException exception) {
            System.err.println("SQLException while existsById manager " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    @Override
    public Iterable<Admin> findAll() {
        List<Admin> admins = new ArrayList<>();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    AdminMapper mapper = new AdminMapper();
                    Admin admin = mapper.mapRow(rs);
                    admins.add(admin);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while findAll manager " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return admins;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(DELETE)) {
            stat.setLong(1, id);
            if (stat.executeUpdate() > 0) result = true;
        } catch (SQLException exception) {
            System.err.println("SQLException while deleteById manager " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    public Optional<Admin> getByUserId(Long userID) {
        Admin admin = null;
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_USER_ID)) {
            stat.setLong(1, userID);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    long id = rs.getLong("id");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    admin = Admin.createManager(userID, name, surname);
                    admin.setId(id);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while getById manager " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return Optional.ofNullable(admin);
    }

    /**
     * Extracts an admin from the result set row.
     */
    private static class AdminMapper implements EntityMapper<Admin> {

        @Override
        public Admin mapRow(ResultSet rs) {
            try {
                long id = rs.getLong("id");
                long userID = rs.getLong("user_id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                Admin admin = Admin.createManager(userID, name, surname);
                admin.setId(id);
                return admin;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }

    }
}
