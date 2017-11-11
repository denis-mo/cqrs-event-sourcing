package com.cqrs.events;

import com.cqrs.cqrs.AggregateType;
import com.cqrs.cqrs.Event;
import com.cqrs.cqrs.EventType;

import java.util.UUID;

public class CustomerBonusUpdatedEvent extends Event {

    public final UUID id;
    public final int bonus;

    public CustomerBonusUpdatedEvent(UUID id, int bonus) {
        this.id = id;
        this.bonus = bonus;
    }

    @Override
    public EventType getEventType() {
        return EventType.CUSTOMER_BONUS_UPDATED;
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
