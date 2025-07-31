package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import exception.DBException;

public class ConnectionFactory {
    public static Connection getConnection() {
        String url = "jdbc:postgresql://localhost:5432/db_laba_servlets";
        try{
            return DriverManager.getConnection(url, "postgres", "21PilotsAgainBeTTer99");
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
