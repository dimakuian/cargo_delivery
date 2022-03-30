package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.doa.EntityMapper;
import com.epam.delivery.db.entities.ShippingStatus;
import com.epam.delivery.db.entities.bean.StatusDescriptionBean;

import java.sql.*;
import java.util.*;

public class ShippingStatusDao extends AbstractDao<ShippingStatus, Long> {
    private static final long serialVersionUID = 5803902748264449477L;

    private static final String INSERT = "INSERT INTO delivery.`shipping_status` (id, name) VALUES (DEFAULT,?)";

    private static final String UPDATE = "UPDATE delivery.`shipping_status` SET name=? WHERE id=?";

    private static final String SELECT_BY_ID = "SELECT id, name from delivery.`shipping_status` WHERE id=?";

    private static final String EXIST = "SELECT id FROM delivery.`shipping_status` WHERE id=?";

    private static final String SELECT_ALL = "select id, name from delivery.`shipping_status`";

    private static final String DELETE = "DELETE FROM delivery.`shipping_status` WHERE id=?";

    private static final String SELECT_TRANSLATE_BY_STATUS_ID = "SELECT description FROM delivery.`shipping_status_description` " +
            "WHERE shipping_status_id = ? AND language_id=?";

    public ShippingStatusDao(ConnectionBuilder builder) {
        super(builder);
    }

    @Override
    public boolean insert(ShippingStatus entity) {
        Connection connection = builder.getConnection();
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
            logger.error("SQLException while ShippingStatus insert. " + exception.getMessage());
        } finally {
            builder.closeConnection(connection);
        }
        return false;
    }

    @Override
    public boolean update(ShippingStatus entity) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            stat.setString(1, entity.getName());
            stat.setLong(2, entity.getId());
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            logger.error("SQLException while ShippingStatus update. " + exception.getMessage());
        } finally {
            builder.closeConnection(connection);
        }
        return false;
    }

    public Optional<ShippingStatus> findById(Long id) {
        ShippingStatus status = null;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    ShippingStatusMapper mapper = new ShippingStatusMapper();
                    status = mapper.mapRow(rs);
                }
            }
        } catch (SQLException exception) {
            logger.error("SQLException while ShippingStatus findById. " + exception.getMessage());
        } finally {
            builder.closeConnection(connection);
        }
        return Optional.ofNullable(status);
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
            logger.error("SQLException while ShippingStatus existById. " + exception.getMessage());
        } finally {
            builder.closeConnection(connection);
        }
        return false;
    }

    @Override
    public List<ShippingStatus> findAll() {
        List<ShippingStatus> statusList = new ArrayList<>();
        Connection connection = builder.getConnection();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    ShippingStatusMapper mapper = new ShippingStatusMapper();
                    ShippingStatus status = mapper.mapRow(rs);
                    statusList.add(status);
                }
            }
        } catch (SQLException exception) {
            logger.error("SQLException while ShippingStatus findAll. " + exception.getMessage());
        } finally {
            builder.closeConnection(connection);
        }
        return statusList;
    }

    @Override
    public boolean deleteById(Long id) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(DELETE)) {
            stat.setLong(1, id);
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            logger.error("SQLException while ShippingStatus deleteById. " + exception.getMessage());
        } finally {
            builder.closeConnection(connection);
        }
        return false;
    }

    public Optional<StatusDescriptionBean> findTranslateByStatusId(Long id) {
        StatusDescriptionBean descriptionBean = null;
        String en = getTranslateByStatusIdAndLangId(id, 1L);
        String ua = getTranslateByStatusIdAndLangId(id, 2L);
        if (en != null && ua != null) {
            descriptionBean = new StatusDescriptionBean();
            Map<String, String> map = new HashMap<>();
            map.put("en", en);
            map.put("ua", ua);
            descriptionBean.setStatusID(id);
            descriptionBean.setDescription(map);
        }
        return Optional.ofNullable(descriptionBean);
    }

    private String getTranslateByStatusIdAndLangId(Long statusID, Long langID) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SELECT_TRANSLATE_BY_STATUS_ID)) {
            stat.setLong(1, statusID);
            stat.setLong(2, langID);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("description");
                }
            }
        } catch (SQLException exception) {
            logger.error("SQLException while ShippingStatus getTranslateByStatusIdAndLangId. " + exception.getMessage());
        } finally {
            builder.closeConnection(connection);
        }
        return null;
    }


    public List<StatusDescriptionBean> getAllTranslate() {
        List<StatusDescriptionBean> list = new ArrayList<>();
        Iterable<ShippingStatus> statuses = this.findAll();
        for (ShippingStatus status : statuses) {
            StatusDescriptionBean description = findTranslateByStatusId(status.getId()).orElse(null);
            if (description == null) return null;
            list.add(description);
        }
        return list;
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
            } catch (SQLException exception) {
                logger.error("SQLException while ShippingStatus mapRow. " + exception.getMessage());
                throw new IllegalStateException(exception);
            }
        }
    }
}
