package root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import root.dto.ProductDto;
import root.entity.Product;
import root.entity.User;
import root.service.OrdersItemService;
import root.service.OrdersService;
import root.service.ProductService;
import root.service.UserServiceImpl;
import root.validator.ProductValidation;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    private final UserServiceImpl userServiceImpl;
    private final OrdersService ordersService;
    private final OrdersItemService ordersItemService;
    private final ProductValidation productValidation;

    @Autowired
    public AdminController(ProductService productService,
                           UserServiceImpl userServiceImpl,
                           OrdersService ordersService,
                           OrdersItemService ordersItemService,
                           ProductValidation productValidation) {
        this.productService = productService;
        this.userServiceImpl = userServiceImpl;
        this.ordersService = ordersService;
        this.ordersItemService = ordersItemService;
        this.productValidation = productValidation;
    }


    @GetMapping("/admin-page")
    public String getCategory(Model model,
                              HttpSession session) {

        User user = userServiceImpl.findById((Long)session.getAttribute("userId")).orElse(null);
        model.addAttribute("user", user);
        return "/admin/admin-page";
    }

    @GetMapping(value = "/edit{id}")
    public Model orders(Model model,
                        @RequestParam("id") Integer id) {

        model.addAttribute("id", id);
        if (id == 1) {
            model.addAttribute("productDto", new ProductDto());
        }
        return model;
    }

    @PostMapping(value = "/edit-product",
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public String createProduct(@Validated ProductDto productDto,
                                BindingResult bindingResult,
                                Model model) {

        productValidation.check(productDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("id", 1);
            return "/admin/edit";
        }
        productService.saveProduct(productDto);
        return "redirect:/admin/admin-page";
    }

    @PostMapping(value = "/update-price")
    public String updatePrice(@RequestParam("price") BigDecimal price,
                              @RequestParam("id") Long id) {

        productService.updatePrice(price, id);
        return "redirect:/admin/admin-page";
    }

    @PostMapping(value = "/update-status")
    public String updateStatus(@RequestParam("status") String status,
                               @RequestParam("id") Long id) {

        ordersService.updateStatus(status, id);
        return "redirect:/admin/admin-page";
    }

    @PostMapping(value = "/dell-orders")
    public String dellOrders(@RequestParam("id") Long id) {

        ordersItemService.dellOrdersWithProduct(id);
        return "redirect:/admin/admin-page";
    }

    @GetMapping("/view-product")
    public Model getProduct(Model model,
                            @RequestParam(defaultValue = "0") Integer pageNo,
                            @RequestParam(defaultValue = "3") Integer pageSize,
                            @RequestParam(defaultValue = "id") String sortBy) {

        model.addAttribute("products", productService.findAllProduct(pageNo, pageSize, sortBy));
        model.addAttribute("pageNo", pageNo);
        return model;
    }

    @GetMapping("/view-orders")
    public Model getOrders(Model model,
                           @RequestParam(defaultValue = "0") Integer pageNo,
                           @RequestParam(defaultValue = "3") Integer pageSize,
                           @RequestParam(defaultValue = "id") String sortBy) {

        model.addAttribute("orders", ordersService.findAllOrders(pageNo, pageSize, sortBy))
                .addAttribute("pageNo", pageNo);
        return model;
    }

    @GetMapping("/view-users")
    public Model getUsers(Model model,
                          @RequestParam(defaultValue = "0") Integer pageNo,
                          @RequestParam(defaultValue = "3") Integer pageSize,
                          @RequestParam(defaultValue = "id") String sortBy) {

        model.addAttribute("users", userServiceImpl.findAll(pageNo, pageSize, sortBy))
                .addAttribute("pageNo", pageNo);
        return model;
    }

    @GetMapping(value = "/list-product")
    public String listProduct(Model model,
                              @ModelAttribute("order") Long id,
                              @RequestParam(defaultValue = "0") Integer pageNo,
                              @RequestParam(defaultValue = "3") Integer pageSize,
                              @RequestParam(defaultValue = "id") String sortBy) {

        List<Product> products = ordersItemService.findAllProductByOrderIdPageable(pageNo, pageSize, sortBy,
                ordersService.findById(id));
        model.addAttribute("products", products);
        model.addAttribute("pageNo", pageNo);
        return "/admin/list-products";
    }

}
