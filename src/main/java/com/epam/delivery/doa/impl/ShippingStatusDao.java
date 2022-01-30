package com.epam.delivery.doa.impl;

import com.epam.delivery.entities.ShippingStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShippingStatusDao extends AbstractDao<ShippingStatus, Integer> {

    private static final String INSERT = "INSERT INTO shipping_status (id, name_EN, name_UK) VALUES (DEFAULT,?,?)";

    private static final String UPDATE = "UPDATE shipping_status SET name_EN=?,name_UK=? WHERE id=?";

    private static final String SELECT_BY_ID = "SELECT id, name_EN, name_UK from shipping_status WHERE id=?";

    private static final String EXIST = "SELECT id FROM shipping_status WHERE id=?";

    private static final String SELECT_ALL = "select id, name_EN, name_UK from shipping_status";

    private static final String DELETE = "DELETE FROM shipping_status WHERE id=?";

    protected ShippingStatusDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean insert(ShippingStatus entity) {
        try (PreparedStatement stat = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stat.setString(1, entity.getNameEN());
            stat.setString(2, entity.getNameUK());
            if (stat.executeUpdate() > 0) {
                try (ResultSet rs = stat.getGeneratedKeys()) {
                    if (rs.next()) {
                        int genID = rs.getInt(1);
                        entity.setId(genID);
                    }
                }
                return true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while insert ShippingStatus " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(ShippingStatus entity) {
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            stat.setString(1, entity.getNameEN());
            stat.setString(2, entity.getNameUK());
            stat.setInt(3, entity.getId());
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while update ShippingStatus " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    public Optional<ShippingStatus> findById(Integer id) {
        ShippingStatus status = null;
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    int statusID = rs.getInt("id");
                    String nameEn = rs.getString("name_EN");
                    String nameUk = rs.getString("name_UK");
                    status = ShippingStatus.createShippingStatus(nameEn, nameUk);
                    status.setId(statusID);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while getById ShippingStatus " + exception.getMessage());
            exception.printStackTrace();
        }
        return Optional.ofNullable(status);
    }

    @Override
    public boolean existsById(Integer id) {
        try (PreparedStatement stat = connection.prepareStatement(EXIST)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while existsById ShippingStatus " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public Iterable<ShippingStatus> findAll() {
        List<ShippingStatus> statusList = new ArrayList<>();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    int statusID = rs.getInt("id");
                    String nameEn = rs.getString("name_EN");
                    String nameUk = rs.getString("name_UK");
                    ShippingStatus status = ShippingStatus.createShippingStatus(nameEn, nameUk);
                    status.setId(statusID);
                    statusList.add(status);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while findAll ShippingStatus " + exception.getMessage());
            exception.printStackTrace();
        }
        return statusList;
    }

    @Override
    public boolean deleteById(Integer id) {
        try (PreparedStatement stat = connection.prepareStatement(DELETE)) {
            stat.setInt(1, id);
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while deleteById ShippingStatus " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }
}
