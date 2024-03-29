package com.epam.delivery.db.doa.impl;


import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.doa.EntityMapper;
import com.epam.delivery.db.entities.Admin;
import com.epam.delivery.db.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.delivery.db.Fields.*;
import static com.epam.delivery.db.doa.SqlQuery.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class AdminDao extends AbstractDao<Admin, Long> {
    private static final long serialVersionUID = 3048949702578419905L;

    public AdminDao(ConnectionBuilder builder) {
        super(builder);
    }


    @Override
    public boolean insert(Admin entity) {
        boolean result = false;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__ADMIN_INSERT, RETURN_GENERATED_KEYS)) {
            stat.setLong(1, entity.getUserID());
            stat.setString(2, entity.getName());
            stat.setString(3, entity.getSurname());
            if (stat.executeUpdate() > 0) {
                try (ResultSet rs = stat.getGeneratedKeys()) {
                    if (rs.next()) {
                        long genID = rs.getLong(1);
                        entity.setId(genID);
                        result = true;
                    }
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Admin insert. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return result;
    }

    public boolean insert(User user, Admin admin) {
        boolean result = false;
        Connection connection = builder.getConnection();
        try (PreparedStatement adminStat = connection.prepareStatement(SQL_QUERY__ADMIN_INSERT, RETURN_GENERATED_KEYS);
             PreparedStatement userStat = connection.prepareStatement(SQL_QUERY__USER_INSERT, RETURN_GENERATED_KEYS)) {
            userStat.setString(1, user.getLogin());
            userStat.setString(2, user.getPassword());
            userStat.setInt(3, user.getRoleID());
            if (userStat.executeUpdate() > 0) {
                try (ResultSet userRs = userStat.getGeneratedKeys()) {
                    if (userRs.next()) {
                        long genUserId = userRs.getLong(1);
                        user.setId(genUserId);
                    }
                }
            }
            admin.setUserID(user.getId());
            adminStat.setLong(1, admin.getUserID());
            adminStat.setString(2, admin.getName());
            adminStat.setString(3, admin.getSurname());
            if (adminStat.executeUpdate() > 0) {
                try (ResultSet adminRs = adminStat.getGeneratedKeys()) {
                    if (adminRs.next()) {
                        long genAdminId = adminRs.getLong(1);
                        admin.setId(genAdminId);
                        result = true;
                    }
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Admin insert. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return result;
    }

    @Override
    public boolean update(Admin entity) {
        boolean result = false;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__ADMIN_UPDATE)) {
            stat.setLong(1, entity.getUserID());
            stat.setString(2, entity.getName());
            stat.setString(3, entity.getSurname());
            stat.setLong(4, entity.getId());
            if (stat.executeUpdate() > 0) result = true;
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            logger.error("SQLException while Admin update. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return result;
    }


    public Optional<Admin> findById(Long id) {
        logger.info("id=" + id);
        Admin admin = null;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__ADMIN_SELECT_BY_ID)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    AdminMapper mapper = new AdminMapper();
                    admin = mapper.mapRow(rs);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            System.err.println("SQLException while Admin findById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return Optional.ofNullable(admin);
    }

    @Override
    public boolean existsById(Long id) {
        boolean result = false;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__ADMIN_EXIST)) {
            stat.setLong(1, id);
            ResultSet rs = stat.executeQuery();
            if (rs.next()) result = true;
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            System.err.println("SQLException while Admin existsById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return result;
    }

    @Override
    public List<Admin> findAll() {
        List<Admin> admins = new ArrayList<>();
        Connection connection = builder.getConnection();
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SQL_QUERY__ADMIN_SELECT_ALL)) {
                while (rs.next()) {
                    AdminMapper mapper = new AdminMapper();
                    Admin admin = mapper.mapRow(rs);
                    admins.add(admin);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            System.err.println("SQLException while Admin findAll. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return admins;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean result = false;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__ADMIN_DELETE)) {
            stat.setLong(1, id);
            if (stat.executeUpdate() > 0) result = true;
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            System.err.println("SQLException while Admin deleteById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return result;
    }

    public Optional<Admin> getByUserId(Long userID) {
        Admin admin = null;
        Connection connection = builder.getConnection();
        try (PreparedStatement stat = connection.prepareStatement(SQL_QUERY__ADMIN_SELECT_BY_USER_ID)) {
            stat.setLong(1, userID);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    AdminMapper mapper = new AdminMapper();
                    admin = mapper.mapRow(rs);
                }
            }
        } catch (SQLException exception) {
            builder.rollbackAndClose(connection);
            System.err.println("SQLException while Admin getById. " + exception.getMessage());
        } finally {
            builder.commitAndClose(connection);
        }
        return Optional.ofNullable(admin);
    }

    /**
     * Extracts an admin from the result set row.
     */
    private static class AdminMapper implements EntityMapper<Admin> {

        @Override
        public Admin mapRow(ResultSet rs) {
            try {
                long id = rs.getLong(ADMIN__ID);
                long userID = rs.getLong(ADMIN__USER_ID);
                String name = rs.getString(ADMIN__NAME);
                String surname = rs.getString(ADMIN__SURNAME);
                Admin admin = new Admin(userID, name, surname);
                admin.setId(id);
                return admin;
            } catch (SQLException exception) {
                logger.error("SQLException while Admin mapRow." + exception.getMessage());
                throw new IllegalStateException(exception);
            }
        }

    }
}
