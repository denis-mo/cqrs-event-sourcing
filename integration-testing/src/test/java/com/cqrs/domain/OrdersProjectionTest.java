package com.cqrs.domain;

import com.cqrs.IntegrationTest;
import com.cqrs.command.AddFilmToOrderCommand;
import com.cqrs.command.CancelOrderCommand;
import com.cqrs.command.PlaceOrderCommand;
import com.cqrs.command.ReturnOrderCommand;
import com.cqrs.cqrs.EventType;
import com.cqrs.events.AddedToOrderEvent;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.Callable;

public class OrdersProjectionTest extends IntegrationTest {

    @Test
    public void addFilmToOrder() throws Exception {
        Orders orders = new Orders(eventBus);
        UUID customerId = createCustomer("Mateo", 200);
        UUID orderId = createOrder(customerId);

        Callable<Boolean> added = catchEvent(EventType.ADDED_TO_ORDER, event -> {
            AddedToOrderEvent addedToOrder = (AddedToOrderEvent) event;
            Assert.assertEquals(orderId, addedToOrder.orderId);
            Assert.assertEquals(spiderManId, addedToOrder.filmId);
        });

        commandBus.dispatch(new AddFilmToOrderCommand(orderId, spiderManId));

        OrderProjection order = orders.get(orderId);
        Assert.assertEquals(orderId, order.id);
        Assert.assertEquals(1, order.items.size());

        OrderProjectionItem item = order.items.get(0);
        Assert.assertEquals(spiderManId, item.filmId);

        Assert.assertTrue(added.call());
    }

    @Test
    public void cancelOrder() throws Exception {
        Orders orders = new Orders(eventBus);
        UUID customerId = createCustomer("Mateo", 200);
        UUID orderId = createOrder(customerId);

        commandBus.dispatch(new AddFilmToOrderCommand(orderId, outOfAfricaId));
        commandBus.dispatch(new CancelOrderCommand(orderId));

        OrderProjection order = orders.get(orderId);

        Assert.assertFalse(order.confirmed);
        Assert.assertEquals(OrderStatus.CANCELLED, order.status);
    }

    @Test
    public void placeOrder() throws Exception {
        Orders orders = new Orders(eventBus);
        UUID customerId = createCustomer("Sofia", 200);
        UUID orderId = createOrder(customerId);

        commandBus.dispatch(new AddFilmToOrderCommand(orderId, outOfAfricaId));
        commandBus.dispatch(new PlaceOrderCommand(orderId, 4));

        OrderProjection order = orders.get(orderId);

        Assert.assertTrue(order.confirmed);
        Assert.assertEquals(OrderStatus.PLACED, order.status);
    }

    @Test
    public void returnOrder() throws Exception {
        Orders orders = new Orders(eventBus);
        UUID customerId = createCustomer("Kurt", 200);
        UUID orderId = createOrder(customerId);

        commandBus.dispatch(new AddFilmToOrderCommand(orderId, outOfAfricaId));
        commandBus.dispatch(new PlaceOrderCommand(orderId, 4));
        commandBus.dispatch(new ReturnOrderCommand(orderId));

        OrderProjection order = orders.get(orderId);

        Assert.assertTrue(order.confirmed);
        Assert.assertEquals(OrderStatus.RETURNED, order.status);
    }

}
