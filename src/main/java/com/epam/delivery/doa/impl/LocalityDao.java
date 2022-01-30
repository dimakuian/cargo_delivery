package com.epam.delivery.doa.impl;

import com.epam.delivery.entities.Locality;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocalityDao extends AbstractDao<Locality, Integer> {

    private static final String INSERT = "INSERT INTO locality (id, name) VALUES (DEFAULT,?)";

    private static final String UPDATE = "UPDATE locality SET name=? WHERE id=?";

    private static final String SELECT_BY_ID = "SELECT id, name FROM locality WHERE id =?";

    private static final String EXIST = "SELECT id FROM locality WHERE id =?";

    private static final String SELECT_ALL = "SELECT id, name FROM locality";

    private static final String DELETE = "DELETE FROM locality WHERE id=?";

    protected LocalityDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean insert(Locality entity) {
        try (PreparedStatement stat = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stat.setString(1, entity.getName());
            if (stat.executeUpdate() > 0) {
                try (ResultSet rs = stat.getGeneratedKeys()) {
                    if (rs.next()) {
                        int genId = rs.getInt(1);
                        entity.setId(genId);
                    }
                }
                return true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while insert Locality " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Locality entity) {
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            stat.setString(1, entity.getName());
            stat.setInt(2, entity.getId());
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while update Locality " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    public Optional<Locality> findById(Integer id) {
        Locality locality = null;
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    int locID = rs.getInt("id");
                    String name = rs.getString("name");
                    locality = Locality.createLocality(name);
                    locality.setId(locID);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while findById Locality " + exception.getMessage());
            exception.printStackTrace();
        }
        return Optional.ofNullable(locality);
    }

    @Override
    public boolean existsById(Integer id) {
        try (PreparedStatement stat = connection.prepareStatement(EXIST)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while existsById Locality " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public Iterable<Locality> findAll() {
        List<Locality> list = new ArrayList<>();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    int locID = rs.getInt("id");
                    String name = rs.getString("name");
                    Locality locality = Locality.createLocality(name);
                    locality.setId(locID);
                    list.add(locality);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while existsById Locality " + exception.getMessage());
            exception.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteById(Integer id) {
        try (PreparedStatement stat = connection.prepareStatement(DELETE)) {
            stat.setInt(1, id);
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while deleteById Locality " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }
}
