package services;

import entities.User;
import repository.UserRepository;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    public User findUserByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }
}
