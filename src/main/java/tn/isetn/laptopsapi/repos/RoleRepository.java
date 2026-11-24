package tn.isetn.laptopsapi.repos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import tn.isetn.laptopsapi.entities.Role;

@RepositoryRestResource(exported = false)
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(String role);
}
