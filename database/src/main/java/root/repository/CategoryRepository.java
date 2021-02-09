package root.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import root.entity.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

    Category save(Category category);

    List<Category> findAll();

    Optional<Category> findById(Integer id);

    List<Category> findAll(Pageable pageable);

    boolean existsById(Integer integer);

}
