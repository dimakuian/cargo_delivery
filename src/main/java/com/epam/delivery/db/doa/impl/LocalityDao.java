package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.doa.EntityMapper;
import com.epam.delivery.db.entities.Locality;
import com.epam.delivery.db.entities.bean.LocalityBean;

import java.sql.*;
import java.util.*;

public class LocalityDao extends AbstractDao<Locality, Long> {
    private static final long serialVersionUID = -5714557877277572823L;

    private static final String INSERT = "INSERT INTO delivery.`locality` VALUES (DEFAULT,?,?,?)";

    private static final String UPDATE = "UPDATE delivery.`locality` SET name=?,lat=?,lng=? WHERE id=?";

    private static final String SELECT_BY_ID = "SELECT id, name, lat, lng FROM delivery.`locality` WHERE id =?";

    private static final String EXIST = "SELECT id FROM delivery.`locality` WHERE id =?";

    private static final String SELECT_ALL = "SELECT id, name, lat, lng FROM delivery.`locality`";

    private static final String DELETE = "DELETE FROM delivery.`locality` WHERE id=?";

    public static final String CALC_DISTANCE = "SELECT a.name AS from_city,\n" +
            "       b.name AS to_city,\n" +
            "       ROUND(\n" +
            "                   111.111 *\n" +
            "                   DEGREES(ACOS(LEAST(1.0, COS(RADIANS(a.lat))\n" +
            "                   * COS(RADIANS(b.lat))\n" +
            "                   * COS(RADIANS(a.lng - b.lng))\n" +
            "                   + SIN(RADIANS(a.lat))\n" +
            "                   * SIN(RADIANS(b.lat)))))) + 40 AS distance_in_km\n" +
            "FROM delivery.`locality` AS a\n" +
            "         JOIN delivery.`locality` AS b ON a.id <> b.id\n" +
            "WHERE a.id = ?\n" +
            "  AND b.id = ?";

    private static final String SELECT_TRANSLATE_BY_STATUS_ID =
            "SELECT locality_id, language_id, city, street, street_number FROM description_locality " +
                    "WHERE locality_id=? AND language_id=?;\n";

    public LocalityDao(ConnectionBuilder builder) {
        super(builder);
    }

    @Override
    public boolean insert(Locality entity) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stat.setString(1, entity.getName());
            stat.setDouble(2, entity.getLatitude());
            stat.setDouble(3, entity.getLongitude());
            if (stat.executeUpdate() > 0) {
                try (ResultSet rs = stat.getGeneratedKeys()) {
                    if (rs.next()) {
                        long genId = rs.getLong(1);
                        entity.setId(genId);
                    }
                }
                return true;
            }
        } catch (SQLException exception) {
            logger.error("SQLException while Locality insert. " + exception.getMessage());
        } finally {
            builder.closeConnection(connection);
        }
        return false;
    }

    @Override
    public boolean update(Locality entity) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            stat.setString(1, entity.getName());
            stat.setDouble(2, entity.getLatitude());
            stat.setDouble(3, entity.getLongitude());
            stat.setLong(4, entity.getId());
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            logger.error("SQLException while Locality update. " + exception.getMessage());
        } finally {
            builder.closeConnection(connection);
        }
        return false;
    }

    @Override
    public Optional<Locality> findById(Long id) {
        Locality locality = null;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    LocalityMapper mapper = new LocalityMapper();
                    locality = mapper.mapRow(rs);
                }
            }
        } catch (SQLException exception) {
            logger.error("SQLException while Locality findById. " + exception.getMessage());
        } finally {
            builder.closeConnection(connection);
        }
        return Optional.ofNullable(locality);
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
            logger.error("SQLException while Locality existsById. " + exception.getMessage());
        } finally {
            builder.closeConnection(connection);
        }
        return false;
    }

    @Override
    public List<Locality> findAll() {
        List<Locality> list = new ArrayList<>();
        Connection connection = builder.getConnection();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    LocalityMapper mapper = new LocalityMapper();
                    Locality locality = mapper.mapRow(rs);
                    list.add(locality);
                }
            }
        } catch (SQLException exception) {
            logger.error("SQLException while Locality findAll. " + exception.getMessage());
        } finally {
            builder.closeConnection(connection);
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
            logger.error("SQLException while Locality deleteById. " + exception.getMessage());
        } finally {
            builder.closeConnection(connection);
        }
        return false;
    }

    public Optional<Double> calcDistanceBetweenTwoLocality(Locality from, Locality to) {
        Double distance_in_km = null;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(CALC_DISTANCE)) {
            stat.setLong(1, from.getId());
            stat.setLong(2, to.getId());
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    distance_in_km = rs.getDouble("distance_in_km");
                }
            }
        } catch (SQLException exception) {
            logger.error("SQLException while Locality calcDistanceBetweenTwoLocality. " + exception.getMessage());
        } finally {
            builder.closeConnection(connection);
        }
        return Optional.ofNullable(distance_in_km);
    }

    public Optional<LocalityBean> findTranslateByStatusId(Long id) {
        LocalityBean descriptionBean = null;
        String en = getTranslateByLocalityIdAndLangId(id, 1L);
        String ua = getTranslateByLocalityIdAndLangId(id, 2L);
        if (en != null && ua != null) {
            descriptionBean = new LocalityBean();
            Map<String, String> map = new HashMap<>();
            map.put("en", en);
            map.put("ua", ua);
            descriptionBean.setLocalityID(id);
            descriptionBean.setDescription(map);
        }
        return Optional.ofNullable(descriptionBean);
    }

    private String getTranslateByLocalityIdAndLangId(Long statusID, Long langID) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SELECT_TRANSLATE_BY_STATUS_ID)) {
            stat.setLong(1, statusID);
            stat.setLong(2, langID);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    LocalityBeanMapper mapper = new LocalityBeanMapper();
                    return mapper.mapRow(rs);
                }
            }
        } catch (SQLException exception) {
            logger.error("SQLException while ShippingStatus getTranslateByLocalityIdAndLangId. " + exception.getMessage());
        } finally {
            builder.closeConnection(connection);
        }
        return null;
    }


    public List<LocalityBean> getAllTranslate() {
        List<LocalityBean> list = new ArrayList<>();
        List<Locality> localities = this.findAll();
        localities.sort(Comparator.comparingLong(Locality::getId));
        for (Locality locality : localities) {
            LocalityBean bean = findTranslateByStatusId(locality.getId()).orElse(null);
            if (bean == null) return null;
            list.add(bean);
        }
        return list;
    }


    /**
     * Extracts a locality from the result set row.
     */
    private static class LocalityMapper implements EntityMapper<Locality> {

        @Override
        public Locality mapRow(ResultSet rs) {
            try {
                long locID = rs.getLong("id");
                String name = rs.getString("name");
                double lat = rs.getDouble("lat");
                double lng = rs.getDouble("lng");
                Locality locality = Locality.createLocality(name, lat, lng);
                locality.setId(locID);
                return locality;
            } catch (SQLException exception) {
                logger.error("SQLException while Locality mapRow. " + exception.getMessage());
                throw new IllegalStateException(exception);
            }
        }
    }

    private static class LocalityBeanMapper implements EntityMapper<String> {

        @Override
        public String mapRow(ResultSet rs) {
            try {
                String city = rs.getString("city");
                String street = rs.getString("street");
                String streetNumber = rs.getString("street_number");
                return String.format("%s %s №%s", city, street, streetNumber);
            } catch (SQLException exception) {
                logger.error("SQLException while Locality mapRow. " + exception.getMessage());
                throw new IllegalStateException(exception);
            }
        }
    }
}
