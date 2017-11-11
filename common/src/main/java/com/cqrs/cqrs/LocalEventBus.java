package com.cqrs.cqrs;

import java.util.*;
import java.util.function.Consumer;

public class LocalEventBus implements EventBus {

    private Map<EventType, List<Consumer<Event>>> listeners = new HashMap<>();
    private EventStore eventStore;

    public LocalEventBus(InMemoryEventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public void dispatchEvent(Event event) {
        eventStore.save(event);
        dispatch(event);
    }

    @Override
    public List<Event> loadEvents(AggregateType type, UUID id) {
        return eventStore.loadEvents(type, id);
    }

    @Override
    public void register(EventType type, Consumer<Event> listener) {
        listeners.computeIfAbsent(type, eventType -> new ArrayList<>())
                .add(listener);
    }

    private void dispatch(Event event) {
        listeners.getOrDefault(event.getEventType(), Collections.emptyList())
                .forEach(listener -> listener.accept(event));
    }
}
