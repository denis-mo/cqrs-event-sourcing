package com.cqrs.domain;

import java.util.UUID;

public class FilmProjection {

    public final UUID id;
    public String name;
    public FilmType type;

    FilmProjection(UUID id, String name, FilmType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
