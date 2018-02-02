package final_project.dao;

import java.sql.*;
import java.util.ArrayList;

public abstract class GeneralDAO <T> {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.c88oq4boivpv.eu-west-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "AWS_Admin";
    private static String DB_NAME;

    // SQL queries
    private static String SELECT_ALL = "SELECT * FROM %s";
    private static String DELETE_BY_ID = "DELETE FROM %s WHERE ID = ?";
    private static String SELECT_BY_FIELD = "SELECT * FROM %s WHERE %s = ?";

    public GeneralDAO(String dbName) {
        DB_NAME = dbName;
    }

    public void delete(long id) throws SQLException {
        String sql = String.format(DELETE_BY_ID, DB_NAME);
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            if(statement.executeUpdate() == 0)
                throw new SQLException();
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + " Issue with deleting ID: " + id + " from DB: " + DB_NAME);
        }
    }
    public ArrayList<T> getAll() throws SQLException {
        try(Connection connection = getConnection()){
            return getAll(connection);
        } catch (SQLException e) {
            throw  new SQLException();
        }
    }

    public ArrayList<T> getAll(Connection connection) throws SQLException {
        ArrayList<T> itemsList;
        String sql = String.format(SELECT_ALL, DB_NAME);
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();
            itemsList = buildItemList(connection, result);
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + " Issues with selecting all items from " + DB_NAME);
        }
        return itemsList;
    }

    public T getById(long id) throws SQLException {
        try(Connection connection = getConnection()){
            return getById(connection, id);
        } catch (SQLException e) {
            throw  new SQLException();
        }
    }

    public T getById(Connection connection, long id) throws SQLException {
        T t = null;
        String sql = String.format(SELECT_BY_FIELD, DB_NAME, "ID");
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next())
                t = buildItem(connection, result);
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with searching in " + DB_NAME + " by ID: " + id);
        }
        return t;
    }

    public ArrayList<T> getByTextField(String filed, String value) throws SQLException {
        ArrayList<T> items = new ArrayList<>();
        String sql = String.format(SELECT_BY_FIELD, DB_NAME, filed);
        try(Connection connection = getConnection();
            PreparedStatement hotelStatement = connection.prepareStatement(sql) ) {
            hotelStatement.setString(1, value);
            ResultSet result = hotelStatement.executeQuery();
            while (result.next())
                items.add(buildItem(connection, result));
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with searching room by " + filed + ": " + value);
        }
        return items;
    }

    protected abstract T buildItem(Connection connection, ResultSet result) throws SQLException;

    protected abstract ArrayList<T> buildItemList(Connection connection, ResultSet result) throws SQLException;

    Connection getConnection()throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

}
