package root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import root.entity.User;
import root.service.ProductService;
import root.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class BasketController {

    private final ProductService productService;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public BasketController(ProductService productService, UserServiceImpl userServiceImpl) {
        this.productService = productService;
        this.userServiceImpl = userServiceImpl;
    }

    @RequestMapping(value = "/addInBasket", method = RequestMethod.POST)
    public String addProductInBasket(HttpServletRequest request,
                                     @ModelAttribute("product") Long id,
                                     HttpSession session) {

        userServiceImpl.addProductInBasket((Long)session.getAttribute("userId"), id);
        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/basket")
    public String basket(Model model,
                         HttpSession session) {
        User user = userServiceImpl.findById((Long)session.getAttribute("userId")).orElse(null);
        if(user!=null) {
            model.addAttribute("products", user.getProducts());
        }
        return "basket";
    }

    @RequestMapping(value = "/dellProduct", method = RequestMethod.POST)
    public String dellBook(@ModelAttribute("product") Long id,
                           HttpServletRequest request,
                           HttpSession session) {

        userServiceImpl.dellProductOfBasket((Long)session.getAttribute("userId"), id);
        return "redirect:" + request.getHeader("referer");
    }
}
