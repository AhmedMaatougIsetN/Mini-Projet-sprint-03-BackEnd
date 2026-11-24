package tn.isetn.laptopsapi.service;

import java.util.List;
import tn.isetn.laptopsapi.entities.Laptop;

public interface LaptopService {
    Laptop saveLaptop(Laptop laptop);
    Laptop updateLaptop(Long id, Laptop laptop);
    void deleteLaptopById(Long id);
    Laptop getLaptop(Long id);
    List<Laptop> getAllLaptops();
    List<Laptop> findByNomLaptopContains(String nom);
    List<Laptop> findByTypeIdType(Long idType);
}
