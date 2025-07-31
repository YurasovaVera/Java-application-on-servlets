package service;

public class PasswordService {
    private static final String SALT = "xQ!0ps#7dkw9@Y3kI";
    public String hashPassword(String password) {
        String passwordWithSalt = password + SALT;
        return String.valueOf(passwordWithSalt.hashCode());
    }
    public boolean verifyPassword(String password, String hashedPassword) {
        return hashedPassword.equals(hashPassword(password));
    }
}
