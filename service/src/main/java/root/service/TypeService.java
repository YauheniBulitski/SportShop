package root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.TypeDto;
import root.entity.Category;
import root.entity.Type;
import root.repository.TypeRepository;

import java.util.List;

@Service
public class TypeService {

    private final TypeRepository typeRepository;
    private final CategoryService categoryService;

    @Autowired
    public TypeService(TypeRepository typeRepository,
                       CategoryService categoryService) {
        this.typeRepository = typeRepository;
        this.categoryService = categoryService;
    }

    @Transactional
    public List<Type> findAllByCategoryId(Integer id) {
        List<Type> types = typeRepository.findAllByCategoryId(id);
        return types;
    }

    @Transactional
    public Type save(Type type) {
        return typeRepository.save(type);
    }

    public Type findById(Integer id) {
        return typeRepository.findById(id).orElse(null);
    }

    public Boolean existsById(Integer id) {
        return typeRepository.existsById(id);
    }

    public List<Type> findAll(int pageN, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageN, pageSize, Sort.by(sortBy));
        return typeRepository.findAll(pageable);
    }

    @Transactional
    public void update(TypeDto typeDto) {
        Type type = findById(typeDto.getId());
        if(type != null) {
            if (!typeDto.getName().equals("")) {
                type.setName(typeDto.getName());
            }
            if (typeDto.getCategoryId() != 0) {
                Category category = categoryService.findById(typeDto.getCategoryId()).orElse(null);
                if (category != null) {
                    type.setCategory(category);
                }
            }
            save(type);
        }
    }
}
