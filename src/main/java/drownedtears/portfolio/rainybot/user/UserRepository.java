package drownedtears.portfolio.rainybot.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * {@link Repository} for handling with {@link User}
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAllByActiveTrue();

    List<User> findAllByTimeAndActiveTrue(String time);
}
