package com.cqrs.events;

import com.cqrs.cqrs.AggregateType;
import com.cqrs.cqrs.Event;
import com.cqrs.cqrs.EventType;

import java.util.UUID;

public class CustomerDebitedEvent extends Event {

    public final UUID customerId;
    public final long debited;

    public CustomerDebitedEvent(UUID customerId, long debited) {
        this.customerId = customerId;
        this.debited = debited;
    }

    @Override
    public EventType getEventType() {
        return EventType.CUSTOMER_DEBITED;
    }

    @Override
    public AggregateType getType() {
        return AggregateType.CUSTOMER;
    }

    @Override
    public UUID getAggregateId() {
        return customerId;
    }
}
