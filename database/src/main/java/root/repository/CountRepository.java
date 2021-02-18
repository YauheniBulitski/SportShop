package root.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import root.entity.Count;
import root.entity.Product;

import java.util.Optional;

@Repository
public interface CountRepository extends CrudRepository<Count, Long> {

    Count save(Count count);

    Optional<Count> findByProductId(long id);

    @Modifying
    @Query("update Count c set c.count=:count where c.product=:product")
    void updateCount(@Param("count") Integer count, @Param("product") Product product);
}
