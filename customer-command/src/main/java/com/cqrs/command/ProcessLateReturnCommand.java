package com.cqrs.command;

import com.cqrs.cqrs.Command;
import com.cqrs.cqrs.CommandType;

import java.util.List;
import java.util.UUID;

public class ProcessLateReturnCommand extends Command {

    public final UUID customerId;
    public final List<UUID> films;
    public final int days;
    public final int extraDays;

    public ProcessLateReturnCommand(UUID customerId, List<UUID> films, int days, int extraDays) {
        this.customerId = customerId;
        this.films = films;
        this.days = days;
        this.extraDays = extraDays;
    }

    @Override
    public CommandType getType() {
        return CommandType.PROCESS_RETURN_LATE;
    }
}
