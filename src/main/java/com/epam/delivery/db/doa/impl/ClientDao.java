package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.doa.EntityMapper;
import com.epam.delivery.entities.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class ClientDao extends AbstractDao<Client,Long> {
    private static final long serialVersionUID = 5751739494497157799L;

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

    public ClientDao(ConnectionBuilder builder) {
        super(builder);
    }

    @Override
    public boolean insert(Client entity) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(INSERT, RETURN_GENERATED_KEYS)) {
            stat.setLong(1, entity.getUserID());
            stat.setString(2, entity.getName());
            stat.setString(3, entity.getSurname());
            stat.setString(4, entity.getPatronymic());
            stat.setString(5, entity.getEmail());
            stat.setString(6, entity.getPhone());
            stat.setDouble(7, entity.getBalance());
            if (stat.executeUpdate() > 0) {
                try (ResultSet rs = stat.getGeneratedKeys()) {
                    if (rs.next()) {
                        long genID = rs.getLong(1);
                        entity.setId(genID);
                        return true;
                    }
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while insert client " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return false;
    }

    @Override
    public boolean update(Client entity) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            stat.setLong(1, entity.getUserID());
            stat.setString(2, entity.getName());
            stat.setString(3, entity.getSurname());
            stat.setString(4, entity.getPatronymic());
            stat.setString(5, entity.getEmail());
            stat.setString(6, entity.getPhone());
            stat.setDouble(7, entity.getBalance());
            stat.setLong(8, entity.getId());
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while update client " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return false;
    }


    public Optional<Client> findById(Long id) {
        Client client = null;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    long userID = rs.getLong("user_id");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String patronymic = rs.getString("patronymic");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    double balance = rs.getDouble("balance");
                    client = Client.createClient(userID, name, surname);
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
        } finally {
            closeConnection(connection);
        }
        return Optional.ofNullable(client);
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
            System.err.println("SQLException while exist client " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return false;
    }

    @Override
    public Iterable<Client> findAll() {
        List<Client> clientList = new ArrayList<>();
        Connection connection = builder.getConnection();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    ClientMapper mapper = new ClientMapper();
                    Client client = mapper.mapRow(rs);
                    clientList.add(client);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while findAll client " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return clientList;
    }

    @Override
    public boolean deleteById(Long id) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(DELETE)) {
            stat.setLong(1, id);
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while deleteById client " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return false;
    }

    public Optional<Client> getByUserId(Long userID) {
        Client client = null;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_USER_ID)) {
            stat.setLong(1, userID);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    ClientMapper mapper = new ClientMapper();
                    client = mapper.mapRow(rs);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while findAll client " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return Optional.ofNullable(client);
    }

    public boolean existsEmail(String email) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(EXIST_EMAIL)) {
            stat.setString(1, email);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while exist client " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return false;
    }

    public boolean existsPhone(String tel) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(EXIST_PHONE)) {
            stat.setString(1, tel);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while exist client " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return false;
    }

    /**
     * Extracts a client from the result set row.
     */
    private static class ClientMapper implements EntityMapper<Client> {
        @Override
        public Client mapRow(ResultSet rs) {
            try {
                long id = rs.getLong("id");
                long userID = rs.getLong("user_id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String patronymic = rs.getString("patronymic");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                double balance = rs.getDouble("balance");
                Client client = Client.createClient(userID, name, surname);
                client.setId(id);
                client.setPatronymic(patronymic);
                client.setEmail(email);
                client.setPhone(phone);
                client.setBalance(balance);
                return client;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }

    }

}
