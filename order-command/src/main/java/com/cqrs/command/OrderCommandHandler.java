package com.cqrs.command;

import com.cqrs.cqrs.*;
import com.cqrs.domain.*;
import com.cqrs.events.*;

import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class OrderCommandHandler extends CommandHandler {

    public OrderCommandHandler(EventBus eventBus, CommandBus commandBus) {
        commandBus.register(CommandType.CREATE_ORDER, command -> {
            CreateOrderCommand createOrder = (CreateOrderCommand) command;

            UUID orderId = UUID.randomUUID();
            eventBus.dispatchEvent(new OrderCreatedEvent(orderId, createOrder.customerId));
        });

        commandBus.register(CommandType.ADD_FILM_TO_ORDER, command -> {
            AddFilmToOrderCommand addFilm = (AddFilmToOrderCommand) command;

            eventBus.dispatchEvent(new AddedToOrderEvent(addFilm.orderId, addFilm.filmId));
        });

        commandBus.register(CommandType.PLACE_ORDER, command -> {
            PlaceOrderCommand placeOrder = (PlaceOrderCommand) command;

            Order order = aggregateOrder(eventBus, placeOrder.orderId);
            eventBus.dispatchEvent(new OrderPlacedEvent(order.id, order.customerId, order.films, placeOrder.days));
        });

        commandBus.register(CommandType.RETURN_ORDER, command -> {
            ReturnOrderCommand returnOrder = (ReturnOrderCommand) command;

            Order order = aggregateOrder(eventBus, returnOrder.orderId);

            long totalDays = ChronoUnit.DAYS.between(order.placedTime, returnOrder.returnTime) + 1;
            int extraDays = (int) (totalDays - order.days);
            if (extraDays > 0) {
                eventBus.dispatchEvent(new OrderReturnedLateEvent(order.customerId,
                        returnOrder.orderId,
                        order.films,
                        order.days,
                        extraDays));
            }

            eventBus.dispatchEvent(new OrderReturnedEvent(order.customerId, returnOrder.orderId, order.films, extraDays));
        });

        commandBus.register(CommandType.CONFIRM_ORDER, command -> {
            ConfirmOrderCommand confirmOrder = (ConfirmOrderCommand) command;

            eventBus.dispatchEvent(new OrderConfirmedEvent(confirmOrder.orderId));
        });

        commandBus.register(CommandType.CANCEL_ORDER, command -> {
            CancelOrderCommand cancelOrder = (CancelOrderCommand) command;

            eventBus.dispatchEvent(new OrderCancelledEvent(cancelOrder.orderId));
        });
    }

    private Order aggregateOrder(EventBus eventBus, UUID id) {
        Order order = new Order();
        eventBus.loadEvents(AggregateType.ORDER, id).forEach(order::applyChange);
        return order;
    }
}
