package com.cqrs.events;

import com.cqrs.cqrs.AggregateType;
import com.cqrs.cqrs.Event;
import com.cqrs.cqrs.EventType;

import java.util.List;
import java.util.UUID;

public class OrderPlacedEvent extends Event {

    public final UUID orderId;
    public final UUID customerId;
    public final List<UUID> films;
    public final int days;

    public OrderPlacedEvent(UUID orderId, UUID customerId, List<UUID> films, int days) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.films = films;
        this.days = days;
    }

    @Override
    public EventType getEventType() {
        return EventType.ORDER_PLACED;
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
