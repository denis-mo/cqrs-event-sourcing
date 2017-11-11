package com.cqrs.command;

import com.cqrs.cqrs.Command;
import com.cqrs.cqrs.CommandType;

import java.util.UUID;

public class CancelOrderCommand extends Command {
    public final UUID orderId;

    public CancelOrderCommand(UUID orderId) {
        this.orderId = orderId;
    }

    @Override
    public CommandType getType() {
        return CommandType.CANCEL_ORDER;
    }
}
