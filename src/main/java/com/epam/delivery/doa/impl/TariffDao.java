package com.epam.delivery.doa.impl;

import com.epam.delivery.entities.Tariff;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TariffDao extends AbstractDao<Tariff, Double> {
    public static final String SELECT_ALL = "SELECT weight, price_up_to_500_km, price_up_to_700_km, price_up_to_900_km, " +
            "price_up_to_1200_km, price_up_to_1500_km FROM tariff";

    public TariffDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean insert(Tariff entity) {
        return false;
    }

    @Override
    public boolean update(Tariff entity) {
        return false;
    }

    @Override
    public boolean existsById(Double aDouble) {
        return false;
    }

    @Override
    public Iterable<Tariff> findAll() {
        List<Tariff> list = new ArrayList<>();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    double weight = rs.getDouble("weight");
                    int to500Km = rs.getInt("price_up_to_500_km");
                    int to700Km = rs.getInt("price_up_to_700_km");
                    int to900Km = rs.getInt("price_up_to_900_km");
                    int to1200Km = rs.getInt("price_up_to_1200_km");
                    int to1500Km = rs.getInt("price_up_to_1500_km");
                    Tariff tariff = Tariff.createTariff(weight, to500Km, to700Km, to900Km, to1200Km, to1500Km);
                    list.add(tariff);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteById(Double aDouble) {
        return false;
    }
}
