package repository;

import entity.Profile;
import exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileRepository {
    private static final String INSERT_QUERY = "INSERT INTO profile (login, password_hash) values(?,?)";
    private static final String SELECT_QUERY = "SELECT * FROM profile where login = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM profile where id = ?";
    private static final ProfileRepository instance = new ProfileRepository();

    /*public ProfileRepository() {
    }*/

    public int create(Profile profile) {
        try (Connection connection = ConnectionFactory.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, profile.getLogin());
            preparedStatement.setString(2, profile.getPassword());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public Profile findByLogin(String login) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSetToProfile(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public boolean existsById(Long profileId) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, profileId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new DBException("Error checking the existence of a profile");
        }
    }

    private Profile resultSetToProfile(ResultSet resultSet) throws SQLException {
        Profile profile = new Profile();
        profile.setId(resultSet.getLong("id"));
        profile.setLogin(resultSet.getString("login"));
        profile.setPassword(resultSet.getString("password_hash"));
        return profile;
    }
}
