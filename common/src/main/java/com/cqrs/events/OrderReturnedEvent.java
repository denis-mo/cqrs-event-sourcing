package com.cqrs.events;

import com.cqrs.cqrs.AggregateType;
import com.cqrs.cqrs.Event;
import com.cqrs.cqrs.EventType;

import java.util.List;
import java.util.UUID;

public class OrderReturnedEvent extends Event{

	public final UUID customerId;
	public final UUID orderId;
	public final List<UUID> films;
	public final int extraDays;

	public OrderReturnedEvent(UUID customerId, UUID orderId, List<UUID> films, int extraDays) {
		this.customerId = customerId;
		this.orderId = orderId;
		this.films = films;
		this.extraDays = extraDays;
	}

	@Override
	public EventType getEventType() {
		return EventType.ORDER_RETURNED;
	}

	@Override
	public AggregateType getType() {
		return AggregateType.ORDER;
	}

	@Override
	public UUID getAggregateId() {
		return orderId;
	}
}
