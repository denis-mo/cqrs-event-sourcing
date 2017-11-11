package com.cqrs.rest;

import com.cqrs.cqrs.EventBus;
import com.cqrs.domain.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("customer/{customerId}/get-order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderQueryResource {

    private Orders orders;
    private Films films;

    public OrderQueryResource(EventBus eventBus) {
        this.orders = new Orders(eventBus);
        this.films = new Films(eventBus);
    }

    @GET
    public OrderDto getOrder(@PathParam("customerId") String customerId) {
        return toDto(orders.getByCustomer(UUID.fromString(customerId)));
    }

    private OrderDto toDto(OrderProjection order) {
        OrderDto orderDto = new OrderDto();
        orderDto.id = order.id;
        orderDto.days = order.days;
        orderDto.items = order.items.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return orderDto;
    }

    private OrderItemDto toDto(OrderProjectionItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.name = films.get(orderItem.filmId).name;
        return orderItemDto;
    }
}
