package final_project.dao;

import final_project.models.User;
import final_project.utils.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO extends GeneralDAO <User>{
    private final static String USER_DB = "USERS";

    public UserDAO() {
        super(USER_DB);
    }

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
       } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + " Issue to save User ID: " + user.getId());
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

    @Override
    protected User buildItem(Connection connection, ResultSet result) throws SQLException {
        long userId = result.getLong(1);
        String name = result.getString(2);
        String password = result.getString(3);
        String country = result.getString(4);
        int age = result.getInt(5);
        UserType uType = UserType.valueOf(result.getString(6));
        User user = new User(name, password, country, age, uType);
        user.setId(userId);
        return user;
    }

    @Override
    protected ArrayList<User> buildItemList(Connection connection, ResultSet result) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        while (result.next())
            users.add(buildItem(connection, result));
        return users;
    }
}
