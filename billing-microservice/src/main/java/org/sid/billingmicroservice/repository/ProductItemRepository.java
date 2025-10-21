package org.sid.billingmicroservice.repository;

import org.sid.billingmicroservice.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
}
