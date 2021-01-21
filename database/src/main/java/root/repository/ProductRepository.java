package root.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import root.entity.Product;
import root.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Product save(Product product);

    List<Product> findAllByTypeId(Integer id, Pageable pageable);

    List<Product> findAll(Pageable pageable);

    List<Product> findByUsers(User user);

    Optional<Product> findById(Long id);

    @Modifying
    @Query("update Product p set p.count=:count where p.id=:id")
    public void updateProductCount(@Param("count") int count, @Param("id") Long id);

    @Query("select p from Product p where upper(p.name) like %:name%")
    public List<Product> findAllMatch(@Param("name") String name, Pageable pageable);

    @Modifying
    @Query("update Product p set p.price=:price where p.id=:id")
    public void updatePrice(@Param("price") BigDecimal price, @Param("id") Long id);
}
