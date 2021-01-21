package root.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import root.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findByName(String name);

    List<User> findAll(Pageable pageable);

    void deleteById(Long id);

    Optional<User> findById(long id);
}
