package root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.converter.UserDetailsConverter;
import root.dto.UserDto;
import root.entity.Orders;
import root.entity.Product;
import root.entity.User;
import root.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProductService productService;
    private final UserDetailsConverter detailsConverter;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserDetailsConverter detailsConverter,
                           ProductService productService,
                           RoleService roleService,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.detailsConverter = detailsConverter;
        this.productService = productService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.findByName(name)
                .map(detailsConverter::convert)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User saveUser(UserDto userDto) {
        User user = User.builder()
                .user_name(userDto.getUser_name())
                .name(userDto.getLogin_name())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .phone_number(userDto.getPhone_number())
                .role(roleService.findByName("USER"))
                .build();
        return save(user);
    }

    @Transactional
    public void addProductInBasket(Long id, Long productId) {
        User userOne = userRepository.findById(id).orElse(null);
        Product product = productService.findById(productId).orElse(null);
        if (userOne != null && product != null) {
            productService.decrementProduct(product);
            userOne.getProducts().add(product);
        }
    }

    @Transactional
    public void dellProductOfBasket(Long userId, Long productId) {
        User user = findById(userId).orElse(null);
        Product product = productService.findById(productId).orElse(null);
        if (user != null && product != null) {
            user.getProducts().remove(product);
            productService.incrementProduct(product);
        }
    }

    @Transactional
    public void dellAllProductOfBasket(User user) {
        User us = findByName(user.getName()).orElse(null);
        if (us != null) {
            us.getProducts().clear();
        }
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Transactional
    public void addOrderForUser(User user, Orders order) {
        User us = userRepository.findByName(user.getName()).orElse(null);
        if (us != null) {
            us.getOrders().add(order);
        }
    }

    public List<User> findAll(int pageN, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageN, pageSize, Sort.by(sortBy));
        List<User> users = userRepository.findAll(pageable);
        return users;
    }

    public Optional<User>  findById(Long id){
        return userRepository.findById(id);
    }

}
