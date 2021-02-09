package root.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import root.entity.Type;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeRepository extends CrudRepository<Type, Integer> {

    Type save(Type type);

    List<Type> findAllByCategoryId(Integer id);

    Optional<Type> findById(Integer integer);

    boolean existsById(Integer integer);

    List<Type> findAll(Pageable pageable);
}
