package root.validator;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import root.dto.ProductDto;
import root.service.MakerService;
import root.service.ProductService;
import root.service.TypeService;

@NoArgsConstructor
@Component
public class ProductValidation implements CustomValidator<ProductDto> {

    private ProductService productService;
    private MakerService makerService;
    private TypeService typeService;

    @Autowired
    public ProductValidation(ProductService productService,
                             MakerService makerService,
                             TypeService typeService) {
        this.productService = productService;
        this.makerService = makerService;
        this.typeService = typeService;
    }

    @Override
    public void check(ProductDto productDto, Errors errors) {

        if (productDto.getName().length() == 0 || "".equals(productDto.getName().trim())) {
            errors.rejectValue("name", "10", "Название не может быть пустым");
        } else if (productDto.getName().length() < 3 && productDto.getName().length() > 256) {
            errors.rejectValue("name", "11", "Название должно содержать не меньше 3 и не больше 256 символов");
        }

        if (productDto.getPrice() == null) {
            errors.rejectValue("price", "12", "Поле цена не может быть пустым");
        } else if (productDto.getPrice() <= 0.0) {
            errors.rejectValue("price", "13", "Цена не может быть ровна '0' или быть отрицательной");
        }

        if (productDto.getDescribe().length() == 0 || "".equals(productDto.getName().trim())) {
            errors.rejectValue("describe", "15", "Описание не может быть пустым");
        } else if (productDto.getDescribe().length() < 3 && productDto.getDescribe().length() > 560) {
            errors.rejectValue("describe", "16", "Название должно содержать не меньше 3 и не больше 560 символов");
        }

        if (productDto.getCount() == null) {
            errors.rejectValue("count", "17", "Поле количество не может быть пустым");
        } else if (productDto.getPrice() <= 0) {
            errors.rejectValue("count", "18", "Колличество не может быть ровна '0' или быть отрицательным");
        }

        if (productDto.getMaker_id() == null) {
            errors.rejectValue("maker_id", "20", "Поле производитель не может быть пустым");
        } else if (productDto.getMaker_id() <= 0) {
            errors.rejectValue("maker_id", "21", "Id производителя не может быть ровна '0' или быть отрицательным");
        } else if (!makerService.existsById(productDto.getMaker_id())) {
            errors.rejectValue("maker_id", "23", "Производителя с данным Id не существует");
        }

        if (productDto.getType_id() == null) {
            errors.rejectValue("type_id", "24", "Поле тип не может быть пустым");
        } else if (productDto.getType_id() <= 0) {
            errors.rejectValue("type_id", "25", "Id типа не может быть ровна '0' или быть отрицательным");
        } else if (!typeService.existsById(productDto.getType_id())) {
            errors.rejectValue("type_id", "27", "Тип с данным Id не существует");
        }
    }
}
