package tn.isetn.laptopsapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idType;
    private String nomType;
    @JsonIgnore
    @OneToMany(mappedBy = "type")
    private List<Laptop> laptops = new ArrayList<>();

    public Type() {}
    public Type(String nomType) { this.nomType = nomType; }
    public Long getIdType() { return idType; }
    public void setIdType(Long idType) { this.idType = idType; }
    public String getNomType() { return nomType; }
    public void setNomType(String nomType) { this.nomType = nomType; }
    public List<Laptop> getLaptops() { return laptops; }
    public void setLaptops(List<Laptop> laptops) { this.laptops = laptops; }
}
