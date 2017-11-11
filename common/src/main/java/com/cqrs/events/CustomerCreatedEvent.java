package com.cqrs.events;

import com.cqrs.cqrs.AggregateType;
import com.cqrs.cqrs.Event;
import com.cqrs.cqrs.EventType;

import java.util.UUID;

public class CustomerCreatedEvent extends Event {
    public final UUID id;
    public final String code;
    public final String name;
    public final long balance;

    public CustomerCreatedEvent(UUID id, String code, String name, long balance) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.balance = balance;
    }

    @Override
    public EventType getEventType() {
        return EventType.CUSTOMER_CREATED;
    }

    @Override
    public AggregateType getType() {
        return AggregateType.CUSTOMER;
    }

    @Override
    public UUID getAggregateId() {
        return id;
    }
}
