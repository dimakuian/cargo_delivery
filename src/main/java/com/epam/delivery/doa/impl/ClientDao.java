package com.epam.delivery.doa.impl;

import com.epam.delivery.entities.Client;
import com.epam.delivery.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class ClientDao extends AbstractDao<Client, Integer> {

    private static final String INSERT = "INSERT INTO client (id, user_id, name, surname, patronymic, email, phone, " +
            "balance) VALUES (DEFAULT,?,?,?,?,?,?,?)";

    private static final String UPDATE = "UPDATE client SET user_id=?,name=?,surname=?,patronymic=?,email=?,phone=?," +
            "balance=? WHERE id=?";

    private static final String SELECT_BY_ID = "SELECT id, user_id, name, surname, patronymic, email, phone, balance "
            + "FROM client WHERE id=?";

    private static final String SELECT_BY_USER_ID = "SELECT id, user_id, name, surname, patronymic, email, phone, balance "
            + "FROM client WHERE user_id=?";

    private static final String EXIST = "SELECT id FROM client WHERE id=?";

    private static final String EXIST_EMAIL = "SELECT email FROM client WHERE email=?";

    private static final String EXIST_PHONE = "SELECT phone FROM client WHERE phone=?";

    private static final String SELECT_ALL = "SELECT id, user_id, name, surname, patronymic, email, phone, balance "
            + "FROM client";

    private static final String DELETE = "DELETE FROM client WHERE id=?";

    public ClientDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean insert(Client entity) {
        try (PreparedStatement stat = connection.prepareStatement(INSERT, RETURN_GENERATED_KEYS)) {
            stat.setInt(1, entity.getUser().getId());
            stat.setString(2, entity.getName());
            stat.setString(3, entity.getSurname());
            stat.setString(4, entity.getPatronymic());
            stat.setString(5, entity.getEmail());
            stat.setString(6, entity.getPhone());
            stat.setDouble(7,entity.getBalance());
            if (stat.executeUpdate() > 0) {
                try (ResultSet rs = stat.getGeneratedKeys()) {
                    if (rs.next()) {
                        int genID = rs.getInt(1);
                        entity.setId(genID);
                        return true;
                    }
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while insert client " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Client entity) {
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            stat.setInt(1, entity.getUser().getId());
            stat.setString(2, entity.getName());
            stat.setString(3, entity.getSurname());
            stat.setString(4, entity.getPatronymic());
            stat.setString(5, entity.getEmail());
            stat.setString(6, entity.getPhone());
            stat.setDouble(7,entity.getBalance());
            stat.setInt(8, entity.getId());
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while update client " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }


    public Optional<Client> findById(Integer id) {
        Client client = null;
        UserDao userDao = new UserDao(super.connection);
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    int userID = rs.getInt("user_id");
                    User user = userDao.findById(userID).orElseThrow(() ->
                            new SQLException("Can't find User for client while client findAll"));
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String patronymic = rs.getString("patronymic");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    double balance = rs.getDouble("balance");
                    client = Client.createClient(user, name, surname);
                    client.setId(id);
                    client.setPatronymic(patronymic);
                    client.setEmail(email);
                    client.setPhone(phone);
                    client.setBalance(balance);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while findAll client " + exception.getMessage());
            exception.printStackTrace();
        }
        return Optional.ofNullable(client);
    }

    @Override
    public boolean existsById(Integer id) {
        try (PreparedStatement stat = connection.prepareStatement(EXIST)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while exist client " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public Iterable<Client> findAll() {
        List<Client> clientList = new ArrayList<>();
        UserDao userDao = new UserDao(super.connection);
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int userID = rs.getInt("user_id");
                    User user = userDao.findById(userID).orElseThrow(() ->
                            new SQLException("Can't find User for client while client findAll"));
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String patronymic = rs.getString("patronymic");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    double balance = rs.getDouble("balance");
                    Client client = Client.createClient(user, name, surname);
                    client.setId(id);
                    client.setPatronymic(patronymic);
                    client.setEmail(email);
                    client.setPhone(phone);
                    client.setBalance(balance);
                    clientList.add(client);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while findAll client " + exception.getMessage());
            exception.printStackTrace();
        }
        return clientList;
    }

    @Override
    public boolean deleteById(Integer id) {
        try (PreparedStatement stat = connection.prepareStatement(DELETE)) {
            stat.setInt(1, id);
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while deleteById client " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    public Optional<Client> getByUserId(Integer userID) {
        Client client = null;
        UserDao userDao = new UserDao(super.connection);
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_USER_ID)) {
            stat.setInt(1, userID);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    int clientID = rs.getInt("id");
                    User user = userDao.findById(userID).orElseThrow(() ->
                            new SQLException("Can't find User for client while client findAll"));
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String patronymic = rs.getString("patronymic");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    double balance = rs.getDouble("balance");
                    client = Client.createClient(user, name, surname);
                    client.setId(clientID);
                    client.setPatronymic(patronymic);
                    client.setEmail(email);
                    client.setPhone(phone);
                    client.setBalance(balance);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while findAll client " + exception.getMessage());
            exception.printStackTrace();
        }
        return Optional.ofNullable(client);
    }

    public boolean existsEmail(String email) {
        try (PreparedStatement stat = connection.prepareStatement(EXIST_EMAIL)) {
            stat.setString(1, email);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while exist client " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    public boolean existsPhone(String tel) {
        try (PreparedStatement stat = connection.prepareStatement(EXIST_PHONE)) {
            stat.setString(1, tel);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while exist client " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }
}
