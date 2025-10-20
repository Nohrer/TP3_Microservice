package org.sid.inventorymicroservice;

import org.sid.inventorymicroservice.entities.Product;
import org.sid.inventorymicroservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class InventoryMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryMicroserviceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            productRepository.save(
                    Product.builder()
                    .id(UUID.randomUUID().toString())
                    .name("Product 1")
                    .price(200)
                    .quantity(5)
                    .build()
            );
            productRepository.save(
                    Product.builder()
                            .id(UUID.randomUUID().toString())
                            .name("Product 2")
                            .price(300)
                            .quantity(10)
                            .build()
            );
            productRepository.save(
                    Product.builder()
                            .id(UUID.randomUUID().toString())
                            .name("Product 3")
                            .price(700)
                            .quantity(12)
                            .build()
            );
        };
    }
}
