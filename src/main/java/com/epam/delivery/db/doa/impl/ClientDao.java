package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.doa.EntityMapper;
import com.epam.delivery.db.entities.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.delivery.db.Fields.*;
import static com.epam.delivery.db.doa.SqlQuery.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class ClientDao extends AbstractDao<Client, Long> {
    private static final long serialVersionUID = 5751739494497157799L;

    public ClientDao(ConnectionBuilder builder) {
        super(builder);
    }

    @Override
    public boolean insert(Client entity) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(QL_QUERY__CLIENT_INSERT, RETURN_GENERATED_KEYS)) {
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
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Client insert. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return false;
    }

    @Override
    public boolean update(Client entity) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(QL_QUERY__CLIENT_UPDATE)) {
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
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Client update. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return false;
    }


    public Optional<Client> findById(Long id) {
        Client client = null;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(QL_QUERY__CLIENT_SELECT_BY_ID)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    long userID = rs.getLong(CLIENT__USER_ID);
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String patronymic = rs.getString("patronymic");
                    String email = rs.getString(CLIENT__EMAIL);
                    String phone = rs.getString(CLIENT__PHONE);
                    double balance = rs.getDouble(CLIENT__BALANCE);
                    client = Client.createClient(userID, name, surname);
                    client.setId(id);
                    client.setPatronymic(patronymic);
                    client.setEmail(email);
                    client.setPhone(phone);
                    client.setBalance(balance);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Client findById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return Optional.ofNullable(client);
    }

    @Override
    public boolean existsById(Long id) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(QL_QUERY__CLIENT_EXIST)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Client existsById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return false;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clientList = new ArrayList<>();
        Connection connection = builder.getConnection();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(QL_QUERY__CLIENT_SELECT_ALL)) {
                while (rs.next()) {
                    ClientMapper mapper = new ClientMapper();
                    Client client = mapper.mapRow(rs);
                    clientList.add(client);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Client findAll. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return clientList;
    }

    @Override
    public boolean deleteById(Long id) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(QL_QUERY__CLIENT_DELETE)) {
            stat.setLong(1, id);
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Client deleteById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return false;
    }

    public Optional<Client> getByUserId(Long userID) {
        Client client = null;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(QL_QUERY__CLIENT_SELECT_BY_USER_ID)) {
            stat.setLong(1, userID);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    ClientMapper mapper = new ClientMapper();
                    client = mapper.mapRow(rs);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Client getByUserId. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return Optional.ofNullable(client);
    }

    public boolean existsEmail(String email) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(QL_QUERY__CLIENT_EXIST_EMAIL)) {
            stat.setString(1, email);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Client existsEmail. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return false;
    }

    public boolean existsPhone(String tel) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(QL_QUERY__CLIENT_EXIST_PHONE)) {
            stat.setString(1, tel);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Client existsPhone. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
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
                long id = rs.getLong(CLIENT__ID);
                long userID = rs.getLong(CLIENT__USER_ID);
                String name = rs.getString(CLIENT__NAME);
                String surname = rs.getString(CLIENT__SURNAME);
                String patronymic = rs.getString(CLIENT__PATRONYMIC);
                String email = rs.getString(CLIENT__EMAIL);
                String phone = rs.getString(CLIENT__PHONE);
                double balance = rs.getDouble(CLIENT__BALANCE);
                Client client = Client.createClient(userID, name, surname);
                client.setId(id);
                client.setPatronymic(patronymic);
                client.setEmail(email);
                client.setPhone(phone);
                client.setBalance(balance);
                return client;
            } catch (SQLException exception) {
                logger.error("SQLException while Client mapRow. " + exception.getMessage());
                throw new IllegalStateException(exception);
            }
        }
    }

}
