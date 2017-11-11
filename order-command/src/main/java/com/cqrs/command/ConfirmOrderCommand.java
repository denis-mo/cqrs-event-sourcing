package com.cqrs.command;

import com.cqrs.cqrs.Command;
import com.cqrs.cqrs.CommandType;

import java.util.UUID;

public class ConfirmOrderCommand extends Command {

    public final UUID orderId;

    public ConfirmOrderCommand(UUID orderId) {
        this.orderId = orderId;
    }

    @Override
    public CommandType getType() {
        return CommandType.CONFIRM_ORDER;
    }
}
