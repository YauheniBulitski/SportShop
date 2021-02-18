package root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.entity.Count;
import root.entity.Product;
import root.repository.CountRepository;

@Service
public class CountService {

    private final CountRepository countRepository;

    @Autowired
    public CountService(CountRepository countRepository) {
        this.countRepository = countRepository;
    }

    public Count save(int count, Product product) {
        return countRepository.save(Count.builder()
                .count(count)
                .product(product)
                .build());
    }

    public Count findByProductId(Long id) {
        return countRepository.findByProductId(id).get();
    }

    public void updateCount(Integer count, Product product) {
        countRepository.updateCount(count, product);
    }

    @Transactional
    public Integer incrementProduct(Product product) {
        int count = findByProductId(product.getId()).getCount() + 1;
        updateCount(count, product);
        return count;
    }

    @Transactional
    public Integer decrementProduct(Product product) {
        int count = findByProductId(product.getId()).getCount() - 1;
        updateCount(count, product);
        return count;
    }
}
