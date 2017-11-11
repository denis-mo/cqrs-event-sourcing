package com.cqrs.domain;

import com.cqrs.cqrs.Aggregate;
import com.cqrs.cqrs.Event;
import com.cqrs.cqrs.EventType;
import com.cqrs.events.CustomerBonusUpdatedEvent;
import com.cqrs.events.CustomerCreatedEvent;
import com.cqrs.events.CustomerDebitedEvent;

import java.util.UUID;

public class Customer implements Aggregate {

    public UUID id;
    public String name;
    public String code;
    public long balance;
    public int bonusPoints;

    @Override
    public void applyChange(Event event) {
        if (event.getEventType() == EventType.CUSTOMER_CREATED) {
            applyChange((CustomerCreatedEvent) event);
        } else if (event.getEventType() == EventType.CUSTOMER_DEBITED) {
            applyChange((CustomerDebitedEvent) event);
        } else if (event.getEventType() == EventType.CUSTOMER_BONUS_UPDATED) {
            applyChange((CustomerBonusUpdatedEvent) event);
        }
    }

    public void applyChange(CustomerCreatedEvent customerCreated) {
        this.id = customerCreated.id;
        this.name = customerCreated.name;
        this.code = customerCreated.code;
        this.balance = customerCreated.balance;
    }

    public void applyChange(CustomerDebitedEvent customerDebited) {
        balance -= customerDebited.debited;
    }

    public void applyChange(CustomerBonusUpdatedEvent customerBonusUpdated) {
        bonusPoints =+ customerBonusUpdated.bonus;
    }
}
