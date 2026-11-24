package tn.isetn.laptopsapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import tn.isetn.laptopsapi.entities.Laptop;
import tn.isetn.laptopsapi.entities.Type;

@Configuration
public class RepositoryRestConfig implements RepositoryRestConfigurer {
    @Override public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(Laptop.class, Type.class);
        cors.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS").allowedHeaders("*").exposedHeaders("Authorization", "Location");
    }
}
