package tn.isetn.laptopsapi.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tn.isetn.laptopsapi.entities.Type;

@CrossOrigin(origins = "http://localhost:4200")
@RepositoryRestResource(path = "cat", collectionResourceRel = "types", itemResourceRel = "type")
public interface TypeRepository extends JpaRepository<Type, Long> {
}
