package final_project.dao;

import final_project.models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class GeneralDAO <T> {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.c88oq4boivpv.eu-west-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "AWS_Admin";

    void delete(String dbName, long id) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE ID = ?", dbName);
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            if(statement.executeUpdate() == 0)
                throw new SQLException();
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + " Issue with deleting ID: " + id + " from DB: " + dbName);
        }
    }

    public ArrayList<T> getAll() throws SQLException {
        try(Connection connection = getConnection()){
            return getAll(connection);
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with selecting all items in " + this.getClass().getSimpleName());
        }
    }

    abstract ArrayList<T> getAll(Connection connection) throws SQLException;

    public T getById(long id) throws SQLException {
        try(Connection connection = getConnection()){
            return getById(connection, id);
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + this.getClass().getSimpleName() + ".getById issue! ID: " + id);
        }
    }

    abstract T getById(Connection connection, long id) throws SQLException ;


    Connection getConnection()throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

}
