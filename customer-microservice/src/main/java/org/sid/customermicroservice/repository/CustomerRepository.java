package org.sid.customermicroservice.repository;

import org.sid.customermicroservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//this notation give us access to all methods present in this interface
@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
