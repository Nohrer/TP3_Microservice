package org.sid.billingmicroservice;

import org.sid.billingmicroservice.entities.Bill;
import org.sid.billingmicroservice.entities.ProductItem;
import org.sid.billingmicroservice.feign.CustomerRestClient;
import org.sid.billingmicroservice.feign.ProductRestClient;
import org.sid.billingmicroservice.model.Customer;
import org.sid.billingmicroservice.model.Product;
import org.sid.billingmicroservice.repository.BillRepository;
import org.sid.billingmicroservice.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
@EnableFeignClients
public class BillingMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingMicroserviceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BillRepository billRepository,
                                        ProductItemRepository productItemRepository,
                                        CustomerRestClient customerRestClient,
                                        ProductRestClient productRestClient) {
        return args -> {
        Collection<Customer> customers = customerRestClient.getAllCustomers().getContent();
        Collection<Product> products = productRestClient.getAllProducts().getContent();
        customers.forEach(customer -> {
            Bill bill = Bill.builder()
                    .billingDate(new Date())
                    .customerId(customer.getId())
                    .build();
            billRepository.save(bill);
            products.forEach(product -> {
                ProductItem productItem = ProductItem.builder()
                        .bill(bill)
                        .productId(product.getId())
                        .quantity(1 + new Random().nextInt(10))
                        .unitPrice(product.getPrice())
                        .build();
                productItemRepository.save(productItem);
            });
        });
    };
}
}
