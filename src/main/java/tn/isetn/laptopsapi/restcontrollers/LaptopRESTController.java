package tn.isetn.laptopsapi.restcontrollers;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.isetn.laptopsapi.entities.Laptop;
import tn.isetn.laptopsapi.service.LaptopService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class LaptopRESTController {
    private final LaptopService laptopService;
    public LaptopRESTController(LaptopService laptopService) { this.laptopService = laptopService; }
    @GetMapping public List<Laptop> getAllLaptops() { return laptopService.getAllLaptops(); }
    @GetMapping("/{id}") public Laptop getLaptopById(@PathVariable Long id) { return laptopService.getLaptop(id); }
    @PostMapping public Laptop createLaptop(@RequestBody Laptop laptop) { return laptopService.saveLaptop(laptop); }
    @PutMapping("/{id}") public Laptop updateLaptop(@PathVariable Long id, @RequestBody Laptop laptop) { return laptopService.updateLaptop(id, laptop); }
    @DeleteMapping("/{id}") public void deleteLaptop(@PathVariable Long id) { laptopService.deleteLaptopById(id); }
    @GetMapping("/prodscat/{idType}") public List<Laptop> getLaptopsByType(@PathVariable Long idType) { return laptopService.findByTypeIdType(idType); }
    @GetMapping("/prodsnom") public List<Laptop> getLaptopsByName(@RequestParam String nom) { return laptopService.findByNomLaptopContains(nom); }
}
