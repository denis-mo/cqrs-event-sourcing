package com.cqrs.events;

import com.cqrs.cqrs.AggregateType;
import com.cqrs.cqrs.Event;
import com.cqrs.cqrs.EventType;

import java.util.List;
import java.util.UUID;

public class OrderReturnedLateEvent extends Event {
    public final UUID customerId;
    public final UUID orderId;
    public List<UUID> films;
    public final int days;
    public final int extraDays;

    public OrderReturnedLateEvent(UUID customerId, UUID orderId, List<UUID> films, int days, int extraDays) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.films = films;
        this.days = days;
        this.extraDays = extraDays;
    }

    @Override
    public EventType getEventType() {
        return EventType.ORDER_RETURNED_LATE;
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
