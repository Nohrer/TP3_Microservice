package org.sid.customermicroservice.entities;
import org.springframework.data.rest.core.config.Projection;
// Projection  are used so we can choose what data we want to get specifically
// the name given is the one that will be displayed in the link to get this data
@Projection(name = "all", types = Customer.class)
public interface CustomerProjection {
    String getName();
    String getEmail();
}
