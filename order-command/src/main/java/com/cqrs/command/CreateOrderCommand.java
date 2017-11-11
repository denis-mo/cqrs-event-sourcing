package com.cqrs.command;

import com.cqrs.cqrs.Command;
import com.cqrs.cqrs.CommandType;

import java.util.UUID;

public class CreateOrderCommand extends Command {

    public final UUID customerId;

    public CreateOrderCommand(UUID customerId) {
        this.customerId = customerId;
    }

    @Override
    public CommandType getType() {
        return CommandType.CREATE_ORDER;
    }
}
