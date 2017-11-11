package com.cqrs.domain;

import com.cqrs.cqrs.EventBus;
import com.cqrs.cqrs.EventType;
import com.cqrs.events.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Customers {

    Map<UUID, CustomerProjection> customers = new HashMap<>();

    public Customers(EventBus eventBus) {
        registerEvents(eventBus);
    }

    public CustomerProjection get(UUID aggregateId) {
        return customers.get(aggregateId);
    }

    private void registerEvents(EventBus eventBus) {
        eventBus.register(EventType.CUSTOMER_CREATED, event -> {
            CustomerCreatedEvent customerCreated = (CustomerCreatedEvent) event;

            CustomerProjection customer = new CustomerProjection();
            customer.id = customerCreated.id;
            customer.name = customerCreated.name;
            customer.code = customerCreated.code;
            customer.balance = customerCreated.balance;
            customers.put(customer.id, customer);
        });

        eventBus.register(EventType.CUSTOMER_DEBITED, event -> {
            CustomerDebitedEvent customerDebited = (CustomerDebitedEvent) event;
            customers.get(customerDebited.getAggregateId()).balance -= customerDebited.debited;
        });

        eventBus.register(EventType.CUSTOMER_BONUS_UPDATED, event -> {
            CustomerBonusUpdatedEvent customerBonusUpdated = (CustomerBonusUpdatedEvent) event;
            customers.get(customerBonusUpdated.getAggregateId()).bonusPoints += customerBonusUpdated.bonus;
        });
    }

}
