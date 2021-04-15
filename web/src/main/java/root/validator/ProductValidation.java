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
            errors.rejectValue("name", "10", "err.product_name_empty");
        } else if (productDto.getName().length() < 3 && productDto.getName().length() > 256) {
            errors.rejectValue("name", "11", "err.product_name_length");
        }

        if (productDto.getPrice() == null) {
            errors.rejectValue("price", "12", "err.price_empty");
        } else if (productDto.getPrice() <= 0.0) {
            errors.rejectValue("price", "13", "err.price_valid");
        }

        if (productDto.getDescribe().length() == 0 || "".equals(productDto.getName().trim())) {
            errors.rejectValue("describe", "15", "err.desc_empty");
        } else if (productDto.getDescribe().length() < 3 && productDto.getDescribe().length() > 560) {
            errors.rejectValue("describe", "16", "err.desc_length");
        }

        if (productDto.getCount() == null) {
            errors.rejectValue("count", "17", "err.count_empty");
        } else if (productDto.getPrice() <= 0) {
            errors.rejectValue("count", "18", "err.count_valid");
        }

        if (productDto.getMaker_id() == null) {
            errors.rejectValue("maker_id", "20", "err.maker_empty");
        } else if (productDto.getMaker_id() <= 0) {
            errors.rejectValue("maker_id", "21", "err.maker_id_valid");
        } else if (!makerService.existsById(productDto.getMaker_id())) {
            errors.rejectValue("maker_id", "23", "err.maker_exist");
        }

        if (productDto.getType_id() == null) {
            errors.rejectValue("type_id", "24", "err.type_empty");
        } else if (productDto.getType_id() <= 0) {
            errors.rejectValue("type_id", "25", "err.type_id_valid");
        } else if (!typeService.existsById(productDto.getType_id())) {
            errors.rejectValue("type_id", "27", "err.type_exist");
        }
    }

}
