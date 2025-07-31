package service;

import entity.Employee;
import exception.ValidationException;
import repository.EmployeeRepository;
import repository.ProfileRepository;

import java.util.List;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ProfileRepository profileRepository;

    public EmployeeService(EmployeeRepository employeeRepository, ProfileRepository profileRepository) {
        this.employeeRepository = employeeRepository;
        this.profileRepository = profileRepository;
    }

    public List<Employee> getAllEmployees(Long profileId) {
        return employeeRepository.findAll(profileId);
    }

    public void deleteEmployee(Long id, Long profileId) throws ValidationException {
        if (!employeeRepository.existsByIdAndProfileId(id, profileId)) {
            throw new ValidationException("Employee with id " + id + " not found");
        }
        employeeRepository.delete(id);
    }

    public void addEmployee(Employee employee) throws ValidationException {
        validateEmployee(employee);
        employeeRepository.insert(employee);
    }

    public void updateEmployee(Employee employee, Long profileId) throws ValidationException {
        if (!employeeRepository.existsByIdAndProfileId(employee.getId(), profileId)) {
            throw new ValidationException("Employee with id " + employee.getId() + " not found");
        }
        validateEmployee(employee);
        employeeRepository.update(employee);
    }

    public Employee getEmployeeByIdAndProfileId(Long id, Long profileId) throws ValidationException {
        if (!employeeRepository.existsByIdAndProfileId(id, profileId)) {
            throw new ValidationException("Employee with id " + id + " not found");
        }
        return employeeRepository.findById(id);
    }
    public List<Employee> searchEmployee(String search, Long userId) {
        return employeeRepository.search(search, userId);
    }

    private void validateEmployee(Employee employee) throws ValidationException {
        validateName(employee.getName());
        validateAge(employee.getAge());
        validateProfileId(employee.getId_profile());
    }

    private void validateName(String name) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Employee name is required");
        }
    }

    private void validateAge(int age) throws ValidationException {
        if (age <= 0 || age > 100) {
            throw new ValidationException("Employee age is required");
        }
    }

    private void validateProfileId(Long profileId) throws ValidationException {
        if (!profileRepository.existsById(profileId)) {
            throw new ValidationException("Profile with id " + profileId + " not found");
        }
    }
}
