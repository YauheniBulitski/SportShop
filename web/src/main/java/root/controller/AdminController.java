package root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import root.dto.CategoryDto;
import root.dto.ProductDto;
import root.dto.TypeDto;
import root.dto.UserDto;
import root.entity.Product;
import root.entity.User;
import root.service.*;
import root.validator.ProductValidation;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    private final UserServiceImpl userServiceImpl;
    private final OrdersService ordersService;
    private final OrdersItemService ordersItemService;
    private final ProductValidation productValidation;
    private final TypeService typeService;
    private final CategoryService categoryService;

    @Autowired
    public AdminController(ProductService productService,
                           UserServiceImpl userServiceImpl,
                           OrdersService ordersService,
                           OrdersItemService ordersItemService,
                           ProductValidation productValidation,
                           TypeService typeService,
                           CategoryService categoryService) {
        this.productService = productService;
        this.userServiceImpl = userServiceImpl;
        this.ordersService = ordersService;
        this.ordersItemService = ordersItemService;
        this.productValidation = productValidation;
        this.categoryService = categoryService;
        this.typeService = typeService;
    }


    @GetMapping("/admin-page")
    public String getAdmin(Model model,
                           HttpSession session) {

        User user = userServiceImpl.findById((Long) session.getAttribute("userId")).orElse(null);
        model.addAttribute("user", user);
        return "/admin/admin-page";
    }

    @GetMapping(value = "/edit{id}")
    public Model orders(Model model,
                        @RequestParam("id") Integer id) {

        model.addAttribute("id", id);
        if (id == 1 || id == 2) {
            model.addAttribute("productDto", new ProductDto());
        } else if (id == 3) {
            model.addAttribute("userDto", new UserDto());
        } else if (id == 5) {
            model.addAttribute("typeDto", new TypeDto());
        } else if (id == 6) {
            model.addAttribute("categoryDto", new CategoryDto());
        }
        return model;
    }

    @PostMapping(value = "/save-product",
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public String createProduct(@Validated ProductDto productDto,
                                BindingResult bindingResult,
                                Model model) {

        productValidation.check(productDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("id", 1);
            return "admin/edit";
        }
        productService.saveProduct(productDto);
        return "redirect:/admin/admin-page";
    }

    @PostMapping(value = "/update-product",
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public String updateProductAll(ProductDto productDto,
                                   Model model) {
        productService.updateProduct(productDto);
        return "redirect:/admin/admin-page";
    }

    @PostMapping(value = "/update-status")
    public String updateStatus(@RequestParam("status") String status,
                               @RequestParam("id") Long id) {

        ordersService.updateStatus(status, id);
        return "redirect:/admin/admin-page";
    }

    @GetMapping("/view-product")
    public Model getProduct(Model model,
                            @RequestParam(defaultValue = "0") Integer pageNo,
                            @RequestParam(defaultValue = "4") Integer pageSize,
                            @RequestParam(defaultValue = "null") String desc,
                            @RequestParam(defaultValue = "id") String sortBy) {
        if (desc.equals("desc")) {
            model.addAttribute("products", productService.findAllProductDesc(pageNo, pageSize, sortBy))
                    .addAttribute("desc", desc);
        } else {
            model.addAttribute("products", productService.findAllProduct(pageNo, pageSize, sortBy));
        }
        model.addAttribute("pageNo", pageNo)
                .addAttribute("pageSize", pageSize)
                .addAttribute("sortBy", sortBy);
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

    @PostMapping(value = "/update-user",
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public String updateUsersAll(UserDto userDto) {
        userServiceImpl.updateUser(userDto);
        return "redirect:/admin/admin-page";
    }

    @GetMapping("/view-type")
    public Model getTypes(Model model,
                          @RequestParam(defaultValue = "0") Integer pageNo,
                          @RequestParam(defaultValue = "3") Integer pageSize,
                          @RequestParam(defaultValue = "id") String sortBy) {

        model.addAttribute("types", typeService.findAll(pageNo, pageSize, sortBy))
                .addAttribute("pageNo", pageNo);
        return model;
    }

    @PostMapping(value = "/update-type",
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public String updateUsersAll(TypeDto typeDto,
                                 Model model) {
        typeService.update(typeDto);
        return "redirect:/admin/admin-page";
    }

    @GetMapping("/view-category")
    public Model getCategory(Model model,
                             @RequestParam(defaultValue = "0") Integer pageNo,
                             @RequestParam(defaultValue = "3") Integer pageSize,
                             @RequestParam(defaultValue = "id") String sortBy) {

        model.addAttribute("categories", categoryService.findAll(pageNo, pageSize, sortBy))
                .addAttribute("pageNo", pageNo);
        return model;
    }

    @PostMapping(value = "/update-category",
            produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public String updateUsersAll(CategoryDto categoryDto,
                                 Model model) {
        categoryService.update(categoryDto);
        return "redirect:/admin/admin-page";
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
        model.addAttribute("order", id);
        return "/admin/list-products";
    }

}
