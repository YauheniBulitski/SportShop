package root.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import root.entity.Orders;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends CrudRepository<Orders, Long> {

    Orders save(Orders orders);

    void deleteById(Long id);

    Optional<Orders> findById(Long id);

    List<Orders> findAll(Pageable pageable);

    @Modifying
    @Query("update Orders o set o.price=:price where o.id=:id")
    void updateOrdersPrice(@Param("price") BigDecimal price, @Param("id") Long id);

    List<Orders> findByUser_IdAndStatus(Long id, String status);

    @Modifying
    @Query("update Orders o set o.status=:status where o.id=:id")
    void updateStatus(@Param("status") String status, @Param("id") Long id);
}
