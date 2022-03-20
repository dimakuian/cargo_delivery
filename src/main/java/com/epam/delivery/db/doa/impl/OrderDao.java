package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.doa.EntityMapper;
import com.epam.delivery.entities.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDao extends AbstractDao<Order, Long> {
    private static final long serialVersionUID = 7139334124441683412L;

    private static final String INSERT = "INSERT INTO delivery.`order` (id, shipping_address, delivery_address, " +
            "creation_time, client_id, consignee, description, distance, length, height, width, weight, volume, " +
            "fare, shipping_status_id, delivery_date) \n" +
            "VALUES (DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String UPDATE = "UPDATE delivery.`order` SET shipping_address=?,delivery_address=?,creation_time=?," +
            "client_id=?,consignee=?,description=?,distance=?,length=?,width=?,height=?,weight=?,volume=?,fare=?," +
            "shipping_status_id=?,delivery_date=? WHERE id=?";

    private static final String SELECT_BY_ID = "SELECT id, shipping_address, delivery_address, creation_time, client_id," +
            " consignee, description, distance, length, height, width, weight, volume, fare, shipping_status_id, " +
            " delivery_date FROM delivery.`order` WHERE id=?";

    private static final String EXIST = "SELECT id FROM delivery.`order` WHERE id =?";

    private static final String SELECT_ALL = "SELECT id, shipping_address, delivery_address, creation_time, client_id, " +
            "consignee, description, distance, length, height, width, weight, volume, fare, shipping_status_id, " +
            "delivery_date FROM delivery.`order`";

    private static final String SELECT_ALL_FOR_CLIENT = "SELECT id, shipping_address, delivery_address, creation_time, client_id, " +
            "consignee, description, distance, length, height, width, weight, volume, fare, shipping_status_id, " +
            " delivery_date FROM delivery.`order`WHERE client_id=?";

    private static final String DELETE = "DELETE FROM delivery.`order` WHERE id=?";

    public OrderDao(ConnectionBuilder builder) {
        super(builder);
    }

    @Override
    public boolean insert(Order entity) {
        boolean result = false;
        Connection connection = builder.getConnection();
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement stat = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                putDataToStatement(entity, stat);
                if (stat.executeUpdate() > 0) {
                    try (ResultSet rs = stat.getGeneratedKeys()) {
                        if (rs.next()) {
                            long genId = rs.getLong(1);
                            entity.setId(genId);
                        }
                    }
                    result = true;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
//            rollbackAndClose();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public boolean update(Order entity) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            putDataToStatement(entity, stat);
            stat.setLong(16, entity.getId());
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return false;
    }

    @Override
    public Optional<Order> findById(Long id) {
        Order order = null;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    OrderMapper mapper = new OrderMapper();
                    order = mapper.mapRow(rs);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return Optional.ofNullable(order);
    }

    @Override
    public boolean existsById(Long id) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(EXIST)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return false;
    }

    @Override
    public Iterable<Order> findAll() {
        List<Order> list = new ArrayList<>();
        Connection connection = builder.getConnection();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    OrderMapper mapper = new OrderMapper();
                    Order order = mapper.mapRow(rs);
                    list.add(order);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return list;
    }

    @Override
    public boolean deleteById(Long id) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(DELETE)) {
            stat.setLong(1, id);
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return false;
    }

    public Iterable<Order> findAllByUserID(Long id) {
        List<Order> list = new ArrayList<>();
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SELECT_ALL_FOR_CLIENT)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    OrderMapper mapper = new OrderMapper();
                    Order order = mapper.mapRow(rs);
                    list.add(order);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return list;
    }

    private void putDataToStatement(Order entity, PreparedStatement stat) throws SQLException {
        stat.setLong(1, entity.getShippingAddressID());
        stat.setLong(2, entity.getDeliveryAddressID());
        stat.setTimestamp(3, entity.getCreationTime());
        stat.setLong(4, entity.getClientID());
        stat.setString(5, entity.getConsignee());
        stat.setString(6, entity.getDescription());
        stat.setDouble(7, entity.getDistance());
        stat.setDouble(8, entity.getLength());
        stat.setDouble(9, entity.getHeight());
        stat.setDouble(10, entity.getWidth());
        stat.setDouble(11, entity.getWeight());
        stat.setDouble(12, entity.getVolume());
        stat.setDouble(13, entity.getFare());
        stat.setLong(14, entity.getStatusID());
        if (entity.getDeliveryDate() != null) {
            stat.setTimestamp(15, entity.getDeliveryDate());
        }
        stat.setNull(15, 0);
    }


    /**
     * Extracts an order from the result set row.
     */
    private static class OrderMapper implements EntityMapper<Order> {

        @Override
        public Order mapRow(ResultSet rs) {
            try {
                Order order = Order.createOrder();
                Order.Builder builder = order.new Builder(order);
                builder.withID(rs.getLong("id"))
                        .withCreationTimestamp(rs.getTimestamp("creation_time"))
                        .withConsignee(rs.getString("consignee"))
                        .withLength(rs.getDouble("length"))
                        .withHeight(rs.getDouble("height"))
                        .withWidth(rs.getDouble("width"))
                        .withWeight(rs.getDouble("weight"))
                        .withVolume(rs.getDouble("volume"))
                        .withDeliveryDate(rs.getTimestamp("delivery_date"))
                        .withDescription(rs.getString("description"))
                        .withDistance(rs.getDouble("distance"))
                        .withFare(rs.getDouble("fare"));

                long clientId = rs.getLong("client_id");
                builder.withClient(clientId);

                long shippingAddressId = rs.getLong("shipping_address");
                builder.withShippingAddress(shippingAddressId);

                long deliveryAddressId = rs.getLong("delivery_address");
                builder.withDeliveryAddress(deliveryAddressId);

                long statusId = rs.getLong("shipping_status_id");
                builder.withShippingStatus(statusId);

                return builder.build();
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
