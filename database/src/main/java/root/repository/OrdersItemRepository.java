package root.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import root.entity.Orders;
import root.entity.OrdersItem;
import root.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrdersItemRepository extends CrudRepository<OrdersItem, Long> {

    OrdersItem save(OrdersItem oi);

    @Query("select sum(oi.price) from OrdersItem oi where oi.order=:order")
    public BigDecimal ordersTotalPrice(@Param("order") Orders order);

    @Query("select oi.product from OrdersItem oi where oi.order=:order")
    public List<Product> findAllProductByOrderId(@Param("order") Orders order);

    @Query("select oi.product from OrdersItem oi where oi.order=:order")
    public List<Product> findAllProductByOrderIdPageable(@Param("order") Orders order, Pageable pageable);
}
