package com.cqrs.events;

import com.cqrs.cqrs.AggregateType;
import com.cqrs.cqrs.Event;
import com.cqrs.cqrs.EventType;

import java.util.UUID;

public class CustomerConfirmedOrderEvent extends Event {
    public final UUID id;
    public final UUID orderId;

    public CustomerConfirmedOrderEvent(UUID id, UUID orderId) {
        this.id = id;
        this.orderId = orderId;
    }

    @Override
    public EventType getEventType() {
        return EventType.CUSTOMER_CONFIRMED_ORDER;
    }

    @Override
    public AggregateType getType() {
        return AggregateType.CUSTOMER;
    }

    @Override
    public UUID getAggregateId() {
        return orderId;
    }
}
