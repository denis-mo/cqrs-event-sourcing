package com.cqrs.events;

import com.cqrs.cqrs.AggregateType;
import com.cqrs.cqrs.Event;
import com.cqrs.cqrs.EventType;

import java.util.UUID;

public class OrderCreatedEvent extends Event {

    public final UUID orderId;
    public final UUID customerId;

    public OrderCreatedEvent(UUID orderId, UUID customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
    }

    @Override
    public EventType getEventType() {
        return EventType.ORDER_CREATED;
    }

    @Override
    public AggregateType getType() {
        return AggregateType.ORDER;
    }

    @Override
    public UUID getAggregateId() {
        return orderId;
    }
}
