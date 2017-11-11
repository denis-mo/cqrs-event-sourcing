package com.cqrs;

import com.cqrs.command.*;
import com.cqrs.cqrs.*;
import com.cqrs.events.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.Callable;

public class CustomerProcessOrderTest extends IntegrationTest {

    @Test
    public void debitCustomer_successful() throws Exception {
        UUID customerId = createCustomer("John", 100);
        UUID orderId = createOrder(customerId);

        Callable<Boolean> debited = catchEvent(EventType.CUSTOMER_DEBITED, event -> {
            CustomerDebitedEvent customerDebited = (CustomerDebitedEvent) event;
            Assert.assertEquals(customerId, customerDebited.customerId);
            Assert.assertEquals(80, customerDebited.debited);
        });

        Callable<Boolean> bonusUpdated = catchEvent(EventType.CUSTOMER_BONUS_UPDATED, event -> {
            CustomerBonusUpdatedEvent customerBonusUpdated = (CustomerBonusUpdatedEvent) event;
            Assert.assertEquals(customerId, customerBonusUpdated.id);
            Assert.assertEquals(2, customerBonusUpdated.bonus);
        });

        Callable<Boolean> confirmed = catchEvent(EventType.CUSTOMER_CONFIRMED_ORDER, event -> {
            CustomerConfirmedOrderEvent customerConfirmedOrder = (CustomerConfirmedOrderEvent) event;
            Assert.assertEquals(customerId, customerConfirmedOrder.id);
            Assert.assertEquals(orderId, customerConfirmedOrder.orderId);
        });

        commandBus.dispatch(new ProcessOrderCommand(customerId, orderId, Arrays.asList(matrix11Id), 2));

        Assert.assertTrue("CUSTOMER_DEBITED event wasn't called", debited.call());
        Assert.assertTrue("CUSTOMER_BONUS_UPDATED event wasn't called", bonusUpdated.call());
        Assert.assertTrue("CUSTOMER_CONFIRMED_ORDER event wasn't called", confirmed.call());
    }

    @Test
    public void debitCustomer_complexOrderSuccessful() throws Exception {
        UUID customerId = createCustomer("John", 400);
        UUID orderId = createOrder(customerId);

        Callable<Boolean> debited = catchEvent(EventType.CUSTOMER_DEBITED, event -> {
            CustomerDebitedEvent customerDebited = (CustomerDebitedEvent) event;
            Assert.assertEquals(customerId, customerDebited.customerId);
            Assert.assertEquals(80, customerDebited.debited);
        });

        Callable<Boolean> bonusUpdated = catchEvent(EventType.CUSTOMER_BONUS_UPDATED, event -> {
            CustomerBonusUpdatedEvent customerBonusUpdated = (CustomerBonusUpdatedEvent) event;
            Assert.assertEquals(customerId, customerBonusUpdated.id);
            Assert.assertEquals(2, customerBonusUpdated.bonus);
        });

        Callable<Boolean> confirmed = catchEvent(EventType.CUSTOMER_CONFIRMED_ORDER, event -> {
            CustomerConfirmedOrderEvent customerConfirmedOrder = (CustomerConfirmedOrderEvent) event;
            Assert.assertEquals(customerId, customerConfirmedOrder.id);
            Assert.assertEquals(orderId, customerConfirmedOrder.orderId);
        });

        commandBus.dispatch(new ProcessOrderCommand(customerId, orderId, Arrays.asList(matrix11Id), 2));

        Assert.assertTrue("CUSTOMER_DEBITED event wasn't called", debited.call());
        Assert.assertTrue("CUSTOMER_BONUS_UPDATED event wasn't called", bonusUpdated.call());
        Assert.assertTrue("CUSTOMER_CONFIRMED_ORDER event wasn't called", confirmed.call());
    }

    @Test
    public void debitCustomer_balanceExceeded() throws Exception {
        UUID customerId = createCustomer("John", 100);
        UUID orderId = createOrder(customerId);

        Callable<Boolean> exceeded = catchEvent(EventType.CUSTOMER_BALANCE_EXCEEDED, event -> {
            CustomerBalanceExceededEvent customerBalanceExceeded = (CustomerBalanceExceededEvent) event;
            Assert.assertEquals(customerId, customerBalanceExceeded.id);
            Assert.assertEquals(orderId, customerBalanceExceeded.orderId);
        });

        commandBus.dispatch(new ProcessOrderCommand(customerId, orderId, Arrays.asList(matrix11Id), 3));

        Assert.assertTrue("CUSTOMER_BALANCE_EXCEEDED event wasn't called", exceeded.call());
    }
}
