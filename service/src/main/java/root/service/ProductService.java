package root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.ProductDto;
import root.entity.Product;
import root.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MakerService makerService;
    private final TypeService typeService;
    private final CountService countService;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          MakerService makerService,
                          TypeService typeService,
                          CountService countService) {
        this.productRepository = productRepository;
        this.makerService = makerService;
        this.typeService = typeService;
        this.countService = countService;
    }

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }


    public List<Product> findAllByTypeId(Integer id, int pageN, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageN, pageSize, Sort.by(sortBy));
        List<Product> products = productRepository.findAllByTypeId(id, pageable);
        return products;
    }

    public List<Product> findAllByTypeIdDesc(Integer id, int pageN, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageN, pageSize, Sort.by(sortBy).descending());
        List<Product> products = productRepository.findAllByTypeId(id, pageable);
        return products;
    }
    public List<Product> findAllMatch(String name, int pageN, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageN, pageSize, Sort.by(sortBy));
        List<Product> products = productRepository.findAllMatch(name.toUpperCase(), pageable);
        return products;
    }

    public List<Product> findAllProduct(int pageN, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageN, pageSize, Sort.by(sortBy));
        List<Product> products = productRepository.findAll(pageable);
        return products;
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product saveProduct(ProductDto productDto) {
        Product product = save(Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescribe())
                .price(BigDecimal.valueOf(productDto.getPrice()))
                .maker(makerService.findById(productDto.getMaker_id()))
                .type(typeService.findById(productDto.getType_id()))
                .url("/resources/images/" + (productDto.getUrl().equals("") ? "def" : productDto.getUrl()) + ".jpg")
                .build());
        countService.save(productDto.getCount(), product);
        return product;
    }

    @Transactional
    public Product updateProduct(ProductDto productDto) {
        Product product = findById(productDto.getId()).get();

        if (!productDto.getName().equals("")) {
            product.setName(productDto.getName());
        }
        if (!productDto.getDescribe().equals("")) {
            product.setDescription(productDto.getDescribe());
        }
        if (productDto.getCount() != null) {
            countService.updateCount(productDto.getCount(), product);
        }
        if (productDto.getPrice() != null) {
            product.setPrice(BigDecimal.valueOf(productDto.getPrice()));
        }
        if (productDto.getMaker_id() != null) {
            product.setMaker(makerService.findById(productDto.getMaker_id()));
        }
        if (productDto.getType_id() != null) {
            product.setType(typeService.findById(productDto.getType_id()));
        }
        if (!productDto.getUrl().equals("")) {
            product.setUrl("/resources/images/" + productDto.getUrl() + ".jpg");
        }
        return save(product);
    }


}
