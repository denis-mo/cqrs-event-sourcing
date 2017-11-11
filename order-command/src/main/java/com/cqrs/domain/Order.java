package com.cqrs.domain;

import com.cqrs.cqrs.Aggregate;
import com.cqrs.cqrs.Event;
import com.cqrs.cqrs.EventType;
import com.cqrs.events.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order implements Aggregate {

    public UUID id;
    public UUID customerId;
    public LocalDateTime placedTime;
    public OrderStatus status;
    public int days;
    public List<UUID> films = new ArrayList<>();
    public boolean confirmed;

    private void applyChange(OrderCreatedEvent event) {
        id = event.orderId;
        customerId = event.customerId;
    }

    private void applyChange(AddedToOrderEvent event) {
        films.add(event.filmId);
    }

    private void applyChange(OrderConfirmedEvent event) {
        confirmed = true;
    }

    private void applyChange(OrderReturnedEvent event) {
        status = OrderStatus.RETURNED;
    }

    private void applyChange(OrderCancelledEvent event) {
        status = OrderStatus.CANCELLED;
    }

    private void applyChange(OrderPlacedEvent event) {
        status = OrderStatus.PLACED;
        placedTime = LocalDateTime.now();
        days = event.days;
    }

    @Override
    public void applyChange(Event event) {
        if (event.getEventType() == EventType.ORDER_CREATED){
            applyChange((OrderCreatedEvent) event);
        } else if (event.getEventType() == EventType.ADDED_TO_ORDER){
            applyChange((AddedToOrderEvent) event);
        } else if (event.getEventType() == EventType.ORDER_PLACED){
            applyChange((OrderPlacedEvent) event);
        } else if (event.getEventType() == EventType.ORDER_CONFIRMED){
            applyChange((OrderConfirmedEvent) event);
        } else if (event.getEventType() == EventType.ORDER_RETURNED){
            applyChange((OrderReturnedEvent) event);
        } else if (event.getEventType() == EventType.ORDER_CANCELLED){
            applyChange((OrderCancelledEvent) event);
        }
    }
}
