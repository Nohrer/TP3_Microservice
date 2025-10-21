package org.sid.billingmicroservice.repository;

import org.sid.billingmicroservice.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {

}
