package com.cqrs;

import com.cqrs.command.AddFilmToOrderCommand;
import com.cqrs.command.PlaceOrderCommand;
import com.cqrs.command.ReturnOrderCommand;
import com.cqrs.cqrs.AggregateType;
import com.cqrs.cqrs.Event;
import com.cqrs.domain.Customer;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ReturnOrderTest extends IntegrationTest {

    @Test
    public void returnOrder(){
        UUID customerId = createCustomer("Victoria", 200);
        UUID orderId = createOrder(customerId);

        commandBus.dispatch(new AddFilmToOrderCommand(orderId, outOfAfricaId));
        LocalDateTime placedTime = LocalDateTime.now();
        commandBus.dispatch(new PlaceOrderCommand(orderId, 4));
        commandBus.dispatch(new ReturnOrderCommand(orderId, placedTime.plusDays(7)));

        List<Event> events = eventBus.loadEvents(AggregateType.CUSTOMER, customerId);
        Customer customer = new Customer();
        events.forEach(customer::applyChange);
        Assert.assertEquals(110, customer.balance);
    }
}
