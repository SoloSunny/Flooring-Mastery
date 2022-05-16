/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.PersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.State;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author solomon
 */
public interface FlooringMasteryServiceLayer {

    List<Order> getOrdersByDate(LocalDate date) throws
            PersistenceException,
            NoOrdersOnDateException;

    Order getOrderByIdOnDate(int id, LocalDate date) throws
            PersistenceException,
            InvalidOrderNumberException;

    Order addOrder(Order order, LocalDate date) throws PersistenceException;

    Order removeOrder(int id, LocalDate date) throws PersistenceException;

    State getStateByAbbreviation(String abbreviation) throws
            PersistenceException,
            InvalidStateException;

    List<Product> getAllProducts() throws PersistenceException;

    void exportData() throws PersistenceException;

    void setOrderNumberCounter() throws PersistenceException;

}