package com.cqrs.cqrs;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public interface EventBus {

    void register(EventType type, Consumer<Event> listener);

    void dispatchEvent(Event event);

    List<Event> loadEvents(AggregateType type, UUID aggregateId);
}
