package com.cqrs.rest;

import com.cqrs.cqrs.EventBus;
import com.cqrs.domain.FilmProjection;
import com.cqrs.domain.Films;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("film")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FilmQueryResource {

    Films films;

    public FilmQueryResource(EventBus eventBus) {
        this.films = new Films(eventBus);
    }

    @GET
    @Path("{filmId}/calculate")
    public long calculatePrice(@PathParam("filmId") String filmId, @QueryParam("days") int days) {
        FilmProjection film = films.get(UUID.fromString(filmId));
        return film.type.calculatePrice(days);
    }
}
