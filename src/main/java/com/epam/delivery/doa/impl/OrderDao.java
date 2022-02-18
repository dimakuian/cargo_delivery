package com.epam.delivery.doa.impl;

import com.epam.delivery.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDao extends AbstractDao<Order, Integer> {

    private static final String INSERT = "INSERT INTO `order` (id, shipping_address, delivery_address, " +
            "creation_time, client_id, consignee, description, distance, length, height, width, weight, volume, " +
            "fare, shipping_status_id, delivery_date) \n" +
            "VALUES (DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String UPDATE = "UPDATE `order` SET shipping_address=?,delivery_address=?,creation_time=?," +
            "client_id=?,consignee=?,description=?,distance=?,length=?,width=?,height=?,weight=?,volume=?,fare=?," +
            "shipping_status_id=?,delivery_date=? WHERE id=?";

    private static final String SELECT_BY_ID = "SELECT id, shipping_address, delivery_address, creation_time, client_id," +
            " consignee, description, distance, length, height, width, weight, volume, fare, shipping_status_id, " +
            " delivery_date FROM `order` WHERE id=?";

    private static final String EXIST = "SELECT id FROM `order` WHERE id =?";

    private static final String SELECT_ALL = "SELECT id, shipping_address, delivery_address, creation_time, client_id, " +
            "consignee, description, distance, length, height, width, weight, volume, fare, shipping_status_id, " +
            "delivery_date FROM `order`";

    private static final String SELECT_ALL_FOR_CLIENT = "SELECT id, shipping_address, delivery_address, creation_time, client_id, " +
            "consignee, description, distance, length, height, width, weight, volume, fare, shipping_status_id, " +
            " delivery_date FROM `order`WHERE client_id=?";

    private static final String DELETE = "DELETE FROM `order` WHERE id=?";

    public OrderDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean insert(Order entity) {
        boolean result = false;
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement stat = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                putDataToStatement(entity, stat);
                if (stat.executeUpdate() > 0) {
                    try (ResultSet rs = stat.getGeneratedKeys()) {
                        if (rs.next()) {
                            int genId = rs.getInt(1);
                            entity.setId(genId);
                        }
                    }
                    result = true;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    private void putDataToStatement(Order entity, PreparedStatement stat) throws SQLException {
        stat.setInt(1, entity.getShippingAddress().getId());
        stat.setInt(2, entity.getDeliveryAddress().getId());
        stat.setTimestamp(3, entity.getCreationTime());
        stat.setInt(4, entity.getClient().getId());
        stat.setString(5, entity.getConsignee());
        stat.setString(6, entity.getDescription());
        stat.setDouble(7, entity.getDistance());
        stat.setDouble(8, entity.getLength());
        stat.setDouble(9, entity.getHeight());
        stat.setDouble(10, entity.getWidth());
        stat.setDouble(11, entity.getWeight());
        stat.setDouble(12, entity.getVolume());
        stat.setDouble(13, entity.getFare());
        stat.setInt(14, entity.getShippingStatus().getId());
        if (entity.getDeliveryDate() != null) {
            stat.setTimestamp(15, entity.getDeliveryDate());
        }
        stat.setNull(15, 0);
    }

    @Override
    public boolean update(Order entity) {
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            putDataToStatement(entity, stat);
            stat.setInt(16, entity.getId());
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Order> findById(Integer id) {
        Order order = Order.createOrder();
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    Order.Builder builder = order.new Builder(order);
                    order = getOrder(rs, builder);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return Optional.ofNullable(order);
    }

    private Order getOrder(ResultSet rs, Order.Builder builder) throws SQLException {
        builder.withID(rs.getInt("id"))
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

        int clientId = rs.getInt("client_id");
        ClientDao clientDao = new ClientDao(connection);
        Client client = clientDao.findById(clientId).orElse(null);//replace to throw!!!

        builder.withClient(client);

        int shippingAddressId = rs.getInt("shipping_address");
        LocalityDao localityDao = new LocalityDao(connection);
        Locality shippingAddress = localityDao.findById(shippingAddressId).orElse(null);//replace to throw!!!
        builder.withShippingAddress(shippingAddress);

        int deliveryAddressId = rs.getInt("delivery_address");
        Locality deliveryAddress = localityDao.findById(deliveryAddressId).orElse(null);//replace to throw!!!
        builder.withDeliveryAddress(deliveryAddress);

        int shippingStatusId = rs.getInt("shipping_status_id");
        ShippingStatusDao shippingStatusDao = new ShippingStatusDao(connection);
        ShippingStatus shippingStatus = shippingStatusDao.findById(shippingStatusId).orElse(null);//replace to throw!!!
        builder.withShippingStatus(shippingStatus);

        return builder.build();
    }

    @Override
    public boolean existsById(Integer id) {
        try (PreparedStatement stat = connection.prepareStatement(EXIST)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public Iterable<Order> findAll() {
        List<Order> list = new ArrayList<>();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    Order order = Order.createOrder();
                    Order.Builder builder = order.new Builder(order);
                    order = getOrder(rs, builder);
                    list.add(order);
                }
            }
        } catch (SQLException exception) {
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
            exception.printStackTrace();
        }
        return false;
    }

    public Iterable<Order> findAllByUserID(Integer id) {
        List<Order> list = new ArrayList<>();
        try (PreparedStatement stat = connection.prepareStatement(SELECT_ALL_FOR_CLIENT)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    Order order = Order.createOrder();
                    Order.Builder builder = order.new Builder(order);
                    order = getOrder(rs, builder);
                    list.add(order);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return list;
    }
}
