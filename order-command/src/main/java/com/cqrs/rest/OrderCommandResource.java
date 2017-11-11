package com.cqrs.rest;

import com.cqrs.command.ReturnOrderCommand;
import com.cqrs.cqrs.CommandBus;
import com.cqrs.command.AddFilmToOrderCommand;
import com.cqrs.command.CreateOrderCommand;
import com.cqrs.command.PlaceOrderCommand;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("customer/{customerId}/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderCommandResource {

    private CommandBus commandBus;

    public OrderCommandResource(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @POST
    public void createOrder(@PathParam("customerId") String customerId) {
        commandBus.dispatch(new CreateOrderCommand(UUID.fromString(customerId)));
    }

    @POST
    @Path("{orderId}")
    public void addFilm(@PathParam("orderId") String orderId, AddFilmRequestDto requestDto) {
        commandBus.dispatch(new AddFilmToOrderCommand(UUID.fromString(orderId), UUID.fromString(requestDto.filmId)));
    }

    @POST
    @Path("{orderId}/place")
    public void placeOrder(@PathParam("orderId") String orderId, PlaceOrderRequestDto requestDto) {
        commandBus.dispatch(new PlaceOrderCommand(UUID.fromString(orderId), requestDto.days));
    }

    @POST
    @Path("{orderId}/return")
    public void returnOrder(@PathParam("orderId") String orderId) {
        commandBus.dispatch(new ReturnOrderCommand(UUID.fromString(orderId)));
    }
}
