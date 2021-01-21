package root.converter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import root.entity.User;

@Component
public class UserDetailsConverter implements Converter<User, UserDetails> {
    @Override
    public UserDetails convert(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getName())
                .password(user.getPassword())
                .authorities(user.getRole().getName())
                .build();
    }
}
