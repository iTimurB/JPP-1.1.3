package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.ConnectionFromDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = null;
    private Statement statement = null;

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {

        try {
            connection = ConnectionFromDB.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE if not exists users(" +
                    "id integer primary key auto_increment, " +
                    "name varchar(45) not null, " +
                    "lastname varchar(45) not null, " +
                    "age tinyint not null );");
            connection.commit();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            connection = ConnectionFromDB.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS users;");
            connection.commit();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            connection = ConnectionFromDB.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(name, lastname, age)  values (?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            connection = ConnectionFromDB.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM users WHERE  id =" + id + ";");
            connection.commit();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            connection = ConnectionFromDB.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                userList.add(user);
            }
            statement.close();
            resultSet.close();
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try {
            connection = ConnectionFromDB.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE users");
            connection.commit();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
