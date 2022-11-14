package drownedtears.portfolio.rainybot.service;

import drownedtears.portfolio.rainybot.user.User;
import drownedtears.portfolio.rainybot.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link UserService}
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAllActiveUsers() {
        return userRepository.findAllByActiveTrue();
    }

    @Override
    public List<User> getAllUsersByTime(String time) {
        return userRepository.findAllByTimeAndActiveTrue(time);
    }

    @Override
    public Optional<User> findByChatId(String chatId) {
        return userRepository.findById(chatId);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
