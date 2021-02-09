package root.validator;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import root.dto.UserDto;
import root.service.UserServiceImpl;

@NoArgsConstructor
@Component
@PropertySource("classpath:project.properties")
public class UserValidator implements CustomValidator<UserDto> {
    private UserServiceImpl userServiceImpl;

    @Autowired
    public UserValidator(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Value("${pr.matches.regexp}")
    String value;

    @Override
    public void check(UserDto userDto, Errors errors) {

        if (userDto.getUserName().length() == 0 || "".equals(userDto.getUserName().trim())) {
            errors.rejectValue("user_name", "79", "Имя не может быть пустым");
        } else if (userDto.getUserName().length() < 3 || userDto.getUserName().length() > 64) {
            errors.rejectValue("user_name", "80", "Имя должно содержать не меньше 3 и не больше 64 символов");
        } else if (!userDto.getUserName().matches("[a-zA-Zа-яА-Я]+")) {
            errors.rejectValue("user_name", "81", "Используйте кириллицу или латиницу");
        }

        if (userDto.getPhoneNumber().length() == 0 || "".equals(userDto.getPhoneNumber().trim())) {
            errors.rejectValue("phone_number", "89", "Поле не может быть пустым");
        } else if (!userDto.getPhoneNumber().matches(value)) {
            errors.rejectValue("phone_number", "90", "Введенный номер телефона не соответсвует требуемому формату");
        }

        if (userDto.getLoginName().length() == 0 || "".equals(userDto.getLoginName().trim())) {
            errors.rejectValue("login_name", "100", "Логин не может быть пустым");
        } else if (userServiceImpl.findByName(userDto.getLoginName()).orElse(null) != null) {
            errors.rejectValue("login_name", "101", "Пользователь с таким логином уже существует");
        } else if (!userDto.getLoginName().matches("[0-9a-zA-Z]+")) {
            errors.rejectValue("login_name", "81", "Используйте кириллицу и цифры");
        } else if (userDto.getLoginName() == null || "".equals(userDto.getLoginName().trim())) {
            errors.rejectValue("login_name", "102", "Поле не может быть пустым");
        } else if (userDto.getLoginName().length() < 3 || userDto.getLoginName().length() > 64) {
            errors.rejectValue("login_name", "103", "Логин должен содержать не меньше 3 и не больше 64 символов");
        }

        if (userDto.getPassword().length() == 0 || "".equals(userDto.getPassword().trim())) {
            errors.rejectValue("password", "103", "Пароль не может быть пустым");
        } else if (userDto.getPassword().length() < 4 || userDto.getPassword().length() > 80) {
            errors.rejectValue("password", "104", "Пароль должен содержать не меньше 4 и не больше 80 символов");
        } else if (!userDto.getPassword().matches("[0-9]+")) {
            errors.rejectValue("password", "110", "Пароль должен содержать только цифры");
        }
    }

}
