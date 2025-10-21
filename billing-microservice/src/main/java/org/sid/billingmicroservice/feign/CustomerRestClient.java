package org.sid.billingmicroservice.feign;

import org.sid.billingmicroservice.model.Customer;
import org.sid.billingmicroservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient( name = "customer-microservice")
public interface CustomerRestClient {
    @GetMapping("/api/customers/{id}")
    Customer findCustomerById(@PathVariable Long id);
    @GetMapping("/api/customers")
    PagedModel<Customer> getAllCustomers();
}
