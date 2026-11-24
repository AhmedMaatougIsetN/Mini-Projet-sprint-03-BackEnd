package tn.isetn.laptopsapi.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import tn.isetn.laptopsapi.entities.Laptop;
import tn.isetn.laptopsapi.entities.Type;
import tn.isetn.laptopsapi.repos.LaptopRepository;
import tn.isetn.laptopsapi.repos.TypeRepository;

@Service
@Transactional
public class LaptopServiceImpl implements LaptopService {
    private final LaptopRepository laptopRepository;
    private final TypeRepository typeRepository;

    public LaptopServiceImpl(LaptopRepository laptopRepository, TypeRepository typeRepository) {
        this.laptopRepository = laptopRepository;
        this.typeRepository = typeRepository;
    }

    @Override public Laptop saveLaptop(Laptop laptop) {
        resolveType(laptop);
        laptop.setIdlaptop(null);
        return laptopRepository.save(laptop);
    }

    @Override public Laptop updateLaptop(Long id, Laptop laptop) {
        Laptop current = getLaptop(id);
        current.setNomlaptop(laptop.getNomlaptop());
        current.setPrixlaptop(laptop.getPrixlaptop());
        current.setDateCreation(laptop.getDateCreation());
        current.setType(laptop.getType());
        resolveType(current);
        return laptopRepository.save(current);
    }

    private void resolveType(Laptop laptop) {
        if (laptop.getType() == null || laptop.getType().getIdType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un type est obligatoire");
        }
        Type type = typeRepository.findById(laptop.getType().getIdType())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Type introuvable"));
        laptop.setType(type);
    }

    @Override public void deleteLaptopById(Long id) {
        if (!laptopRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Laptop introuvable");
        laptopRepository.deleteById(id);
    }
    @Override @Transactional(readOnly = true) public Laptop getLaptop(Long id) {
        return laptopRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Laptop introuvable"));
    }
    @Override @Transactional(readOnly = true) public List<Laptop> getAllLaptops() { return laptopRepository.findAll(); }
    @Override @Transactional(readOnly = true) public List<Laptop> findByNomLaptopContains(String nom) { return laptopRepository.findByNomlaptopContains(nom); }
    @Override @Transactional(readOnly = true) public List<Laptop> findByTypeIdType(Long idType) { return laptopRepository.findByType_IdType(idType); }
}
