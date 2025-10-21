package org.sid.billingmicroservice.feign;


import org.sid.billingmicroservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "inventory-microservice")
public interface ProductRestClient {
    @GetMapping("/api/products/{id}")
    Product getProductById(@PathVariable String id);
    @GetMapping("/api/products")
    PagedModel<Product> getAllProducts();
}
