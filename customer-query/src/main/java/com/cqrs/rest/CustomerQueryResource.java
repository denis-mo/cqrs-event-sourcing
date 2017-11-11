package com.cqrs.rest;

import com.cqrs.cqrs.EventBus;
import com.cqrs.domain.CustomerProjection;
import com.cqrs.domain.Customers;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("customer/{customerId}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerQueryResource {

    private Customers customers;

    public CustomerQueryResource(EventBus eventBus) {
        this.customers = new Customers(eventBus);
    }

    @GET
    public CustomerDto getCustomer(@PathParam("customerId") String customerId) {
        return toDto(customers.get(UUID.fromString(customerId)));
    }

    public CustomerDto toDto(CustomerProjection customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.id = customer.id;
        customerDto.balance = customer.balance;
        customerDto.bonusPoints = customer.bonusPoints;
        return customerDto;
    }
}
