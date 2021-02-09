package root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import root.entity.Product;
import root.entity.User;
import root.service.CategoryService;
import root.service.ProductService;
import root.service.TypeService;
import root.service.UserServiceImpl;
import root.utils.BaseHelper;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ShopController {

    private final ProductService productService;
    private final TypeService typeService;
    private final CategoryService categoryService;
    private final BaseHelper baseHelper;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public ShopController(ProductService productService,
                          TypeService typeService,
                          CategoryService categoryService,
                          BaseHelper baseHelper,
                          UserServiceImpl userServiceImpl) {
        this.productService = productService;
        this.typeService = typeService;
        this.categoryService = categoryService;
        this.baseHelper = baseHelper;
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/creat")
    public String creat() {
        baseHelper.data();
        return "redirect:shop";
    }

    @GetMapping("/shop")
    public String getCategory(Model model, HttpSession session) {

        if (session.getAttribute("id") == null) {
            if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
                String name = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userServiceImpl.findByName(name).get();
                session.setAttribute("userId", user.getId());
            }
        }
        model.addAttribute("categories", categoryService.findAll());
        return "shop";
    }

    @GetMapping("/type{categoryId}")
    public Model getTypeByCategory(Model model,
                                   @RequestParam("categoryId") Integer categoryId) {

        model.addAttribute("types", typeService.findAllByCategoryId(categoryId))
                .addAttribute("categoryId", categoryId);
        return model;
    }

    @GetMapping("/product-type{typeId}")
    public Model getProductByType(Model model,
                                  @RequestParam(defaultValue = "0") Integer pageNo,
                                  @RequestParam(defaultValue = "4") Integer pageSize,
                                  @RequestParam(defaultValue = "id") String sortBy,
                                  @RequestParam("typeId") Integer typeId) {

        model.addAttribute("products", productService.findAllByTypeId(typeId, pageNo, pageSize, sortBy))
                .addAttribute("pageNo", pageNo)
                .addAttribute("typeId", typeId);
        return model;
    }

    @GetMapping("/find-product")
    public Model getProduct(Model model,
                            @RequestParam(defaultValue = "0") Integer pageNo,
                            @RequestParam(defaultValue = "4") Integer pageSize,
                            @RequestParam(defaultValue = "id") String sortBy,
                            @RequestParam("name") String name) {

        List<Product> products = productService.findAllMatch(name, pageNo, pageSize, sortBy);
        model.addAttribute("products", products);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("name", name);
        return model;
    }

}
