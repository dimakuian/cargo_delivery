package com.epam.delivery.doa.impl;

import com.epam.delivery.doa.SimpleConnection;
import com.epam.delivery.entities.Language;
import com.epam.delivery.entities.ShippingStatus;
import com.epam.delivery.entities.ShippingStatusDescription;

import java.sql.*;
import java.util.*;

public class ShippingStatusDescriptionDao extends AbstractDao<ShippingStatusDescription, Integer> {
    private static final long serialVersionUID = 5914479348016532002L;

    private static final String INSERT =
            "INSERT INTO shipping_status_description (shipping_status_id, language_id, description) VALUES (?,?,?), (?,?,?)";

    private static final String UPDATE =
            "UPDATE description_locality SET city=?,street=?,street_number=? " +
                    "WHERE locality_id=? AND language_id=?";

    private static final String SELECT_BY_ID = "SELECT\n" +
            "    shipping_status.id,\n" +
            "    shipping_status.name,\n" +
            "    GROUP_CONCAT((SELECT shipping_status_description.description WHERE language.short_name = 'EN')) as en_description,\n" +
            "    GROUP_CONCAT((SELECT shipping_status_description.description WHERE language.short_name = 'UA')) as ua_description\n" +
            "FROM shipping_status_description\n" +
            "         INNER JOIN language ON shipping_status_description.language_id = language.id\n" +
            "         INNER JOIN shipping_status ON shipping_status_description.shipping_status_id = shipping_status.id\n" +
            "WHERE shipping_status.id=?\n" +
            "GROUP BY shipping_status.id\n" +
            "ORDER BY shipping_status.id";


    private static final String EXIST = "SELECT locality_id FROM description_locality WHERE locality_id =?";

    private static final String SELECT_ALL = "SELECT\n" +
            "    shipping_status.id,\n" +
            "    shipping_status.name,\n" +
            "    GROUP_CONCAT((SELECT shipping_status_description.description WHERE language.short_name = 'EN')) as en_description,\n" +
            "    GROUP_CONCAT((SELECT shipping_status_description.description WHERE language.short_name = 'UA')) as ua_description\n" +
            "FROM shipping_status_description\n" +
            "         INNER JOIN language ON shipping_status_description.language_id = language.id\n" +
            "         INNER JOIN shipping_status ON shipping_status_description.shipping_status_id = shipping_status.id\n" +
            "GROUP BY shipping_status.id\n" +
            "ORDER BY shipping_status.id";

    private static final String DELETE = "DELETE FROM description_locality WHERE locality_id=?";


    public ShippingStatusDescriptionDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean insert(ShippingStatusDescription entity) {
        try (PreparedStatement stat = connection.prepareStatement(INSERT)) {
            int id = entity.getShippingStatus().getId();
            stat.setInt(1, id);
            Language en = getLanguage(entity.getDescription(), "EN");
            stat.setInt(2, en.getId());
            stat.setString(3, entity.getDescription().get(en));
            stat.setInt(4, id);
            Language ua = getLanguage(entity.getDescription(), "UA");
            stat.setInt(5, ua.getId());
            stat.setString(6, entity.getDescription().get(ua));
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            //add logger
        }
        return false;
    }

    @Override
    public boolean update(ShippingStatusDescription entity) {
        return false;
    }


    //select by shipping status id
    @Override
    public Optional<ShippingStatusDescription> findById(Integer id) {
        ShippingStatusDescription statusDescription = null;
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    statusDescription = getStatusDescription(rs);
                }
            }
        } catch (SQLException exception) {
            //add logger and work with exception
            exception.printStackTrace();
        }
        return Optional.ofNullable(statusDescription);
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<ShippingStatusDescription> findAll() {
        List<ShippingStatusDescription> list = new ArrayList<>();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    ShippingStatusDescription description = getStatusDescription(rs);
                    list.add(description);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            //add logger
        }
        return list;
    }

    @Override
    public boolean deleteById(Integer integer) {
        return false;
    }

    private static Language getLanguage(Map<Language, String> map, String shortName) {
        return map.keySet().stream()
                .filter(language -> language.getShortName().equals(shortName))
                .findFirst().get();
    }

    private ShippingStatusDescription getStatusDescription(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String en = rs.getString("en_description");
        String ua = rs.getString("ua_description");
        ShippingStatus shippingStatus = ShippingStatus.createShippingStatus(name);
        int statusId = rs.getInt("id");
        shippingStatus.setId(statusId);
        LanguageDao languageDao = new LanguageDao(connection);
        Language enLanguage = languageDao.findById(1).orElse(null);
        Language uaLanguage = languageDao.findById(2).orElse(null);
        Map<Language, String> map = new HashMap<>();
        map.put(enLanguage, en);
        map.put(uaLanguage, ua);
        return ShippingStatusDescription.create(shippingStatus, map);
    }

    public static void main(String[] args) {
        ShippingStatusDescriptionDao dao = new ShippingStatusDescriptionDao(SimpleConnection.getConnection());
        ShippingStatusDescription description = dao.findById(7).get();
        System.out.println(description.getUaDescription());
        System.out.println(description.getEnDescription());
        System.out.println(description);
        for (ShippingStatusDescription d:dao.findAll()) {
            System.out.println(d);
        }
    }
}
