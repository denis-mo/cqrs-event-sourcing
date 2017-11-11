package com.cqrs.command;

import com.cqrs.cqrs.*;
import com.cqrs.events.FilmCreatedEvent;

import java.util.UUID;

public class InventoryCommandHandler extends CommandHandler {

    public InventoryCommandHandler(EventBus eventBus, CommandBus commandBus) {
        commandBus.register(CommandType.CREATE_FILM, command -> {
            CreateFilmCommand createFilm = (CreateFilmCommand) command;

            UUID id = UUID.randomUUID();
            eventBus.dispatchEvent(new FilmCreatedEvent(id, createFilm.name, createFilm.type));
        });
    }
}
