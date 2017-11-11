package com.cqrs.cqrs;

import java.util.List;
import java.util.UUID;

public interface EventStore {

    List<Event> loadEvents(AggregateType type, UUID id);

    void save(Event event);
}
