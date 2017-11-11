package com.cqrs.cqrs;

public interface Aggregate {
    void applyChange(Event event);
}
