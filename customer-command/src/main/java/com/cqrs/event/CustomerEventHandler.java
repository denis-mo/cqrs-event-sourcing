package com.cqrs.event;

import com.cqrs.command.ProcessOrderCommand;
import com.cqrs.command.ProcessLateReturnCommand;
import com.cqrs.cqrs.CommandBus;
import com.cqrs.cqrs.EventBus;
import com.cqrs.cqrs.EventType;
import com.cqrs.events.OrderPlacedEvent;
import com.cqrs.events.OrderReturnedLateEvent;

public class CustomerEventHandler {

    private EventBus eventBus;
    private CommandBus commandBus;

    public CustomerEventHandler(EventBus eventBus, CommandBus commandBus) {
        this.eventBus = eventBus;
        this.commandBus = commandBus;

        registerEvents();
    }

    private void registerEvents() {
        eventBus.register(EventType.ORDER_PLACED, orderPlacedEvent -> {
            OrderPlacedEvent event = (OrderPlacedEvent) orderPlacedEvent;

            commandBus.dispatch(
                    new ProcessOrderCommand(event.customerId, event.orderId, event.films, event.days));
        });
        eventBus.register(EventType.ORDER_RETURNED_LATE, orderLateEvent -> {
            OrderReturnedLateEvent event = (OrderReturnedLateEvent) orderLateEvent;

            commandBus.dispatch(new ProcessLateReturnCommand(event.customerId, event.films, event.days, event.extraDays));
        });
    }
}
