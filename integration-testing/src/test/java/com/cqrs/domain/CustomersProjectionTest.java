package com.cqrs.domain;

import com.cqrs.IntegrationTest;
import com.cqrs.command.ProcessOrderCommand;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

public class CustomersProjectionTest extends IntegrationTest {

    @Test
    public void customerCreated() throws Exception {
        Customers customers = new Customers(eventBus);
        UUID marinaId = createCustomer("Marina", 100);

        Assert.assertTrue(!customers.customers.isEmpty());
        Assert.assertEquals(marinaId, customers.customers.get(marinaId).id);
        Assert.assertEquals("Marina", customers.customers.get(marinaId).name);
        Assert.assertEquals("marina", customers.customers.get(marinaId).code);
        Assert.assertEquals(100, customers.customers.get(marinaId).balance);
        Assert.assertEquals(0, customers.customers.get(marinaId).bonusPoints);
    }

    @Test
    public void customerCreatedMultiple() throws Exception {
        Customers customers = new Customers(eventBus);
        UUID jerryId = createCustomer("Jerry", 100);
        UUID timId = createCustomer("Tim", 200);

        Assert.assertEquals(2, customers.customers.size());

        Assert.assertEquals(jerryId, customers.customers.get(jerryId).id);
        Assert.assertEquals("Jerry", customers.customers.get(jerryId).name);
        Assert.assertEquals("jerry", customers.customers.get(jerryId).code);
        Assert.assertEquals(100, customers.customers.get(jerryId).balance);
        Assert.assertEquals(0, customers.customers.get(jerryId).bonusPoints);

        Assert.assertEquals(timId, customers.customers.get(timId).id);
        Assert.assertEquals("Tim", customers.customers.get(timId).name);
        Assert.assertEquals("tim", customers.customers.get(timId).code);
        Assert.assertEquals(200, customers.customers.get(timId).balance);
        Assert.assertEquals(0, customers.customers.get(timId).bonusPoints);
    }

    @Test
    public void orderCancelled_balanceNotAffected() throws Exception {
        Customers customers = new Customers(eventBus);
        UUID customerId = createCustomer("John", 100);
        UUID orderId = createOrder(customerId);

        commandBus.dispatch(new ProcessOrderCommand(customerId, orderId, Arrays.asList(matrix11Id), 3));

        Assert.assertNotNull(customers.get(customerId));
        Assert.assertEquals(100, customers.get(customerId).balance);
    }

    @Test
    public void orderConfirmed_customerBalanceChanged() throws Exception {
        Customers customers = new Customers(eventBus);
        UUID customerId = createCustomer("Ivan", 200);
        UUID orderId = createOrder(customerId);

        commandBus.dispatch(new ProcessOrderCommand(customerId, orderId, Arrays.asList(matrix11Id), 3));

        Assert.assertNotNull(customers.get(customerId));
        Assert.assertEquals(80, customers.get(customerId).balance);
    }
}
