package com.webserviceVoiture.webserviceVoiture.repository;

/**
 *
 * @author TOAVINA
 */
import com.webserviceVoiture.webserviceVoiture.configuration.user_model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);
    
    Optional<User> findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);
}
