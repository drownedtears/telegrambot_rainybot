package drownedtears.portfolio.rainybot.service;

import drownedtears.portfolio.rainybot.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * {@link Service} for handling {@link User} entity
 */
public interface UserService {

    /**
     * Save provided {@link User} entity
     *
     * @param  User provided telegram user
     */
    void save(User User);

    /**
     * Retrieve all active {@link User}.
     *
     * @return the collection of the active {@link User} objects
     */
    List<User> getAllActiveUsers();

    /**
     * Retrieve all active {@link User} with time =
     * @param time
     *
     * @return the collection of the active {@link User} objects
     */
    List<User> getAllUsersByTime(String time);

    /**
     * Find {@link User} by chatId.
     *
     * @param chatId provided Chat ID
     * @return {@link User} with provided chat ID or null otherwise
     */
    Optional<User> findByChatId(String chatId);

    /**
     * Retrieve all {@link User}.
     *
     * @return the collection of the {@link User} objects
     */
    List<User> getAll();
}
