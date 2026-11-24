package tn.isetn.laptopsapi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idlaptop;
    private String nomlaptop;
    private Double prixlaptop;
    private LocalDate dateCreation;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_type", nullable = false)
    private Type type;

    public Laptop() {}
    public Laptop(String nomlaptop, Double prixlaptop, LocalDate dateCreation, Type type) {
        this.nomlaptop = nomlaptop; this.prixlaptop = prixlaptop; this.dateCreation = dateCreation; this.type = type;
    }
    public Long getIdlaptop() { return idlaptop; }
    public void setIdlaptop(Long idlaptop) { this.idlaptop = idlaptop; }
    public String getNomlaptop() { return nomlaptop; }
    public void setNomlaptop(String nomlaptop) { this.nomlaptop = nomlaptop; }
    public Double getPrixlaptop() { return prixlaptop; }
    public void setPrixlaptop(Double prixlaptop) { this.prixlaptop = prixlaptop; }
    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }
    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
}
