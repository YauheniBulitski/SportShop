package root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.entity.Type;
import root.repository.TypeRepository;

import java.util.List;

@Service
public class TypeService {

    private final TypeRepository typeRepository;

    @Autowired
    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
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

    public Boolean existsById(Integer id){
        return typeRepository.existsById(id);
    }
}
