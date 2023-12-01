package oleg.sichev.cloudstorage.repository;

import oleg.sichev.cloudstorage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author OlegSichev
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
