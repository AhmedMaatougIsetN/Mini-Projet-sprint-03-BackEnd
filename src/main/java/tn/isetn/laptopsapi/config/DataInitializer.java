package tn.isetn.laptopsapi.config;

import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tn.isetn.laptopsapi.entities.Laptop;
import tn.isetn.laptopsapi.entities.Role;
import tn.isetn.laptopsapi.entities.Type;
import tn.isetn.laptopsapi.entities.User;
import tn.isetn.laptopsapi.repos.LaptopRepository;
import tn.isetn.laptopsapi.repos.RoleRepository;
import tn.isetn.laptopsapi.repos.TypeRepository;
import tn.isetn.laptopsapi.repos.UserRepository;
import tn.isetn.laptopsapi.service.UserService;

@Component
public class DataInitializer implements CommandLineRunner {
    private final TypeRepository typeRepository;
    private final LaptopRepository laptopRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public DataInitializer(TypeRepository typeRepository, LaptopRepository laptopRepository, RoleRepository roleRepository,
                           UserRepository userRepository, UserService userService) {
        this.typeRepository = typeRepository; this.laptopRepository = laptopRepository;
        this.roleRepository = roleRepository; this.userRepository = userRepository; this.userService = userService;
    }

    @Override @Transactional public void run(String... args) {
        if (typeRepository.count() == 0) {
            typeRepository.saveAll(List.of(new Type("Gaming laptop"), new Type("Professional Laptop"), new Type("MacBooks")));
        }
        typeRepository.findAll().stream()
            .filter(type -> "Gming laptop".equals(type.getNomType()))
            .forEach(type -> type.setNomType("Gaming laptop"));
        if (laptopRepository.count() == 0) {
            List<Type> types = typeRepository.findAll();
            laptopRepository.saveAll(List.of(
                new Laptop("Lenovo Legion 5", 4799.20, LocalDate.of(2011, 1, 14), types.get(0)),
                new Laptop("ASUS Zenbook 14", 3450.0, LocalDate.of(2010, 12, 17), types.get(1)),
                new Laptop("MacBook Air M1", 2499.0, LocalDate.of(2020, 2, 20), types.get(2))
            ));
        }
        Role admin = roleRepository.findByRole("ADMIN").orElseGet(() -> roleRepository.save(new Role("ADMIN")));
        Role user = roleRepository.findByRole("USER").orElseGet(() -> roleRepository.save(new Role("USER")));
        if (userRepository.findByUsername("admin").isEmpty()) userService.saveUser(new User("admin", "123", true));
        if (userRepository.findByUsername("user").isEmpty()) userService.saveUser(new User("user", "123", true));
        userService.addRoleToUser("admin", admin.getRole());
        userService.addRoleToUser("user", user.getRole());
    }
}
