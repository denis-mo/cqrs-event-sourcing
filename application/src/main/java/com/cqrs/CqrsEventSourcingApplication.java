package com.cqrs;

import com.cqrs.command.CustomerCommandHandler;
import com.cqrs.command.InventoryCommandHandler;
import com.cqrs.command.OrderCommandHandler;
import com.cqrs.cqrs.*;
import com.cqrs.event.CustomerEventHandler;
import com.cqrs.event.OrderEventHandler;
import com.cqrs.rest.CustomerQueryResource;
import com.cqrs.rest.FilmQueryResource;
import com.cqrs.rest.OrderCommandResource;
import com.cqrs.rest.OrderQueryResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class CqrsEventSourcingApplication extends Application<CqrsEventSourcingConfiguration> {

    @Override
    public void initialize(Bootstrap<CqrsEventSourcingConfiguration> bootstrap) {
    }

    @Override
    public void run(CqrsEventSourcingConfiguration configuration, Environment environment) throws Exception {
        EventBus eventBus = new LocalEventBus(new InMemoryEventStore());

        createCustomerCommandServer(environment, eventBus);
        createCustomerQueryServer(environment, eventBus);

        createOrderCommandServer(environment, eventBus);
        createOrderQueryServer(environment, eventBus);

        createInventoryCommandServer(environment, eventBus);
        createInventoryQueryServer(environment, eventBus);
    }

    private void createInventoryQueryServer(Environment environment, EventBus eventBus) {
        environment.jersey().register(new FilmQueryResource(eventBus));
    }

    private void createInventoryCommandServer(Environment environment, EventBus eventBus) {
        CommandBus commandBus = new LocalCommandBus();

        new InventoryCommandHandler(eventBus, commandBus);
    }

    private void createOrderQueryServer(Environment environment, EventBus eventBus) {
        environment.jersey().register(new OrderQueryResource(eventBus));
    }

    private void createOrderCommandServer(Environment environment, EventBus eventBus) {
        CommandBus commandBus = new LocalCommandBus();

        new OrderCommandHandler(eventBus, commandBus);
        new OrderEventHandler(eventBus, commandBus);
        environment.jersey().register(new OrderCommandResource(commandBus));
    }

    private void createCustomerQueryServer(Environment environment, EventBus eventBus) {
        environment.jersey().register(new CustomerQueryResource(eventBus));
    }

    private void createCustomerCommandServer(Environment environment, EventBus eventBus) {
        CommandBus commandBus = new LocalCommandBus();

        new CustomerEventHandler(eventBus, commandBus);
        new CustomerCommandHandler(eventBus, commandBus);
    }

    public static void main(String[] args) throws Exception {
        new CqrsEventSourcingApplication().run(args);
    }
}
