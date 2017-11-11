package com.cqrs.events;

import com.cqrs.cqrs.AggregateType;
import com.cqrs.cqrs.Event;
import com.cqrs.cqrs.EventType;

import java.util.UUID;

public class CustomerCreditExceededEvent extends Event {
    public final UUID id;
    public final UUID orderId;

    public CustomerCreditExceededEvent(UUID id, UUID orderId) {
        this.id = id;
        this.orderId = orderId;
    }

    @Override
    public EventType getEventType() {
        return EventType.CUSTOMER_BALANCE_EXCEEDED;
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
