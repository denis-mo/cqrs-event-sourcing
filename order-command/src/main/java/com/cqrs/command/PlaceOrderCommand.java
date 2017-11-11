package com.cqrs.command;

import com.cqrs.cqrs.Command;
import com.cqrs.cqrs.CommandType;

import java.util.UUID;

public class PlaceOrderCommand extends Command {

    public final UUID orderId;
    public final int days;

    public PlaceOrderCommand(UUID orderId, int days) {
        this.orderId = orderId;
        this.days = days;
    }

    @Override
    public CommandType getType() {
        return CommandType.PLACE_ORDER;
    }
}
