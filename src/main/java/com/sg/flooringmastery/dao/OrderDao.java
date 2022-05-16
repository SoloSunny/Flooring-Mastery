/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author solomon
 */
public interface OrderDao {

    List<Order> getOrdersByDate(LocalDate date) throws PersistenceException;

    Order getOrderByIdOnDate(int id, LocalDate date) throws PersistenceException;

    Order addOrder(Order order, LocalDate date) throws PersistenceException;

    Order removeOrder(int id, LocalDate date) throws PersistenceException;

}