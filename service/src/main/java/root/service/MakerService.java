package root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.entity.Maker;
import root.repository.MakerRepository;

@Service
public class MakerService {

    private final MakerRepository makerRepository;

    @Autowired
    public MakerService(MakerRepository makerRepository) {
        this.makerRepository = makerRepository;
    }

    @Transactional
    public Maker save(Maker maker) {
        return makerRepository.save(maker);
    }

    public Maker findById(Integer id) {
        return makerRepository.findById(id).orElse(null);
    }

    public Boolean existsById(Integer id) {
        return makerRepository.existsById(id);
    }
}
