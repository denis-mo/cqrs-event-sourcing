package com.cqrs.command;

import com.cqrs.cqrs.*;
import com.cqrs.domain.Customer;
import com.cqrs.domain.Films;
import com.cqrs.events.*;

import java.util.List;
import java.util.UUID;

public class CustomerCommandHandler extends CommandHandler {

    private final Films films;

    public CustomerCommandHandler(EventBus eventBus, CommandBus commandBus) {
        this.films = new Films(eventBus);

        commandBus.register(CommandType.CREATE_CUSTOMER, command -> {
            CreateCustomerCommand createCustomer = ((CreateCustomerCommand) command);

            eventBus.dispatchEvent(new CustomerCreatedEvent(
                    UUID.randomUUID(),
                    createCustomer.createCode(),
                    createCustomer.name,
                    createCustomer.balance));
        });

        commandBus.register(CommandType.CUSTOMER_PROCESS_ORDER, command -> {
            ProcessOrderCommand debitCustomer = ((ProcessOrderCommand) command);

            List<Event> events = eventBus.loadEvents(AggregateType.CUSTOMER, debitCustomer.customerId);
            Customer customer = new Customer();
            events.forEach(customer::applyChange);

            long totalPrice = films.getTotalPrice(debitCustomer.films, debitCustomer.days);

            if (customer.balance >= totalPrice) {
                eventBus.dispatchEvent(new CustomerDebitedEvent(customer.id, totalPrice));
                eventBus.dispatchEvent(new CustomerBonusUpdatedEvent(customer.id, films.getBonus(debitCustomer.films)));
                eventBus.dispatchEvent(new CustomerConfirmedOrderEvent(customer.id, debitCustomer.orderId));
            } else {
                eventBus.dispatchEvent(new CustomerBalanceExceededEvent(customer.id, debitCustomer.orderId));
            }
        });

        commandBus.register(CommandType.PROCESS_RETURN_LATE, customerProcessReturn -> {
            ProcessLateReturnCommand command = ((ProcessLateReturnCommand) customerProcessReturn);

            long initialPrice = films.getTotalPrice(command.films, command.days);
            long fullPrice = films.getTotalPrice(command.films, command.days + command.extraDays);
            if (fullPrice > initialPrice) {
                eventBus.dispatchEvent(new CustomerDebitedEvent(command.customerId, fullPrice - initialPrice));
            }
        });
    }
}
