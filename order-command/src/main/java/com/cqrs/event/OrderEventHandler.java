package com.cqrs.event;

import com.cqrs.command.CancelOrderCommand;
import com.cqrs.command.ConfirmOrderCommand;
import com.cqrs.cqrs.*;
import com.cqrs.events.CustomerBalanceExceededEvent;
import com.cqrs.events.CustomerConfirmedOrderEvent;

public class OrderEventHandler {

    private EventBus eventBus;
    private CommandBus commandBus;

    public OrderEventHandler(EventBus eventBus, CommandBus commandBus) {
        this.eventBus = eventBus;
        this.commandBus = commandBus;

        registerEvents();
    }

    private void registerEvents() {
        eventBus.register(EventType.CUSTOMER_CONFIRMED_ORDER, event -> {
            CustomerConfirmedOrderEvent customerConfirmedOrder = (CustomerConfirmedOrderEvent) event;

            commandBus.dispatch(new ConfirmOrderCommand(customerConfirmedOrder.orderId));
        });
        eventBus.register(EventType.CUSTOMER_BALANCE_EXCEEDED, event -> {
            CustomerBalanceExceededEvent customerBalanceExceeded = (CustomerBalanceExceededEvent) event;

            commandBus.dispatch(new CancelOrderCommand(customerBalanceExceeded.orderId));
        });
    }
}
