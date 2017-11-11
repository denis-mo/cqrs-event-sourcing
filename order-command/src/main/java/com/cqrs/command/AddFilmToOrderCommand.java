package com.cqrs.command;

import com.cqrs.cqrs.Command;
import com.cqrs.cqrs.CommandType;

import java.util.UUID;

public class AddFilmToOrderCommand extends Command {

    public final UUID orderId;
    public final UUID filmId;

    public AddFilmToOrderCommand(UUID orderId, UUID filmId) {
        this.orderId = orderId;
        this.filmId = filmId;
    }

    @Override
    public CommandType getType() {
        return CommandType.ADD_FILM_TO_ORDER;
    }
}
