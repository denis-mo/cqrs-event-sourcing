package com.cqrs.events;

import com.cqrs.cqrs.AggregateType;
import com.cqrs.cqrs.Event;
import com.cqrs.cqrs.EventType;

import java.util.UUID;

public class OrderCancelledEvent extends Event {
    public final UUID orderId;

    public OrderCancelledEvent(UUID orderId) {
        this.orderId = orderId;
    }

    @Override
    public EventType getEventType() {
        return EventType.ORDER_CANCELLED;
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
