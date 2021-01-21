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

    @Autowired
    public ProductService(ProductRepository productRepository,
                          MakerService makerService,
                          TypeService typeService) {
        this.productRepository = productRepository;
        this.makerService = makerService;
        this.typeService = typeService;
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

    @Transactional
    public void incrementProduct(Product product) {
        int count = productRepository.findById(product.getId()).get().getCount() + 1;
        productRepository.updateProductCount(count, product.getId());
    }

    @Transactional
    public void decrementProduct(Product product) {
        int count = product.getCount() - 1;
        productRepository.updateProductCount(count, product.getId());
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product saveProduct(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .count(productDto.getCount())
                .description(productDto.getDescribe())
                .price(BigDecimal.valueOf(productDto.getPrice()))
                .maker(makerService.findById(productDto.getMaker_id()))
                .type(typeService.findById(productDto.getType_id()))
                .build();
        return save(product);
    }

    @Transactional
    public void updatePrice(BigDecimal price, Long id) {
        productRepository.updatePrice(price, id);
    }

}
