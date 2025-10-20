package org.sid.customermicroservice;

import org.sid.customermicroservice.entities.Customer;
import org.sid.customermicroservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerMicroserviceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository repository) {
        return args -> {
            repository.save(Customer.builder()
                    .name("Simo").email("med@gmail.com")
            .build()
            );
            repository.save(Customer.builder()
                    .name("Bimo").email("bimo@gmail.com")
                    .build()
            );
            repository.save(Customer.builder()
                    .name("Nimo").email("nimo@gmail.com")
                    .build()
            );
            repository.findAll().forEach(System.out::println);
        };
    }
}
