package com.cqrs.command;

import com.cqrs.cqrs.Command;
import com.cqrs.cqrs.CommandType;

import java.util.List;
import java.util.UUID;

public class ProcessOrderCommand extends Command {

    public final UUID customerId;
    public final UUID orderId;
    public final List<UUID> films;
    public final int days;

    public ProcessOrderCommand(UUID customerId, UUID orderId, List<UUID> films, int days) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.films = films;
        this.days = days;
    }

    @Override
    public CommandType getType() {
        return CommandType.CUSTOMER_PROCESS_ORDER;
    }
}
