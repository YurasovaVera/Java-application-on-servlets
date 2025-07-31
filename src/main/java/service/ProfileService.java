package service;

import entity.Profile;
import exception.ValidationException;
import repository.ProfileRepository;

public class ProfileService {
    public static final int MIN_LENGTH_PASSWORD = 6;
    public static final int MIN_LENGTH_LOGIN = 4;
    private final ProfileRepository profileRepository;
    private final PasswordService passwordService;


    public ProfileService(ProfileRepository profileRepository, PasswordService passwordService) {
        this.profileRepository = profileRepository;
        this.passwordService = passwordService;
    }

    public void register(String login, String password) throws ValidationException {
        if (login == null || password == null || login.length() < MIN_LENGTH_LOGIN
                || password.length() < MIN_LENGTH_PASSWORD) {
            throw new ValidationException("The username or password has an incorrect format");
        }
        if (profileRepository.findByLogin(login) != null) {
            throw new ValidationException("The username already exists");
        }
        String hashedPassword = passwordService.hashPassword(password);
        Profile profile = new Profile(null, login, hashedPassword);
        profileRepository.create(profile);
    }

    public Profile login(String login, String password) throws ValidationException {
        Profile profile = profileRepository.findByLogin(login);
        if (profile == null || !passwordService.verifyPassword(password, profile.getPassword())) {
            throw new ValidationException("The username or password has an incorrect format");
        }
        return profile;
    }
}
