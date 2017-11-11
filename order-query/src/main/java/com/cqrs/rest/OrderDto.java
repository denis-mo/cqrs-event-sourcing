package com.cqrs.rest;

import java.util.List;
import java.util.UUID;

public class OrderDto {
    public UUID id;
    public List<OrderItemDto> items;
    public int days;
}
