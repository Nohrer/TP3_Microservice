package org.sid.billingmicroservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.sid.billingmicroservice.model.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity @AllArgsConstructor @NoArgsConstructor @Getter
@Setter @Builder @ToString
public class Bill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date billingDate;
    private long customerId;
    @OneToMany(mappedBy = "bill")
    private List<ProductItem> productItems = new ArrayList<>();
    @Transient private Customer customer;
}
