package com.cqrs.domain;

import com.cqrs.cqrs.EventBus;
import com.cqrs.cqrs.EventType;
import com.cqrs.events.FilmCreatedEvent;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Films {

    private Map<UUID, FilmProjection> films = new ConcurrentHashMap<>();

    public Films(EventBus eventBus) {
        registerEvents(eventBus);
    }

    public FilmProjection get(UUID filmId) {
        return films.get(filmId);
    }

    private void registerEvents(EventBus eventBus) {
        eventBus.register(EventType.FILM_CREATED, event -> {
            FilmCreatedEvent filmCreated = (FilmCreatedEvent) event;
            FilmProjection film = new FilmProjection(filmCreated.id, filmCreated.name, filmCreated.type);
            films.put(film.id, film);
        });
    }

    public int getBonus(List<UUID> filmIds) {
        return filmIds.stream()
                .mapToInt(id -> films.get(id).type.bonusPoints())
                .sum();
    }

    public long getTotalPrice(List<UUID> filmIds, int days) {
        return filmIds.stream()
                .mapToLong(id -> get(id).type.calculatePrice(days))
                .sum();
    }
}
