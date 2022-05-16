package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author solomon
 */
public class OrderDaoFileImplTest {

    private OrderDao testDao;

    @BeforeEach
    public void setUp() throws PersistenceException {
        String directory = "FileData/TestFiles/TestOrders/";
        String testFilePrefix = "testorders_";
        try (PrintWriter out = new PrintWriter(
                new FileWriter(directory + testFilePrefix + "05102022.txt"))) {
            out.println("OrderNumber,CustomerName,State,TaxRate,"
                    + "ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,"
                    + "MaterialCost,LaborCost,Tax,Total");
            out.println("14,Mike Bob,TX,4.45,Carpet,217.00,2.25,2.10,"
                    + "488.25,455.70,42.01,985.96");
            out.println("15,Dave John,TX,4.45,Carpet,100,2.25,2.10,225.00,"
                    + "210.00,19.36,454.36");
            out.flush();
        } catch (IOException e) {
            throw new PersistenceException("Could not set up test inventory"
                    + "file.", e);
        }
        testDao = new OrderDaoFileImpl(directory, testFilePrefix);
    }

    @Test
    public void testGetOrdersByDate() throws PersistenceException {
        LocalDate date = LocalDate.parse("2022-05-10");
        List<Order> orders = testDao.getOrdersByDate(date);
        assertNotNull(orders, "List should not be null.");
        assertEquals(2, orders.size(), "List should have 2 orders.");
        Order mike = new Order(14, "Mike Bob", "TX", new BigDecimal("4.45"),
                "Carpet", new BigDecimal(217), new BigDecimal("2.25"), new BigDecimal("2.10"));
        Order dave = new Order(15, "Dave John", "TX", new BigDecimal("4.45"),
                "Carpet", new BigDecimal(100), new BigDecimal("2.25"), new BigDecimal("2.10"));
        assertTrue(orders.contains(mike), "List should contain Mike.");
        assertTrue(orders.contains(dave), "List should contain Dave.");
    }

    @Test
    public void testGetOrderByIdOnDate() throws PersistenceException {
        LocalDate date = LocalDate.parse("2022-05-10");
        Order mike = new Order(14, "Mike Bob", "TX", new BigDecimal("4.45"),
                "Carpet", new BigDecimal(217), new BigDecimal("2.25"), new BigDecimal("2.10"));
        Order retrievedOrder = testDao.getOrderByIdOnDate(14, date);
        assertEquals(mike, retrievedOrder, "Retrieved order should be Mike.");
    }

    @Test
    public void testAddOrder() throws PersistenceException {
        LocalDate date = LocalDate.parse("2022-05-10");
        Order peter = new Order(16, "Peter Harryy", "TX", new BigDecimal("4.45"),
                "Carpet", new BigDecimal(315), new BigDecimal("2.25"), new BigDecimal("2.10"));
        Order addOrderResult = testDao.addOrder(peter, date);
        assertNull(addOrderResult, "Return value of addOrder should be null if "
                + "a new order was successfully added.");


        Order editedPeter = new Order(16, "Peter Harry", "TX", new BigDecimal("4.45"),
                "Carpet", new BigDecimal(315), new BigDecimal("2.25"), new BigDecimal("2.10"));
        addOrderResult = testDao.addOrder(editedPeter, date);
        assertEquals(addOrderResult, peter, "Return value should be original order.");
    }

    @Test
    public void testRemoveOrder() throws PersistenceException {
        LocalDate date = LocalDate.parse("2022-05-10");
        Order mike = new Order(14, "Mike Bob", "TX", new BigDecimal("4.45"),
                "Carpet", new BigDecimal(217), new BigDecimal("2.25"), new BigDecimal("2.10"));
        Order removeOrderResult = testDao.removeOrder(14, date);
        assertEquals(mike, removeOrderResult, "Removed order should be Mike.");
        removeOrderResult = testDao.removeOrder(14, date);
        assertNull(removeOrderResult, "Result should be null since Mike was already "
                + "removed.");
    }

}