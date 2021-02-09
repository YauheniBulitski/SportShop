package root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import root.dto.UserDto;
import root.entity.Orders;
import root.entity.User;
import root.service.OrdersService;
import root.service.UserServiceImpl;
import root.validator.UserValidator;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final OrdersService ordersService;
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl,
                          OrdersService ordersService,
                          UserValidator userValidator) {
        this.userServiceImpl = userServiceImpl;
        this.ordersService = ordersService;
        this.userValidator = userValidator;
    }

    @GetMapping("/registration")
    public Model getCategory(Model model) {
        model.addAttribute("userDto", new UserDto());
        return model;
    }

    @PostMapping(value = "/save-user",
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public String createUser(@Validated UserDto userDto,
                             BindingResult bindingResult) {

        userValidator.check(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userServiceImpl.saveUser(userDto);
        return "redirect:shop";
    }

    @GetMapping("/user-page")
    public String userPage(Model model,
                           HttpSession session) {

        User user = userServiceImpl.findById((Long) session.getAttribute("userId")).orElse(null);
        List<Orders> orders = ordersService.getOrdersByUserId((Long) session.getAttribute("userId"));
        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        return "/user-page";
    }

}
