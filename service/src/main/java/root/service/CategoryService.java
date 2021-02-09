package root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.CategoryDto;
import root.entity.Category;
import root.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public List<Category> findAll(int pageN, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageN, pageSize, Sort.by(sortBy));
        return categoryRepository.findAll(pageable);
    }

    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Transactional
    public void update(CategoryDto categoryDto) {
        Category category = findById(categoryDto.getId()).orElse(null);
        if (category != null) {
            if (!categoryDto.getName().equals("")) {
                category.setName(categoryDto.getName());
            }
            save(category);
        }
    }

}
