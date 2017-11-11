package com.cqrs.cqrs;

import java.util.UUID;

public abstract class Event {

    public abstract EventType getEventType();
    public abstract AggregateType getType();
    public abstract UUID getAggregateId();

    public Class getClassType(){
        return this.getClass();
    }




}
