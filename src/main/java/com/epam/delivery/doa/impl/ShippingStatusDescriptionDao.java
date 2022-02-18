package com.epam.delivery.doa.impl;

import com.epam.delivery.doa.SimpleConnection;
import com.epam.delivery.entities.Language;
import com.epam.delivery.entities.ShippingStatus;
import com.epam.delivery.entities.ShippingStatusDescription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ShippingStatusDescriptionDao extends AbstractDao<ShippingStatusDescription, Integer> {
    private static final long serialVersionUID = 5914479348016532002L;

    private static final String INSERT =
            "INSERT INTO shipping_status_description (shipping_status_id, language_id, description) VALUES (?,?,?), (?,?,?)";

    private static final String UPDATE =
            "UPDATE description_locality SET city=?,street=?,street_number=? " +
                    "WHERE locality_id=? AND language_id=?";

    private static final String SELECT_BY_ID =
            "SELECT shipping_status.name,\n" +
                    "       shipping_status_description.description,\n" +
                    "       language.id,\n" +
                    "       language.short_name,\n" +
                    "       language.full_name\n" +
                    "FROM shipping_status_description\n" +
                    "         INNER JOIN language ON shipping_status_description.language_id = language.id\n" +
                    "         INNER JOIN shipping_status ON shipping_status_description.shipping_status_id = shipping_status.id\n" +
                    "WHERE shipping_status_id = ?";

    private static final String EXIST = "SELECT locality_id FROM description_locality WHERE locality_id =?";

    private static final String SELECT_ALL =
            "SELECT locality_id, language_id, city, street, street_number FROM description_locality";

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
                Map<Language, String> map = new HashMap<>();
                String name = "";
                while (rs.next()) {
                    String shortName = rs.getString("short_name");
                    String fullName = rs.getString("full_name");
                    Language language = Language.createLanguage(shortName, fullName);
                    String description = rs.getString("description");
                    language.setId(rs.getInt("id"));
                    map.put(language, description);
                    name = rs.getString("name");
                }
                ShippingStatus shippingStatus = ShippingStatus.createShippingStatus(name);
                shippingStatus.setId(id);
                statusDescription = ShippingStatusDescription.create(shippingStatus, map);
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
        return null;
    }

    @Override
    public boolean deleteById(Integer integer) {
        return false;
    }

    private static Language getLanguage(Map<Language, String> map, String shortName) {
        return map.entrySet().stream().map(Map.Entry::getKey)
                .filter(language -> language.getShortName().equals(shortName))
                .findFirst().get();
    }

    public static void main(String[] args) {
        ShippingStatusDescriptionDao dao = new ShippingStatusDescriptionDao(SimpleConnection.getConnection());
        System.out.println(dao.findById(1).get());
    }
}
