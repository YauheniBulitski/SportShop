package root.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import root.entity.User;

public interface UserService extends UserDetailsService {

    User save(User user);
}
