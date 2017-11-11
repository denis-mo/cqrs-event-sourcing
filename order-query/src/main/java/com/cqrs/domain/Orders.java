package com.cqrs.domain;

import com.cqrs.cqrs.EventBus;
import com.cqrs.cqrs.EventType;
import com.cqrs.events.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Orders {

    private Map<UUID, OrderProjection> orders = new HashMap<>();
    private Map<UUID, OrderProjection> orderByCustomer = new HashMap<>();

    public Orders(EventBus eventBus) {
        registerEvents(eventBus);
    }

    public OrderProjection get(UUID id) {
        return orders.get(id);
    }

    public OrderProjection getByCustomer(UUID customerId) {
        return orderByCustomer.get(customerId);
    }

    private void registerEvents(EventBus eventBus) {
        eventBus.register(EventType.ORDER_CREATED, event -> {
            OrderCreatedEvent orderCreated = (OrderCreatedEvent) event;
            OrderProjection order = new OrderProjection(orderCreated.orderId, orderCreated.customerId);
            orders.put(orderCreated.orderId, order);
            orderByCustomer.put(orderCreated.customerId, order);
        });

        eventBus.register(EventType.ADDED_TO_ORDER, event -> {
            AddedToOrderEvent addedToOrder = (AddedToOrderEvent) event;
            orders.get(addedToOrder.orderId).addItem(new OrderProjectionItem(addedToOrder.filmId));
        });

        eventBus.register(EventType.ORDER_PLACED, event -> {
            OrderPlacedEvent orderPlaced = (OrderPlacedEvent) event;
            orders.get(orderPlaced.orderId).status = OrderStatus.PLACED;
            orders.get(orderPlaced.orderId).days = orderPlaced.days;
        });

        eventBus.register(EventType.CUSTOMER_CONFIRMED_ORDER, event -> {
            CustomerConfirmedOrderEvent orderConfirmed = (CustomerConfirmedOrderEvent) event;
            orders.get(orderConfirmed.orderId).confirmed = true;
        });

        eventBus.register(EventType.ORDER_CANCELLED, event -> {
            OrderCancelledEvent orderCancelled = (OrderCancelledEvent) event;
            orders.get(orderCancelled.orderId).status = OrderStatus.CANCELLED;
        });

        eventBus.register(EventType.ORDER_RETURNED, event -> {
            OrderReturnedEvent orderReturned = (OrderReturnedEvent) event;
            orders.get(orderReturned.orderId).status = OrderStatus.RETURNED;
        });
    }
}
