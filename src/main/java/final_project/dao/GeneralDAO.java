package final_project.dao;

import java.sql.*;
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

    public T getById(String dbName, long id) throws SQLException {
        try(Connection connection = getConnection()){
            return getById(connection, dbName, id);
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + this.getClass().getSimpleName() + ".getById issue! ID: " + id);
        }
    }

    public T getById(Connection connection, String dbName, long id) throws SQLException {
        T t = null;
        String sql = String.format("DELETE FROM %s WHERE ID = ?", dbName);
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next())
                t = buildItem(result);
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with searching in " +this.getClass().getSimpleName() + " by ID: " + id);
        }
        return t;
    }
    protected abstract T buildItem(ResultSet result) throws SQLException ;

    Connection getConnection()throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

}
