package com.epam.delivery.doa.impl;

import com.epam.delivery.entities.Person;
import com.epam.delivery.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class PersonDao extends AbstractDao<Person, Integer> {

    private static final String INSERT = "INSERT INTO person (id, user_id, name, surname, patronymic, email, phone)\n" +
            "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE = "UPDATE person SET user_id=?,name=?,surname=?,patronymic=?,email=?,phone=?" +
            "WHERE id=?\n";

    private static final String SELECT_BY_ID = "SELECT id, user_id, name, surname, patronymic, email, phone "
            + "FROM person WHERE id=?";

    private static final String SELECT_BY_USER_ID = "SELECT id, user_id, name, surname, patronymic, email, phone "
            + "FROM person WHERE user_id=?";

    private static final String EXIST = "SELECT id FROM person WHERE id=?";

    private static final String SELECT_ALL = "SELECT id, user_id, name, surname, patronymic, email, phone "
            + "FROM person";

    public static final String DELETE = "DELETE FROM person WHERE id=?";

    protected PersonDao(Connection connection) {
        super(connection);
    }

    @Override
    boolean insert(Person entity) {
        try (PreparedStatement stat = connection.prepareStatement(INSERT, RETURN_GENERATED_KEYS)) {
            stat.setInt(1, entity.getUser().getId());
            stat.setString(2, entity.getName());
            stat.setString(3, entity.getSurname());
            stat.setString(4, entity.getPatronymic());
            stat.setString(5, entity.getEmail());
            stat.setString(6, entity.getPhone());
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
            System.err.println("SQLException while insert person " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    boolean update(Person entity) {
        try (PreparedStatement stat = connection.prepareStatement(UPDATE)) {
            stat.setInt(1, entity.getUser().getId());
            stat.setString(2, entity.getName());
            stat.setString(3, entity.getSurname());
            stat.setString(4, entity.getPatronymic());
            stat.setString(5, entity.getEmail());
            stat.setString(6, entity.getPhone());
            stat.setInt(7, entity.getId());
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while update person " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    Optional<Person> getById(Integer id) {
        Person person = null;
        UserDao userDao = new UserDao(super.connection);
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_ID)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    int personID = rs.getInt("id");
                    int userID = rs.getInt("user_id");
                    User user = userDao.getById(userID).orElseThrow(() ->
                            new SQLException("Can't find User for Person while Person findAll"));
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String patronymic = rs.getString("patronymic");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    person = Person.createPerson(user, name, surname);
                    person.setId(id);
                    person.setPatronymic(patronymic);
                    person.setEmail(email);
                    person.setPhone(phone);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while findAll person " + exception.getMessage());
            exception.printStackTrace();
        }
        return Optional.ofNullable(person);
    }

    @Override
    boolean existsById(Integer id) {
        try (PreparedStatement stat = connection.prepareStatement(EXIST)) {
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while exist person " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    Iterable<Person> findAll() {
        List<Person> personList = new ArrayList<>();
        UserDao userDao = new UserDao(super.connection);
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int userID = rs.getInt("user_id");
                    User user = userDao.getById(userID).orElseThrow(() ->
                            new SQLException("Can't find User for Person while Person findAll"));
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String patronymic = rs.getString("patronymic");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    Person person = Person.createPerson(user, name, surname);
                    person.setId(id);
                    person.setPatronymic(patronymic);
                    person.setEmail(email);
                    person.setPhone(phone);
                    personList.add(person);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while findAll person " + exception.getMessage());
            exception.printStackTrace();
        }
        return personList;
    }

    @Override
    boolean deleteById(Integer id) {
        try (PreparedStatement stat = connection.prepareStatement(DELETE)) {
            stat.setInt(1, id);
            if (stat.executeUpdate() > 0) return true;
        } catch (SQLException exception) {
            System.err.println("SQLException while deleteById person " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    Optional<Person> getByUserId(Integer userID) {
        Person person = null;
        UserDao userDao = new UserDao(super.connection);
        try (PreparedStatement stat = connection.prepareStatement(SELECT_BY_USER_ID)) {
            stat.setInt(1, userID);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    int personID = rs.getInt("id");
                    User user = userDao.getById(userID).orElseThrow(() ->
                            new SQLException("Can't find User for Person while Person findAll"));
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String patronymic = rs.getString("patronymic");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    person = Person.createPerson(user, name, surname);
                    person.setId(personID);
                    person.setPatronymic(patronymic);
                    person.setEmail(email);
                    person.setPhone(phone);
                }
            }
        } catch (SQLException exception) {
            System.err.println("SQLException while findAll person " + exception.getMessage());
            exception.printStackTrace();
        }
        return Optional.ofNullable(person);
    }
}
