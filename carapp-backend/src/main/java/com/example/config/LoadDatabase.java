package com.example.config;


import com.example.model.Car;
import com.example.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CarRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Car("Tesla", "Model X", "Test Model X desc", true)));
            log.info("Preloading " + repository.save(new Car("Tesla", "Model Y", "Test Model Y desc", true)));
        };
    }
}