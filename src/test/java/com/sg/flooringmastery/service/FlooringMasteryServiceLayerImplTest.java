package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.PersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.State;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author solomon
 */
public class FlooringMasteryServiceLayerImplTest {

    private final FlooringMasteryServiceLayer testService;

    public FlooringMasteryServiceLayerImplTest() {
        AnnotationConfigApplicationContext testContext
                = new AnnotationConfigApplicationContext();
        testContext.scan("com.sg.flooringmastery.service");
        testContext.refresh();
        testService = testContext.getBean("flooringMasteryServiceLayerImpl",
                FlooringMasteryServiceLayerImpl.class);
    }

    @Test
    public void testGetOrdersByDateNoOrders() {
        LocalDate date = LocalDate.parse("1982-01-01");
        try {
            testService.getOrdersByDate(date);
            fail("Expected NoOrdersOnDateException was not thrown.");
        } catch (PersistenceException e) {
            fail("Incorrect exception was thrown.");
        } catch (NoOrdersOnDateException e) {

        }
    }

    @Test
    public void testGetOrdersByDateSuccess() {
        LocalDate date = LocalDate.parse("2022-05-10");
        try {
            List<Order> orders = testService.getOrdersByDate(date);
            Order mike = new Order(14, "Mike Bob", "TX", new BigDecimal("4.45"),
                    "Carpet", new BigDecimal(217), new BigDecimal("2.25"), new BigDecimal("2.10"));
            Order dave = new Order(15, "Dave John", "TX", new BigDecimal("4.45"),
                    "Carpet", new BigDecimal(100), new BigDecimal("2.25"), new BigDecimal("2.10"));
            assertNotNull(orders, "List should not be null.");
            assertEquals(2, orders.size(), "List should have 2 orders.");
            assertTrue(orders.contains(mike), "List should contain Mike.");
            assertTrue(orders.contains(dave), "List should contain Dave.");
        } catch (PersistenceException | NoOrdersOnDateException e) {
            fail("No exceptions should be thrown.");
        }
    }

    @Test
    public void testGetOrderByIdOnDateInvalidNumber() {
        LocalDate date = LocalDate.parse("2022-05-10");
        try {
            testService.getOrderByIdOnDate(4, date);
            fail("Expected InvalidOrderNumberException was not thrown.");
        } catch (PersistenceException e) {
            fail("Incorrect exception was thrown.");
        } catch (InvalidOrderNumberException e) {

        }
    }

    @Test
    public void testGetOrderByIdOnDateSuccess() {
        LocalDate date = LocalDate.parse("2022-05-10");
        try {
            Order mike = new Order(14, "Mike Bob", "TX", new BigDecimal("4.45"),
                    "Carpet", new BigDecimal(217), new BigDecimal("2.25"), new BigDecimal("2.10"));
            assertEquals(mike, testService.getOrderByIdOnDate(14, date),
                    "Returned order should be Mike.");
        } catch (PersistenceException | InvalidOrderNumberException e) {
            fail("No exceptions should be thrown.");
        }
    }

    @Test
    public void testAddOrder() {
        LocalDate date = LocalDate.parse("2022-05-10");
        Order peter = new Order(16, "Peter Harry", "TX", new BigDecimal("4.45"),
                "Carpet", new BigDecimal(315), new BigDecimal("2.25"), new BigDecimal("2.10"));
        try {
            Order returnedOrder = testService.addOrder(peter, date);
            assertNull(returnedOrder, "Result should be null, indicating successful addition.");
            Order originalMike = new Order(14, "Mike Bob", "TX", new BigDecimal("4.45"),
                    "Carpet", new BigDecimal(217), new BigDecimal("2.25"), new BigDecimal("2.10"));
            Order editedMike = new Order(14, "Mike Bob", "TX", new BigDecimal("4.45"),
                    "Carpet", new BigDecimal(210), new BigDecimal("2.25"), new BigDecimal("2.10"));
            returnedOrder = testService.addOrder(editedMike, date);
            assertEquals(originalMike, returnedOrder, "Result should be original Mike, indication successful edit.");
        } catch (PersistenceException e) {
            fail("No exceptions should be thrown.");
        }
    }

    @Test
    public void testRemoveOrder() {
        LocalDate date = LocalDate.parse("2022-05-10");
        try {
            Order removedOrder = testService.removeOrder(10, date);
            assertNull(removedOrder, "Result should be null since no order with ID 10.");
            removedOrder = testService.removeOrder(14, date);
            Order mike = new Order(14, "Mike Bob", "TX", new BigDecimal("4.45"),
                    "Carpet", new BigDecimal(217), new BigDecimal("2.25"), new BigDecimal("2.10"));
            assertEquals(mike, removedOrder, "Removed order should be Mike.");
        } catch (PersistenceException e) {
            fail("No exceptions should be thrown.");
        }
    }

    @Test
    public void testGetStateByAbbreviationInvalidState() {
        try {
            testService.getStateByAbbreviation("Canada");
            fail("Expected InvalidStateException was not thrown.");
        } catch (PersistenceException e) {
            fail("Invalid exception was thrown.");
        } catch (InvalidStateException e) {

        }
    }

    @Test
    public void testGetStateByAbbreviationSuccess() {
        try {
            State returnedState = testService.getStateByAbbreviation("TX");
            State texas = new State("TX", "Texas", new BigDecimal("4.45"));
            assertEquals(texas, returnedState, "Returned state should be Texas.");
        } catch (PersistenceException | InvalidStateException e) {
            fail("No exceptions should be thrown.");
        }
    }

    @Test
    public void testGetAllProducts() {
        try {
            List<Product> products = testService.getAllProducts();
            assertNotNull(products, "List should not be null.");
            assertEquals(1, products.size(), "List should have 1 product.");
            Product carpet = new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10"));
            assertTrue(products.contains(carpet), "List should contain Carpet.");
        } catch (PersistenceException e) {
            fail("No exceptions should be thrown.");
        }
    }

}