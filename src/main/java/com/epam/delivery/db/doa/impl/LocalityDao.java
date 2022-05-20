package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.doa.EntityMapper;
import com.epam.delivery.db.entities.Locality;
import com.epam.delivery.db.entities.bean.LocalityBean;

import java.sql.*;
import java.util.*;

import static com.epam.delivery.db.Fields.*;
import static com.epam.delivery.db.doa.SqlQuery.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class LocalityDao extends AbstractDao<Locality, Long> {
    private static final long serialVersionUID = -5714557877277572823L;

    public LocalityDao(ConnectionBuilder builder) {
        super(builder);
    }

    @Override
    public boolean insert(Locality entity) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__LOCALITY_INSERT, RETURN_GENERATED_KEYS)) {
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
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Locality insert. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return false;
    }

    @Override
    public boolean update(Locality entity) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__LOCALITY_UPDATE)) {
            stat.setString(1, entity.getName());
            stat.setDouble(2, entity.getLatitude());
            stat.setDouble(3, entity.getLongitude());
            stat.setLong(4, entity.getId());
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Locality update. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return false;
    }

    @Override
    public Optional<Locality> findById(Long id) {
        Locality locality = null;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__LOCALITY_SELECT_BY_ID)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    LocalityMapper mapper = new LocalityMapper();
                    locality = mapper.mapRow(rs);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Locality findById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return Optional.ofNullable(locality);
    }

    @Override
    public boolean existsById(Long id) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__LOCALITY_EXIST)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Locality existsById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return false;
    }

    @Override
    public List<Locality> findAll() {
        List<Locality> list = new ArrayList<>();
        Connection connection = builder.getConnection();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SQL_QUERY__LOCALITY_SELECT_ALL)) {
                while (rs.next()) {
                    LocalityMapper mapper = new LocalityMapper();
                    Locality locality = mapper.mapRow(rs);
                    list.add(locality);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Locality findAll. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return list;
    }

    @Override
    public boolean deleteById(Long id) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__LOCALITY_DELETE)) {
            stat.setLong(1, id);
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Locality deleteById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return false;
    }

    public Optional<Double> calcDistanceBetweenTwoLocality(Locality from, Locality to) {
        Double distance_in_km = null;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__LOCALITY_CALC_DISTANCE)) {
            stat.setLong(1, from.getId());
            stat.setLong(2, to.getId());
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    distance_in_km = rs.getDouble(LOCALITY__DISTANCE_IN_KM);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Locality calcDistanceBetweenTwoLocality. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
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
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__LOCALITY_SELECT_TRANSLATE_BY_STATUS_ID)) {
            stat.setLong(1, statusID);
            stat.setLong(2, langID);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    LocalityBeanMapper mapper = new LocalityBeanMapper();
                    return mapper.mapRow(rs);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while ShippingStatus getTranslateByLocalityIdAndLangId. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
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
                long locID = rs.getLong(LOCALITY__ID);
                String name = rs.getString(LOCALITY__NAME);
                double lat = rs.getDouble(LOCALITY__LAT);
                double lng = rs.getDouble(LOCALITY__LNG);
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
                String city = rs.getString(LOCALITY__BEAN_CITY);
                String street = rs.getString(LOCALITY__BEAN_STREET);
                String streetNumber = rs.getString(LOCALITY__BEAN_STREET_NUMBER);
                return String.format("%s %s №%s", city, street, streetNumber);
            } catch (SQLException exception) {
                logger.error("SQLException while Locality mapRow. " + exception.getMessage());
                throw new IllegalStateException(exception);
            }
        }
    }
}
