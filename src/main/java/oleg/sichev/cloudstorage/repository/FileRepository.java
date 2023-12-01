package oleg.sichev.cloudstorage.repository;

import oleg.sichev.cloudstorage.entity.File;
import oleg.sichev.cloudstorage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author OlegSichev
 */

public interface FileRepository extends JpaRepository<File, String> {
    void deleteByName(String name);

    Optional<File> findByNameAndUser(String name, User user);

    List<File> findAllByUser (User user);
}
