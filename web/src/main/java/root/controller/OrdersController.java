package root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import root.dto.OrdersDto;
import root.entity.Orders;
import root.entity.User;
import root.service.OrdersItemService;
import root.service.OrdersService;
import root.service.UserServiceImpl;

import javax.servlet.http.HttpSession;

@Controller
public class OrdersController {

    private final OrdersService ordersService;
    private final UserServiceImpl userServiceImpl;
    private final OrdersItemService ordersItemService;

    @Autowired
    public OrdersController(OrdersService ordersService,
                            UserServiceImpl userServiceImpl,
                            OrdersItemService ordersItemService) {
        this.ordersService = ordersService;
        this.userServiceImpl = userServiceImpl;
        this.ordersItemService = ordersItemService;
    }

    @GetMapping("/order-info")
    public String orderInfo(Model model,
                            HttpSession session) {

        User user = userServiceImpl.findById((Long) session.getAttribute("userId")).orElse(null);
        model.addAttribute("user", user);
        if (user != null) {
            model.addAttribute("products", user.getProducts());
        }
        return "order-info";
    }

    @PostMapping(value = "/orders",
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public String createOrders(HttpSession session,
                               OrdersDto ordersDto) {

        User user = userServiceImpl.findById((Long) session.getAttribute("userId")).orElse(null);
        if (user != null) {
            Orders order = ordersService.saveOrders(ordersDto, user);
            userServiceImpl.addOrderForUser(user, order);
            ordersItemService.saveOrderItemWithOrders(user, order);
        }
        return "redirect:shop";
    }

}
