package root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.entity.Orders;
import root.entity.OrdersItem;
import root.entity.Product;
import root.entity.User;
import root.repository.OrdersItemRepository;

import java.util.List;


@Service
public class OrdersItemService {

    private final OrdersService ordersService;
    private final OrdersItemRepository ordersItemRepository;
    private final UserServiceImpl userServiceImpl;
    private final CountService countService;

    @Autowired
    public OrdersItemService(OrdersService ordersService,
                             OrdersItemRepository ordersItemRepository,
                             UserServiceImpl userServiceImpl,
                             CountService countService) {
        this.ordersService = ordersService;
        this.ordersItemRepository = ordersItemRepository;
        this.userServiceImpl = userServiceImpl;
        this.countService = countService;
    }

    @Transactional
    public OrdersItem save(OrdersItem oi) {
        return ordersItemRepository.save(oi);
    }

    @Transactional
    public void saveOrderItemWithOrders(User user, Orders order) {
        List<Product> products = user.getProducts();
        for (Product p : products) {
            int count = countService.decrementProduct(p);
            if (count > 0) {
                OrdersItem oi = OrdersItem.builder()
                        .product(p)
                        .order(order)
                        .price(p.getPrice())
                        .build();
                save(oi);
            }
        }
        userServiceImpl.dellAllProductOfBasket(user);
        ordersService.updatePrice(order);
    }


    public List<Product> findAllProductByOrderIdPageable(int pageN, int pageSize, String sortBy, Orders order) {
        Pageable pageable = PageRequest.of(pageN, pageSize, Sort.by(sortBy));
        List<Product> products = ordersItemRepository.findAllProductByOrderIdPageable(order, pageable);
        return products;
    }

}
