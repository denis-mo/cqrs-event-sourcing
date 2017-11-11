package com.cqrs.events;

import com.cqrs.cqrs.AggregateType;
import com.cqrs.cqrs.Event;
import com.cqrs.cqrs.EventType;
import com.cqrs.domain.FilmType;

import java.util.UUID;

public class FilmCreatedEvent extends Event {
    public final UUID id;
    public final String name;
    public final FilmType type;

    public FilmCreatedEvent(UUID id, String name, FilmType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    @Override
    public EventType getEventType() {
        return EventType.FILM_CREATED;
    }

    @Override
    public AggregateType getType() {
        return AggregateType.FILM;
    }

    @Override
    public UUID getAggregateId() {
        return id;
    }
}
