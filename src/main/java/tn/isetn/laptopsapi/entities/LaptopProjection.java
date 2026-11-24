package tn.isetn.laptopsapi.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "laptopResume", types = Laptop.class)
public interface LaptopProjection {
    Long getIdlaptop();
    String getNomlaptop();
    Double getPrixlaptop();
}
