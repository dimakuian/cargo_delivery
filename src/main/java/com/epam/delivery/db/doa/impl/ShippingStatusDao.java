package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.doa.AbstractDao;
import com.epam.delivery.db.doa.EntityMapper;
import com.epam.delivery.entities.ShippingStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShippingStatusDao extends AbstractDao<ShippingStatus, Long> {
    private static final long serialVersionUID = 5803902748264449477L;

    private static final String INSERT = "INSERT INTO shipping_status (id, name) VALUES (DEFAULT,?)";

    private static final String UPDATE = "UPDATE shipping_status SET name=? WHERE id=?";

    private static final String SELECT_BY_ID = "SELECT id, name from shipping_status WHERE id=?";

    private static final String EXIST = "SELECT id FROM shipping_status WHERE id=?";

    private static final String SELECT_ALL = "select id, name from shipping_status";

    private static final String DELETE = "DELETE FROM shipping_status WHERE id=?";

    public ShippingStatusDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean insert(ShippingStatus entity) {
        try (PreparedStatement stat = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stat.setString(1, entity.getName());
            if (stat.executeUpdate() > 0) {
                try (ResultSet rs = stat.getGeneratedKeys()) {
                    if (rs.next()) {
                        long genID = rs.getLong(1);
                        entity.setId(genID);
                    }
                }
                return true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while insert ShippingStatus " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    @Override
    public boolean update(ShippingStatus entity) {
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            stat.setString(1, entity.getName());
            stat.setLong(2, entity.getId());
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while update ShippingStatus " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    public Optional<ShippingStatus> findById(Long id) {
        ShippingStatus status = null;
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    ShippingStatusMapper mapper = new ShippingStatusMapper();
                    status = mapper.mapRow(rs);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while getById ShippingStatus " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return Optional.ofNullable(status);
    }

    @Override
    public boolean existsById(Long id) {
        try (PreparedStatement stat = connection.prepareStatement(EXIST)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while existsById ShippingStatus " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    @Override
    public Iterable<ShippingStatus> findAll() {
        List<ShippingStatus> statusList = new ArrayList<>();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    ShippingStatusMapper mapper = new ShippingStatusMapper();
                    ShippingStatus status = mapper.mapRow(rs);
                    statusList.add(status);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while findAll ShippingStatus " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return statusList;
    }

    @Override
    public boolean deleteById(Long id) {
        try (PreparedStatement stat = connection.prepareStatement(DELETE)) {
            stat.setLong(1, id);
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while deleteById ShippingStatus " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    /**
     * Extracts a shipping status from the result set row.
     */
    private static class ShippingStatusMapper implements EntityMapper<ShippingStatus> {

        @Override
        public ShippingStatus mapRow(ResultSet rs) {
            try {
                long statusID = rs.getLong("id");
                String name = rs.getString("name");
                ShippingStatus status = ShippingStatus.createShippingStatus(name);
                status.setId(statusID);
                return status;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
