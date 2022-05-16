package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.OrderDao;
import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author solomon
 */
@Component
public class OrderDaoStub implements OrderDao {

    private final Map<Integer, Order> orders = new HashMap<>();
    private final LocalDate date;

    public OrderDaoStub() {
        Order mike = new Order(14, "Mike Bob", "TX", new BigDecimal("4.45"),
                "Carpet", new BigDecimal(217), new BigDecimal("2.25"), new BigDecimal("2.10"));
        Order dave = new Order(15, "Dave John", "TX", new BigDecimal("4.45"),
                "Carpet", new BigDecimal(100), new BigDecimal("2.25"), new BigDecimal("2.10"));
        orders.put(mike.getOrderNumber(), mike);
        orders.put(dave.getOrderNumber(), dave);
        date = LocalDate.parse("2022-05-10");
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) {
        if (this.date.compareTo(date) == 0) {
            return new ArrayList<>(orders.values());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Order getOrderByIdOnDate(int id, LocalDate date) {
        if (this.date.compareTo(date) == 0) {
            return orders.get(id);
        } else {
            return null;
        }
    }

    @Override
    public Order addOrder(Order order, LocalDate date) {
        if (orders.keySet().contains(order.getOrderNumber())) {
            return orders.get(order.getOrderNumber());
        } else {
            return null;
        }
    }

    @Override
    public Order removeOrder(int id, LocalDate date) {
        if (orders.keySet().contains(id)) {
            return orders.get(id);
        } else {
            return null;
        }

    }

}