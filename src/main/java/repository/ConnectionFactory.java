package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import exception.DBException;

public class ConnectionFactory {
    public static Connection getConnection() {
        String url = "jdbc:postgresql://db:5432/db_laba_servlets";
        try{
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, "postgres", "passwordForDB");
        } catch (SQLException e) {
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
