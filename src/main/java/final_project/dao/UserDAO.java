package final_project.dao;

import final_project.models.User;
import final_project.utils.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;

public class UserDAO extends GeneralDAO <User>{

    public User addUser(User user) throws SQLException {

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO USERS VALUES (?, ?, ?, ?, ?, ?)")) {
            checkUserDuplicate(connection, user.getId(), user.getUserName());
            statement.setLong(1, user.getId());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getCountry());
            statement.setInt(5, user.getAge());
            statement.setString(6, user.getUserType().name());

            if(statement.executeUpdate() == 0)
                throw new SQLException();
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + " Issue to save User ID: " + user.getId());
        }
        return user;
    }

    public void deleteUser(long id) throws SQLException{
        delete("USERS", id);
    }

    public TreeSet<User> getAll() throws SQLException {
        TreeSet<User> users = new TreeSet<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM USERS")) {
            ResultSet result = statement.executeQuery();
            if(result.isBeforeFirst())
                while (result.next()) {
                    long user_Id = result.getLong(1);
                    String name = result.getString(2);
                    String password = result.getString(3);
                    String country = result.getString(4);
                    int age = result.getInt(5);
                    UserType u_type = UserType.valueOf(result.getString(6));

                    User user = new User(name, password, country, age, u_type);
                    user.setId(user_Id);
                    users.add(user);
                }
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with selecting all users");
        }

        return users;
    }

    public User getById(Connection connection, long id) throws SQLException {
        User user = null;
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM USERS WHERE ID = ?")) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if(result.isBeforeFirst())
                while (result.next()) {
                    long user_Id = result.getLong(1);
                    String name = result.getString(2);
                    String password = result.getString(3);
                    String country = result.getString(4);
                    int age = result.getInt(5);
                    UserType u_type = UserType.valueOf(result.getString(6));

                    user = new User(name, password, country, age, u_type);
                    user.setId(user_Id);
                }
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with searching user by ID: " + id);
        }
        return user;
    }

    private void checkUserDuplicate (Connection connection, long id, String username) throws SQLException{
        PreparedStatement checkDuplicateStatement = connection.prepareStatement("SELECT * FROM USERS WHERE ID = ? OR U_NAME = ?");
        checkDuplicateStatement.setLong(1, id);
        checkDuplicateStatement.setString(2, username);
        if(checkDuplicateStatement.executeUpdate() != 0)
            throw new SQLException("User is registered already! ID: " + id);
    }

}
