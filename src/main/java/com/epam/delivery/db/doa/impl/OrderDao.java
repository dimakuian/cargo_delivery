package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.Fields;
import com.epam.delivery.db.doa.EntityMapper;
import com.epam.delivery.db.entities.Order;
import com.epam.delivery.db.entities.bean.OrderBean;

import java.sql.*;
import java.util.*;

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

    private static final String SELECT_ALL_ORDER_BEAN = "SELECT o.id,\n" +
            "       sa_ua.city          AS shipping_city_ua,\n" +
            "       sa_ua.street        AS shipping_street_ua,\n" +
            "       sa_ua.street_number AS shipping_street_number_ua,\n" +
            "       sa_en.city          AS shipping_city_en,\n" +
            "       sa_en.street        AS shipping_street_en,\n" +
            "       sa_en.street_number AS shipping_street_number_en,\n" +
            "       da_ua.city          AS delivery_city_ua,\n" +
            "       da_ua.street        as delivery_street_ua,\n" +
            "       da_ua.street_number AS delivery_street_number_ua,\n" +
            "       da_en.city          AS delivery_city_en,\n" +
            "       da_en.street        AS delivery_street_en,\n" +
            "       da_en.street_number AS delivery_street_number_en,\n" +
            "       o.creation_time,\n" +
            "       o.client_id,\n" +
            "       c.name,\n" +
            "       c.surname,\n" +
            "       c.patronymic,\n" +
            "       o.consignee,\n" +
            "       o.description,\n" +
            "       o.distance,\n" +
            "       o.length,\n" +
            "       o.height,\n" +
            "       o.width,\n" +
            "       o.weight,\n" +
            "       o.volume,\n" +
            "       o.fare,\n" +
            "       ssd_ua.shipping_status_id AS status_id,\n" +
            "       ssd_ua.description  AS status_ua,\n" +
            "       ssd_en.description  AS status_en,\n" +
            "       o.delivery_date\n" +
            "FROM `order` o\n" +
            "         JOIN description_locality sa_ua ON sa_ua.locality_id = o.shipping_address\n" +
            "         JOIN description_locality sa_en ON sa_en.locality_id = o.shipping_address\n" +
            "         JOIN description_locality da_ua ON da_ua.locality_id = o.delivery_address\n" +
            "         JOIN description_locality da_en ON da_en.locality_id = o.delivery_address\n" +
            "         JOIN client c on o.client_id = c.id\n" +
            "         JOIN shipping_status_description ssd_ua on o.shipping_status_id = ssd_ua.shipping_status_id\n" +
            "         JOIN shipping_status_description ssd_en on o.shipping_status_id = ssd_en.shipping_status_id\n" +
            "WHERE sa_ua.language_id = 2\n" +
            "  AND sa_en.language_id = 1\n" +
            "  AND da_ua.language_id = 2\n" +
            "  AND da_en.language_id = 1\n" +
            "  AND ssd_ua.language_id = 2\n" +
            "  AND ssd_en.language_id = 1\n" +
            "ORDER BY %s\n" +
            "LIMIT ?,?";

    public static final String SELECT_USER_ORDERS_BEAN = "SELECT o.id,\n" +
            "       sa_ua.city          AS shipping_city_ua,\n" +
            "       sa_ua.street        AS shipping_street_ua,\n" +
            "       sa_ua.street_number AS shipping_street_number_ua,\n" +
            "       sa_en.city          AS shipping_city_en,\n" +
            "       sa_en.street        AS shipping_street_en,\n" +
            "       sa_en.street_number AS shipping_street_number_en,\n" +
            "       da_ua.city          AS delivery_city_ua,\n" +
            "       da_ua.street        as delivery_street_ua,\n" +
            "       da_ua.street_number AS delivery_street_number_ua,\n" +
            "       da_en.city          AS delivery_city_en,\n" +
            "       da_en.street        AS delivery_street_en,\n" +
            "       da_en.street_number AS delivery_street_number_en,\n" +
            "       o.creation_time,\n" +
            "       o.client_id,\n" +
            "       c.name,\n" +
            "       c.surname,\n" +
            "       c.patronymic,\n" +
            "       o.consignee,\n" +
            "       o.description,\n" +
            "       o.distance,\n" +
            "       o.length,\n" +
            "       o.height,\n" +
            "       o.width,\n" +
            "       o.weight,\n" +
            "       o.volume,\n" +
            "       o.fare,\n" +
            "       ssd_ua.shipping_status_id AS status_id,\n" +
            "       ssd_ua.description  AS status_ua,\n" +
            "       ssd_en.description  AS status_en,\n" +
            "       o.delivery_date\n" +
            "FROM `order` o\n" +
            "         JOIN description_locality sa_ua ON sa_ua.locality_id = o.shipping_address\n" +
            "         JOIN description_locality sa_en ON sa_en.locality_id = o.shipping_address\n" +
            "         JOIN description_locality da_ua ON da_ua.locality_id = o.delivery_address\n" +
            "         JOIN description_locality da_en ON da_en.locality_id = o.delivery_address\n" +
            "         JOIN client c on o.client_id = c.id\n" +
            "         JOIN shipping_status_description ssd_ua on o.shipping_status_id = ssd_ua.shipping_status_id\n" +
            "         JOIN shipping_status_description ssd_en on o.shipping_status_id = ssd_en.shipping_status_id\n" +
            "WHERE sa_ua.language_id = 2\n" +
            "  AND sa_en.language_id = 1\n" +
            "  AND da_ua.language_id = 2\n" +
            "  AND da_en.language_id = 1\n" +
            "  AND ssd_ua.language_id = 2\n" +
            "  AND ssd_en.language_id = 1\n" +
            "  AND client_id = ?\n" +
            "ORDER BY %s\n" +
            "LIMIT ?,?";

    public static final String SELECT_ORDER_BEAN_BY_ID = "SELECT o.id,\n" +
            "       sa_ua.city          AS shipping_city_ua,\n" +
            "       sa_ua.street        AS shipping_street_ua,\n" +
            "       sa_ua.street_number AS shipping_street_number_ua,\n" +
            "       sa_en.city          AS shipping_city_en,\n" +
            "       sa_en.street        AS shipping_street_en,\n" +
            "       sa_en.street_number AS shipping_street_number_en,\n" +
            "       da_ua.city          AS delivery_city_ua,\n" +
            "       da_ua.street        as delivery_street_ua,\n" +
            "       da_ua.street_number AS delivery_street_number_ua,\n" +
            "       da_en.city          AS delivery_city_en,\n" +
            "       da_en.street        AS delivery_street_en,\n" +
            "       da_en.street_number AS delivery_street_number_en,\n" +
            "       o.creation_time,\n" +
            "       o.client_id,\n" +
            "       c.name,\n" +
            "       c.surname,\n" +
            "       c.patronymic,\n" +
            "       o.consignee,\n" +
            "       o.description,\n" +
            "       o.distance,\n" +
            "       o.length,\n" +
            "       o.height,\n" +
            "       o.width,\n" +
            "       o.weight,\n" +
            "       o.volume,\n" +
            "       o.fare,\n" +
            "       ssd_ua.shipping_status_id AS status_id,\n" +
            "       ssd_ua.description  AS status_ua,\n" +
            "       ssd_en.description  AS status_en,\n" +
            "       o.delivery_date\n" +
            "FROM `order` o\n" +
            "         JOIN description_locality sa_ua ON sa_ua.locality_id = o.shipping_address\n" +
            "         JOIN description_locality sa_en ON sa_en.locality_id = o.shipping_address\n" +
            "         JOIN description_locality da_ua ON da_ua.locality_id = o.delivery_address\n" +
            "         JOIN description_locality da_en ON da_en.locality_id = o.delivery_address\n" +
            "         JOIN client c on o.client_id = c.id\n" +
            "         JOIN shipping_status_description ssd_ua on o.shipping_status_id = ssd_ua.shipping_status_id\n" +
            "         JOIN shipping_status_description ssd_en on o.shipping_status_id = ssd_en.shipping_status_id\n" +
            "WHERE sa_ua.language_id = 2\n" +
            "  AND sa_en.language_id = 1\n" +
            "  AND da_ua.language_id = 2\n" +
            "  AND da_en.language_id = 1\n" +
            "  AND ssd_ua.language_id = 2\n" +
            "  AND ssd_en.language_id = 1\n" +
            "  AND o.id = ?";

    public static final String COUNT_ALL_ORDERS = "SELECT COUNT(*) FROM `order`";

    public static final String COUNT_USER_ORDERS = "SELECT COUNT(*) FROM `order` WHERE client_id=?";

    public OrderDao(ConnectionBuilder builder) {
        super(builder);
    }

    @Override
    public boolean insert(Order entity) {
        boolean result = false;
        Connection connection = builder.getConnection();
        try {
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
        } catch (SQLException exception) {
            logger.error("SQLException while Order insert. " + exception.getMessage());
            builder.rollbackAndClose(connection);
        }finally {
            builder.commitAndClose(connection);
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
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Order update. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
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
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Order findById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
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
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Order existsById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return false;
    }

    @Override
    public List<Order> findAll() {
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
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Order findAll. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
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
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Order deleteById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
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
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Order findAllByUserId. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
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

    public List<OrderBean> findAllOrderBean(int from, int limit, String sort) {
        List<OrderBean> list = new ArrayList<>();
        Connection connection = builder.getConnection();
        String sqlQuery = String.format(SELECT_ALL_ORDER_BEAN, sort);
        try (PreparedStatement stat = connection.prepareStatement(sqlQuery)) {
            stat.setInt(1, from);
            stat.setInt(2, limit);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    OrderBeanMapper mapper = new OrderBeanMapper();
                    OrderBean order = mapper.mapRow(rs);
                    list.add(order);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Order findAllOrderBean. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return list;
    }

    public Optional<OrderBean> findOrderBeanById(Long orderID) {
        OrderBean orderBean = null;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SELECT_ORDER_BEAN_BY_ID)) {
            stat.setLong(1, orderID);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    OrderBeanMapper mapper = new OrderBeanMapper();
                    orderBean = mapper.mapRow(rs);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Order findOrderBeanById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return Optional.ofNullable(orderBean);
    }

    public List<OrderBean> findClientOrdersBean(int from, int limit, String sort, long clientId) {
        List<OrderBean> list = new ArrayList<>();
        Connection connection = builder.getConnection();
        String sqlQuery = String.format(SELECT_USER_ORDERS_BEAN, sort);
        try (PreparedStatement stat = connection.prepareStatement(sqlQuery)) {
            stat.setLong(1, clientId);
            stat.setInt(2, from);
            stat.setInt(3, limit);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    OrderBeanMapper mapper = new OrderBeanMapper();
                    OrderBean order = mapper.mapRow(rs);
                    System.out.println(order);
                    list.add(order);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Order findClientOrdersBean. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return list;
    }

    public int getNoOfAllOrders() {
        Connection connection = builder.getConnection();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(COUNT_ALL_ORDERS)) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Order getNoOfRecords. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return 0;
    }

    public int getNoOfUserOrders(long clientId) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(COUNT_USER_ORDERS)) {
            stat.setLong(1, clientId);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Order getNoOfRecords. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return 0;
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
            } catch (SQLException exception) {
                logger.error("SQLException while Order mapRaw. " + exception.getMessage());
                throw new IllegalStateException(exception);
            }
        }
    }

    private static class OrderBeanMapper implements EntityMapper<OrderBean> {

        @Override
        public OrderBean mapRow(ResultSet rs) {
            try {
                long orderID = rs.getLong(Fields.USER_ORDER_BEAN__ORDER_ID);
                String shippingCityUA = rs.getString(Fields.USER_ORDER_BEAN__SHIPPING_CITY_UA);
                String shippingStreetUA = rs.getString(Fields.USER_ORDER_BEAN__SHIPPING_STREET_UA);
                String shippingStreetNumberUA = rs.getString(Fields.USER_ORDER_BEAN__SHIPPING_STREET_NUMBER_UA);
                String shippingAddressUA = String.format("%s %s №%s", shippingCityUA, shippingStreetUA,
                        shippingStreetNumberUA);

                String shippingCityEN = rs.getString(Fields.USER_ORDER_BEAN__SHIPPING_CITY_EN);
                String shippingStreetEN = rs.getString(Fields.USER_ORDER_BEAN__SHIPPING_STREET_EN);
                String shippingStreetNumberEN = rs.getString(Fields.USER_ORDER_BEAN__SHIPPING_STREET_NUMBER_EN);
                String shippingAddressEN = String.format("%s %s №%s", shippingCityEN, shippingStreetEN,
                        shippingStreetNumberEN);


                String deliveryCityUA = rs.getString(Fields.USER_ORDER_BEAN__DELIVERY_CITY_UA);
                String deliveryStreetUA = rs.getString(Fields.USER_ORDER_BEAN__DELIVERY_STREET_UA);
                String deliveryStreetNumberUA = rs.getString(Fields.USER_ORDER_BEAN__DELIVERY_STREET_NUMBER_UA);
                String deliveryAddressUA = String.format("%s %s №%s", deliveryCityUA, deliveryStreetUA,
                        deliveryStreetNumberUA);

                String deliveryCityEN = rs.getString(Fields.USER_ORDER_BEAN__DELIVERY_CITY_EN);
                String deliveryStreetEN = rs.getString(Fields.USER_ORDER_BEAN__DELIVERY_STREET_EN);
                String deliveryStreetNumberEN = rs.getString(Fields.USER_ORDER_BEAN__DELIVERY_STREET_NUMBER_EN);
                String deliveryAddressEN = String.format("%s %s №%s", deliveryCityEN, deliveryStreetEN,
                        deliveryStreetNumberEN);


                Timestamp creationTime = rs.getTimestamp(Fields.USER_ORDER_BEAN__DELIVERY_CREATION_TIME);
                long clientID = rs.getLong(Fields.USER_ORDER_BEAN__DELIVERY_CLIENT_ID);
                String clientName = rs.getString(Fields.USER_ORDER_BEAN__DELIVERY_CLIENT_NAME);
                String clientSurname = rs.getString(Fields.USER_ORDER_BEAN__DELIVERY_CLIENT_SURNAME);
                String clientPatronymic = rs.getString(Fields.USER_ORDER_BEAN__DELIVERY_CLIENT_PATRONYMIC);
                String consignee = rs.getString(Fields.USER_ORDER_BEAN__DELIVERY_CLIENT_CONSIGNEE);
                String description = rs.getString(Fields.USER_ORDER_BEAN__DELIVERY_CLIENT_DESCRIPTION);
                double distance = rs.getDouble(Fields.USER_ORDER_BEAN__DELIVERY_CLIENT_DISTANCE);
                double length = rs.getDouble(Fields.USER_ORDER_BEAN__DELIVERY_CLIENT_LENGTH);
                double height = rs.getDouble(Fields.USER_ORDER_BEAN__DELIVERY_HEIGHT);
                double width = rs.getDouble(Fields.USER_ORDER_BEAN__DELIVERY_WIDTH);
                double weight = rs.getDouble(Fields.USER_ORDER_BEAN__DELIVERY_WEIGHT);
                double volume = rs.getDouble(Fields.USER_ORDER_BEAN__DELIVERY_VOLUME);
                double fare = rs.getDouble(Fields.USER_ORDER_BEAN__DELIVERY_FARE);

                long statusID = rs.getLong(Fields.USER_ORDER_BEAN__DELIVERY_STATUS_ID);
                String statusUA = rs.getString(Fields.USER_ORDER_BEAN__DELIVERY_STATUS_UA);
                String statusEN = rs.getString(Fields.USER_ORDER_BEAN__DELIVERY_STATUS_EN);
                Map<String, String> statusMap = new HashMap<>();
                statusMap.put("ua", statusUA);
                statusMap.put("en", statusEN);

                Timestamp deliveryDate = rs.getTimestamp(Fields.USER_ORDER_BEAN__DELIVERY_DELIVERY_DATE);

                OrderBean bean = new OrderBean();

                bean.setId(orderID);
                Map<String, String> shippingMap = new HashMap<>();
                shippingMap.put("ua", shippingAddressUA);
                shippingMap.put("en", shippingAddressEN);
                bean.setShippingAddress(shippingMap);

                Map<String, String> deliveryMap = new HashMap<>();
                deliveryMap.put("ua", deliveryAddressUA);
                deliveryMap.put("en", deliveryAddressEN);
                bean.setDeliveryAddress(deliveryMap);

                bean.setCreationTime(creationTime);
                bean.setClientID(clientID);
                bean.setClient(String.format("%s %s %s", clientSurname, clientName, clientPatronymic));
                bean.setConsignee(consignee);
                bean.setDescription(description);
                bean.setDistance(distance);
                bean.setLength(length);
                bean.setHeight(height);
                bean.setWidth(width);
                bean.setWeight(weight);
                bean.setVolume(volume);
                bean.setFare(fare);
                bean.setStatusId(statusID);
                bean.setStatus(statusMap);
                bean.setDeliveryDate(deliveryDate);

                return bean;
            } catch (SQLException exception) {
                logger.error("SQLException while OrderBeanMapper mapRaw. " + exception.getMessage());
                throw new IllegalStateException(exception);
            }
        }
    }
}
