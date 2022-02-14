package com.epam.delivery.doa.impl;

import com.epam.delivery.entities.LocalityDescription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocalityDescriptionDao extends AbstractDao<LocalityDescription, Integer> {

    private static final String INSERT =
            "INSERT INTO description_locality (locality_id, language_id, city, street, street_number) " +
                    "VALUES (?,?,?,?,?)";

    private static final String UPDATE =
            "UPDATE description_locality SET city=?,street=?,street_number=? " +
                    "WHERE locality_id=? AND language_id=?";

    private static final String SELECT_BY_ID =
            "SELECT locality_id, language_id, city, street, street_number FROM description_locality " +
                    "WHERE locality_id=? AND language_id=?";

    private static final String EXIST = "SELECT locality_id FROM description_locality WHERE locality_id =?";

    private static final String SELECT_ALL =
            "SELECT locality_id, language_id, city, street, street_number FROM description_locality";

    private static final String DELETE = "DELETE FROM description_locality WHERE locality_id=?";

    public LocalityDescriptionDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean insert(LocalityDescription entity) {
        try (PreparedStatement stat = connection.prepareStatement(INSERT)) {
            stat.setInt(1, entity.getLocalityID());
            stat.setInt(2, entity.getLanguageID());
            stat.setString(3, entity.getCity());
            stat.setString(4, entity.getStreet());
            stat.setString(5, entity.getStreetNumber());
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while insert LocalityDescription " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(LocalityDescription entity) {
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            stat.setString(1, entity.getCity());
            stat.setString(2, entity.getStreet());
            stat.setString(3, entity.getStreetNumber());
            stat.setInt(4, entity.getLocalityID());
            stat.setInt(5, entity.getLanguageID());
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while update LocalityDescription " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    //not use
    @Override
    public Optional<LocalityDescription> findById(Integer integer) {
        return Optional.empty();
    }


    public Optional<LocalityDescription> findByLocalIDAndLandID(Integer locID, Integer langID) {
        LocalityDescription description = null;
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setInt(1, locID);
            stat.setInt(2, langID);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    String city = rs.getString("city");
                    String str = rs.getString("street");
                    String strNumb = rs.getString("street_number");
                    description = LocalityDescription.create(locID, langID, city, str, strNumb);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while update LocalityDescription " + exception.getMessage());
            exception.printStackTrace();
        }
        return Optional.ofNullable(description);
    }

    //exist by locality id
    @Override
    public boolean existsById(Integer id) {
        try (PreparedStatement stat = connection.prepareStatement(EXIST)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while existsById LocalityDescription " + exception.getMessage());
            exception.printStackTrace();
        }

        return false;
    }

    @Override
    public Iterable<LocalityDescription> findAll() {
        List<LocalityDescription> list = new ArrayList<>();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    int locID = rs.getInt("locality_id");
                    int langID = rs.getInt("language_id");
                    String city = rs.getString("city");
                    String str = rs.getString("street");
                    String strNumb = rs.getString("street_number");
                    LocalityDescription description = LocalityDescription.create(locID, langID, city, str, strNumb);
                    list.add(description);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while findAll LocalityDescription " + exception.getMessage());
            exception.printStackTrace();
        }
        return list;
    }

    //delete by locality id
    @Override
    public boolean deleteById(Integer id) {
        try (PreparedStatement stat = connection.prepareStatement(DELETE)) {
            stat.setInt(1, id);
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while deleteById LocalityDescription " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }
}
