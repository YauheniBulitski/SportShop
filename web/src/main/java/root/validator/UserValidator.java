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
            errors.rejectValue("userName", "79", "err.name_empty");
        } else if (userDto.getUserName().length() < 3 || userDto.getUserName().length() > 64) {
            errors.rejectValue("userName", "80", "err.name_contains");
        } else if (!userDto.getUserName().matches("[a-zA-Zа-яА-Я]+")) {
            errors.rejectValue("userName", "81", "err.name_letters");
        }

        if (userDto.getPhoneNumber().length() == 0 || "".equals(userDto.getPhoneNumber().trim())) {
            errors.rejectValue("phoneNumber", "89", "err.phone_empty");
        } else if (!userDto.getPhoneNumber().matches(value)) {
            errors.rejectValue("phoneNumber", "90", "err.phone_no_format");
        }

        if (userDto.getLoginName().length() == 0 || "".equals(userDto.getLoginName().trim())) {
            errors.rejectValue("loginName", "100", "err.login.name_empty");
        } else if (userServiceImpl.findByName(userDto.getLoginName()).orElse(null) != null) {
            errors.rejectValue("loginName", "101", "err.login.name_busy");
        } else if (!userDto.getLoginName().matches("[0-9a-zA-Z]+")) {
            errors.rejectValue("loginName", "81", "err.login.name_letters");
        } else if (userDto.getLoginName() == null || "".equals(userDto.getLoginName().trim())) {
            errors.rejectValue("loginName", "102", "err.login.name_empty");
        } else if (userDto.getLoginName().length() < 3 || userDto.getLoginName().length() > 64) {
            errors.rejectValue("loginName", "103", "err.login.name_length");
        }

        if (userDto.getPassword().length() == 0 || "".equals(userDto.getPassword().trim())) {
            errors.rejectValue("password", "103", "err.pass_empty");
        } else if (userDto.getPassword().length() < 4 || userDto.getPassword().length() > 80) {
            errors.rejectValue("password", "104", "err.pass_length");
        } else if (!userDto.getPassword().matches("[0-9]+")) {
            errors.rejectValue("password", "110", "err.pass_contains");
        }
    }

}
