package root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.OrdersDto;
import root.entity.Orders;
import root.entity.User;
import root.repository.OrdersItemRepository;
import root.repository.OrdersRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrdersItemRepository ordersItemRepository;
    private final UserServiceImpl userServiceImpl;
    private final ProductService productService;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository,
                         OrdersItemRepository ordersItemRepository,
                         UserServiceImpl userServiceImpl,
                         ProductService productService) {
        this.ordersRepository = ordersRepository;
        this.ordersItemRepository = ordersItemRepository;
        this.userServiceImpl = userServiceImpl;
        this.productService = productService;
    }

    @Transactional
    public Orders saveOrders(OrdersDto ordersDto, User user) {
        Orders orders = Orders.builder()
                .localDateTime(LocalDateTime.now())
                .status("NOT_PAID")
                .typeOfPayment(ordersDto.getType_of_payment())
                .user(user)
                .build();
        return ordersRepository.save(orders);
    }

    public void updatePrice(Orders order) {
        BigDecimal price = ordersItemRepository.ordersTotalPrice(order);
        ordersRepository.updateOrdersPrice(price, order.getId());
    }

    public List<Orders> getOrdersByUserId(Long id) {
        List<Orders> orders = ordersRepository.findByUser_IdAndStatus(id, "NOT_PAID");
        return orders;
    }

    public void dellById(Long id) {
        ordersRepository.deleteById(id);
    }

    public Orders findById(Long id) {
        return ordersRepository.findById(id).get();
    }

    @Transactional
    public void updateStatus(String status, Long id) {
        ordersRepository.updateStatus(status, id);
    }

    public List<Orders> findAllOrders(int pageN, int pageSize, String sortBy){
        Pageable pageable = PageRequest.of(pageN, pageSize, Sort.by(sortBy));
        List<Orders> orders = ordersRepository.findAll(pageable);
        return orders;
    }
}
