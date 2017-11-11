package com.cqrs.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderProjection {

    public UUID id;
    public UUID customerId;
    public OrderStatus status;
    public List<OrderProjectionItem> items = new ArrayList<>();
    public boolean confirmed;
    public int days;

    public OrderProjection(UUID id, UUID customerId) {
        this.id = id;
        this.customerId = customerId;
        this.status = OrderStatus.CREATED;
    }

    public void addItem(OrderProjectionItem orderItem) {
        items.add(orderItem);
    }

}
