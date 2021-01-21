package root.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import root.entity.Maker;

import java.util.Optional;

@Repository
public interface MakerRepository extends CrudRepository<Maker,Integer> {

    Maker save(Maker maker);

    Optional<Maker> findById(Integer integer);

    boolean existsById(Integer id);
}
