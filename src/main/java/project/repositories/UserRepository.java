package project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    /**
     * поиск юзера по имейлу
     */
    Optional<User> findByEmail(String email);

    /**
     * поиск юзера по коду для восстановления пароля
     */
    Optional<User> findByCode(String code);
}
