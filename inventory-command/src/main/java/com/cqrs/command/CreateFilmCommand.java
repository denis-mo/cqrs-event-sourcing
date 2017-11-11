package com.cqrs.command;

import com.cqrs.cqrs.Command;
import com.cqrs.cqrs.CommandType;
import com.cqrs.domain.FilmType;

public class CreateFilmCommand extends Command {

    public final String name;
    public final FilmType type;

    public CreateFilmCommand(String name, FilmType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public CommandType getType() {
        return CommandType.CREATE_FILM;
    }
}
