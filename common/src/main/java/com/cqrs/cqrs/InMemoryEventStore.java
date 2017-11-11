package com.cqrs.cqrs;

import java.util.*;

public class InMemoryEventStore implements EventStore {

    private List<Event> events = new ArrayList<>();

    @Override
    public List<Event> loadEvents(AggregateType type, UUID id) {
        List<Event> loadedEvents = new ArrayList<>();

        for (Event event : events) {
            System.out.println("event = " + event);
            System.out.println("event.getAggregateId() = " + event.getAggregateId());
            if (event.getAggregateId().equals(id) && event.getType() == type) {
                loadedEvents.add(event);
            }
        }
        return loadedEvents;
    }

    public void save(Event event) {
        if (event == null) {
            System.out.println("fff");
        }
        events.add(event);
    }
}
