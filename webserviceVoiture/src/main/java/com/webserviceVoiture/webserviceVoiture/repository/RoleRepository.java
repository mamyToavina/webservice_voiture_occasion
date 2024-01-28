package com.webserviceVoiture.webserviceVoiture.repository;

/**
 *
 * @author TOAVINA
 */

import com.webserviceVoiture.webserviceVoiture.configuration.user_model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
