package com.epam.delivery.doa.impl;

import com.epam.delivery.entities.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleDao extends AbstractDao<Role, Integer> {

    private static final String INSERT = "INSERT INTO role (id, name) VALUES (DEFAULT,?)";
    private static final String UPDATE = "UPDATE role SET name = ? WHERE id = ?";
    private static final String SELECT_BY_ID = "SELECT id,name FROM role WHERE id = ?";
    private static final String SELECT_BY_NAME = "SELECT id,name FROM role WHERE name = ?";
    private static final String EXIST = "SELECT id FROM role WHERE id=?";
    private static final String SELECT_ALL = "SELECT id,name FROM role";
    private static final String DELETE = "DELETE FROM role WHERE id =?";

    public RoleDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean insert(Role entity) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stat.setString(1, entity.getName());
            if (stat.executeUpdate() > 0) {
                try (ResultSet rs = stat.getGeneratedKeys()) {
                    if (rs.next()) {
                        int genId = rs.getInt(1);
                        entity.setId(genId);
                    }
                    result = true;
                }
            }
        } catch (SQLException exception) {
            System.out.println("Can't insert a new role: " + exception.getMessage());
            exception.printStackTrace();
        }
        return result;
    }

    @Override
    boolean update(Role entity) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            stat.setString(1, entity.getName());
            stat.setInt(2, entity.getId());
            if (stat.executeUpdate() > 0) result = true;
        } catch (SQLException exception) {
            System.out.println("Can't update a new role: " + exception.getMessage());
            exception.printStackTrace();
        }
        return result;
    }

    @Override
    Optional<Role> getById(Integer id) {
        Role role = null;
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    role = Role.createRole(name);
                    role.setId(id);
                }
            }
        } catch (SQLException exception) {
            System.out.println("Can't find any role by this id: " + id + exception.getMessage());
            exception.printStackTrace();
        }
        return Optional.ofNullable(role);
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
            System.out.println("SQLException while Role existById. " + exception.getMessage());
            exception.printStackTrace();
        }
        return result;
    }

    @Override
    Iterable<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    Role role = Role.createRole(name);
                    role.setId(id);
                    roles.add(role);
                }
            }
        } catch (SQLException exception) {
            System.out.println("SQLException while find all roles " + exception.getMessage());
            exception.printStackTrace();
        }
        return roles;
    }

    @Override
    boolean deleteById(Integer id) {
        boolean result = false;
        try (PreparedStatement stat = connection.prepareStatement(DELETE)) {
            stat.setInt(1, id);
            if (stat.executeUpdate() > 0) result = true;
        } catch (SQLException exception) {
            System.out.println("SQLException while delete role by id " + exception.getMessage());
            exception.printStackTrace();
        }
        return result;
    }

    Optional<Role> getByName(String name) {
        Role role = null;
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_NAME)) {
            stat.setString(1, name);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    int roleID = rs.getInt("id");
                    role = Role.createRole(name);
                    role.setId(roleID);
                }
            }
        } catch (SQLException exception) {
            System.out.println("Can't find any role by this name: " + name + exception.getMessage());
            exception.printStackTrace();
        }
        return Optional.ofNullable(role);
    }
}
