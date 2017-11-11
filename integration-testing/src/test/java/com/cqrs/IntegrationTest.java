package com.cqrs;

import com.cqrs.command.*;
import com.cqrs.cqrs.*;
import com.cqrs.domain.FilmType;
import com.cqrs.event.CustomerEventHandler;
import com.cqrs.event.OrderEventHandler;
import com.cqrs.events.CustomerCreatedEvent;
import com.cqrs.events.FilmCreatedEvent;
import com.cqrs.events.OrderCreatedEvent;
import org.junit.Assert;
import org.junit.Before;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class IntegrationTest {

    protected EventBus eventBus;
    protected CommandBus commandBus;

    protected UUID matrix11Id;
    protected UUID spiderManId;
    protected UUID spiderMan2Id;
    protected UUID outOfAfricaId;

    @Before
    public void init(){
        createIntegrationSetup();
    }

    void createIntegrationSetup() {
        commandBus = new LocalCommandBus();
        eventBus = new LocalEventBus(new InMemoryEventStore());
        new CustomerCommandHandler(eventBus, commandBus);
        new CustomerEventHandler(eventBus, commandBus);
        new OrderCommandHandler(eventBus, commandBus);
        new OrderEventHandler(eventBus, commandBus);
        new InventoryCommandHandler(eventBus, commandBus);

        matrix11Id = createFilm("Matrix 11", FilmType.NEW);
        spiderManId = createFilm("Spider Man", FilmType.REGULAR);
        spiderMan2Id = createFilm("Spider Man 2", FilmType.REGULAR);
        outOfAfricaId = createFilm("Out of Africa", FilmType.OLD);
    }

    UUID createFilm(String name, FilmType filmType) {
        AtomicReference<UUID> id = new AtomicReference<>();
        assertEvent(EventType.FILM_CREATED, event -> {
            FilmCreatedEvent filmCreated = (FilmCreatedEvent) event;
            id.set(filmCreated.id);
        }, new CreateFilmCommand(name, filmType));
        return id.get();
    }

    protected UUID createOrder(UUID customerId) {
        AtomicReference<UUID> id = new AtomicReference<>();
        assertEvent(EventType.ORDER_CREATED, event -> {
            OrderCreatedEvent orderCreated = (OrderCreatedEvent) event;
            id.set(orderCreated.orderId);
        }, new CreateOrderCommand(customerId));
        return id.get();
    }

    protected UUID createCustomer(String name, int balance) {
        AtomicReference<UUID> id = new AtomicReference<>();
        assertEvent(EventType.CUSTOMER_CREATED, event -> {
            CustomerCreatedEvent customerCreated = (CustomerCreatedEvent) event;
            id.set(customerCreated.id);
        }, new CreateCustomerCommand(name, balance));
        return id.get();
    }

    protected Callable<Boolean> catchEvent(EventType eventType, Consumer<Event> check){
        AtomicBoolean called = new AtomicBoolean();
        eventBus.register(eventType, event -> {
            check.accept(event);
            called.set(true);
        });
        return called::get;
    }

    void assertEvent(EventType eventType, Consumer<Event> check, Command command){
        AtomicBoolean called = new AtomicBoolean();
        eventBus.register(eventType, event -> {
            check.accept(event);
            called.set(true);
        });

        commandBus.dispatch(command);
        Assert.assertTrue(called.get());
    }

}
