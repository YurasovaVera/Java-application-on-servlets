package repository;

import entity.Employee;
import exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    private static final String SELECT_ALL_EMPLOYEES = "SELECT id, name, age, id_profile FROM employee WHERE id_profile = ?";
    private static final String SELECT_BY_ID = "SELECT id, name, age, id_profile FROM employee WHERE id = ?";
    private static final String SELECT_BY_ID_AND_PROFILE_ID = "SELECT id, name, age, id_profile FROM employee WHERE id = ? AND id_profile = ?";
    private static final String INSERT_QUERY = "INSERT INTO employee (name, age, id_profile) VALUES (?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM employee WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE employee SET name = ?, age = ?, id_profile = ? WHERE id = ?";
    private static final String SEARCH_QUERY = "SELECT id, name, age, id_profile FROM employee WHERE name LIKE ? AND id_profile = ?";

    public List<Employee> findAll(Long id) throws DBException {
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_EMPLOYEES);) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                employees.add(resultSetToEmployee(rs));
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return employees;
    }

    public Employee findById(Long id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return resultSetToEmployee(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
    public boolean existsByIdAndProfileId(Long id, Long profileId) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_AND_PROFILE_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, profileId);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public int insert(Employee employee) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setInt(2, employee.getAge());
            preparedStatement.setLong(3, employee.getId_profile());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void update(Employee employee) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setInt(2, employee.getAge());
            preparedStatement.setLong(3, employee.getId_profile());
            preparedStatement.setLong(4, employee.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DBException("Employee with ID " + employee.getId() + " not found");
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void delete(Long id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
    public List<Employee> search(String name, Long userId){
        List<Employee> employees = new ArrayList<>();
        String searchName = "%" + name + "%";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_QUERY)) {
            preparedStatement.setString(1, searchName);
            preparedStatement.setLong(2, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                employees.add(resultSetToEmployee(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }
    private Employee resultSetToEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getLong("id"));
        employee.setName(rs.getString("name"));
        employee.setAge(rs.getInt("age"));
        employee.setId_profile(rs.getLong("id_profile"));
        return employee;
    }
}
