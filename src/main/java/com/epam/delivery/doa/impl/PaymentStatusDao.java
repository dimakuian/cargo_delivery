package com.epam.delivery.doa.impl;

import com.epam.delivery.entities.PaymentStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentStatusDao extends AbstractDao<PaymentStatus, Integer> {

    private static final String INSERT = "INSERT INTO payment_status (id, name_EN, name_UK) VALUES (DEFAULT,?,?)";

    private static final String UPDATE = "UPDATE payment_status SET name_EN=?,name_UK=? WHERE id=?";

    private static final String SELECT_BY_ID = "SELECT id, name_EN, name_UK from payment_status WHERE id=?";

    private static final String EXIST = "SELECT id FROM payment_status WHERE id=?";

    private static final String SELECT_ALL = "select id, name_EN, name_UK from payment_status";

    private static final String DELETE = "DELETE FROM payment_status WHERE id=?";

    protected PaymentStatusDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean insert(PaymentStatus entity) {
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
            System.err.println("SQLException while insert PaymentStatus " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(PaymentStatus entity) {
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            stat.setString(1, entity.getNameEN());
            stat.setString(2, entity.getNameUK());
            stat.setInt(3, entity.getId());
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while update PaymentStatus " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    public Optional<PaymentStatus> findById(Integer id) {
        PaymentStatus status = null;
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    int statusID = rs.getInt("id");
                    String nameEn = rs.getString("name_EN");
                    String nameUk = rs.getString("name_UK");
                    status = PaymentStatus.createPaymentStatus(nameEn, nameUk);
                    status.setId(statusID);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while getById PaymentStatus " + exception.getMessage());
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
            System.err.println("SQLException while existsById PaymentStatus " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public Iterable<PaymentStatus> findAll() {
        List<PaymentStatus> statusList = new ArrayList<>();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    int statusID = rs.getInt("id");
                    String nameEn = rs.getString("name_EN");
                    String nameUk = rs.getString("name_UK");
                    PaymentStatus status = PaymentStatus.createPaymentStatus(nameEn, nameUk);
                    status.setId(statusID);
                    statusList.add(status);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while findAll PaymentStatus " + exception.getMessage());
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
            System.err.println("SQLException while deleteById PaymentStatus " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }
}
