package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.Fields;
import com.epam.delivery.db.doa.EntityMapper;
import com.epam.delivery.db.doa.SqlQuery;
import com.epam.delivery.db.entities.Client;
import com.epam.delivery.db.entities.Invoice;
import com.epam.delivery.db.entities.InvoiceStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class InvoiceDao extends AbstractDao<Invoice, Long> {

    public InvoiceDao(ConnectionBuilder builder) {
        super(builder);
    }

    @Override
    public boolean insert(Invoice entity) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SqlQuery.SQL_QUERY__INVOICE_INSERT,
                Statement.RETURN_GENERATED_KEYS)) {

            stat.setLong(1, entity.getClientID());
            stat.setTimestamp(2, entity.getCreationDatetime());
            stat.setLong(3, entity.getOrderID());
            stat.setDouble(4, entity.getSum());
            stat.setLong(5, entity.getInvoiceStatusID());
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
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Invoice insert. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return false;
    }

    @Override
    public boolean update(Invoice entity) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SqlQuery.SQL_QUERY__INVOICE_UPDATE)) {
            stat.setLong(1, entity.getClientID());
            stat.setTimestamp(2, entity.getCreationDatetime());
            stat.setLong(3, entity.getOrderID());
            stat.setDouble(4, entity.getSum());
            stat.setLong(5, entity.getInvoiceStatusID());
            stat.setLong(6, entity.getId());
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Invoice update. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return false;
    }

    @Override
    public Optional<Invoice> findById(Long id) {
        Invoice invoice = null;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SqlQuery.SQL_QUERY__INVOICE_SELECT_BY_ID)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    EntityMapper<Invoice> mapper = new InvoiceMapper();
                    invoice = mapper.mapRow(rs);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Invoice findById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return Optional.ofNullable(invoice);
    }

    @Override
    public boolean existsById(Long id) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SqlQuery.SQL_QUERY__INVOICE_EXIST_BY_ID)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Invoice existsById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return false;
    }

    @Override
    public List<Invoice> findAll() {
        List<Invoice> list = new ArrayList<>();
        Connection connection = builder.getConnection();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SqlQuery.SQL_QUERY__INVOICE_SELECT_ALL)) {
                while (rs.next()) {
                    EntityMapper<Invoice> mapper = new InvoiceMapper();
                    Invoice invoice = mapper.mapRow(rs);
                    list.add(invoice);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Invoice findAll. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return list;
    }

    @Override
    public boolean deleteById(Long id) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SqlQuery.SQL_QUERY__INVOICE_DELETE_BY_ID)) {
            stat.setLong(1, id);
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Invoice deleteById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return false;
    }

    public List<Invoice> findClientInvoices(Long clientID) {
        List<Invoice> list = new ArrayList<>();
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SqlQuery.SQL_QUERY__INVOICE_SELECT_CLIENT_INVOICES)) {
            stat.setLong(1, clientID);
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    EntityMapper<Invoice> mapper = new InvoiceMapper();
                    Invoice invoice = mapper.mapRow(rs);
                    list.add(invoice);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Invoice findAll. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return list;
    }

    public List<Invoice> findClientInvoicesByStatus(Long clientID, InvoiceStatus status) {
        List<Invoice> list = new ArrayList<>();
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SqlQuery.SQL_QUERY__INVOICE_SELECT_CLIENT_INVOICES_BY_STATUS_ID)) {
            stat.setLong(1, clientID);
            stat.setInt(2,status.ordinal());
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    EntityMapper<Invoice> mapper = new InvoiceMapper();
                    Invoice invoice = mapper.mapRow(rs);
                    list.add(invoice);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Invoice findAll. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return list;
    }

    public boolean payClientInvoice(Invoice invoice, Client client) {
        Connection connection = builder.getConnection();
        try (PreparedStatement invoicePayStat = connection.prepareStatement(SqlQuery.SQL_QUERY__INVOICE_UPDATE_SET_PAY_STATUS_BY_ID);
             PreparedStatement clientBalanceStat = connection.prepareStatement(SqlQuery.SQL_QUERY__CLIENT_UPDATE_BALANCE)) {
            invoicePayStat.setLong(1, invoice.getId());
            double currentBalance = client.getBalance();
            double newBalance = currentBalance - invoice.getSum();
            clientBalanceStat.setDouble(1, newBalance);
            clientBalanceStat.setLong(2, client.getId());
            if (invoicePayStat.executeUpdate() > 0 && clientBalanceStat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Invoice payClientInvoice. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return false;
    }

    public int getNoOfNotPaidClientInvoices(long clientId) {
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SqlQuery.SQL_QUERY__INVOICE_COUNT_CLIENT_NOT_PAID_INVOICES)) {
            stat.setLong(1, clientId);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Invoice getNoOfNotPaidClientInvoices. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return 0;
    }

    /**
     * Extracts an invoice from the result set row.
     */
    private static class InvoiceMapper implements EntityMapper<Invoice> {

        @Override
        public Invoice mapRow(ResultSet rs) {
            try {
                long id = rs.getLong(Fields.ENTITY__ID);
                long clientID = rs.getLong(Fields.INVOICE__CLIENT_ID);
                Timestamp creationDatetime = rs.getTimestamp(Fields.INVOICE__CREATION_DATETIME);
                long orderID = rs.getLong(Fields.INVOICE__ORDER_ID);
                double sum = rs.getDouble(Fields.INVOICE__SUM);
                int statusID = rs.getInt(Fields.INVOICE__STATUS_ID);
                Invoice invoice = new Invoice(clientID, creationDatetime, orderID, sum, statusID);
                invoice.setId(id);
                return invoice;
            } catch (SQLException exception) {
                logger.error("SQLException while Invoice mapRow. " + exception.getMessage());
                throw new IllegalStateException(exception);
            }
        }
    }
}
