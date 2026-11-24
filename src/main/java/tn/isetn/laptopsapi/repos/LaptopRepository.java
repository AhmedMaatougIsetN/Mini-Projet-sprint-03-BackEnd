package tn.isetn.laptopsapi.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.repository.query.Param;
import tn.isetn.laptopsapi.entities.Laptop;
import tn.isetn.laptopsapi.entities.Type;

@RepositoryRestResource(path = "rest", collectionResourceRel = "laptops", itemResourceRel = "laptop")
public interface LaptopRepository extends JpaRepository<Laptop, Long> {
    List<Laptop> findByNomlaptop(String nomlaptop);
    @RestResource(path = "findByNomlaptopContains", rel = "findByNomlaptopContains")
    List<Laptop> findByNomlaptopContains(@Param("nomlaptop") String nomlaptop);
    List<Laptop> findByType(Type type);
    @RestResource(path = "findByTypeIdType", rel = "findByTypeIdType")
    List<Laptop> findByType_IdType(@Param("idType") Long idType);
    List<Laptop> findByOrderByNomlaptopAsc();
}
