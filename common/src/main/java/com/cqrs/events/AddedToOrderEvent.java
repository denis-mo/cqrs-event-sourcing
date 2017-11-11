package com.cqrs.events;

import com.cqrs.cqrs.AggregateType;
import com.cqrs.cqrs.Event;
import com.cqrs.cqrs.EventType;

import java.util.UUID;

public class AddedToOrderEvent extends Event {

    public final UUID orderId;
    public final UUID filmId;

    public AddedToOrderEvent(UUID orderId, UUID filmId) {
        this.orderId = orderId;
        this.filmId = filmId;
    }

    @Override
    public EventType getEventType() {
        return EventType.ADDED_TO_ORDER;
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
