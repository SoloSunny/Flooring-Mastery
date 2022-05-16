/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.PersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.State;
import com.sg.flooringmastery.service.FlooringMasteryServiceLayer;
import com.sg.flooringmastery.service.InvalidOrderNumberException;
import com.sg.flooringmastery.service.InvalidStateException;
import com.sg.flooringmastery.service.NoOrdersOnDateException;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author solomon
 */
@Component
public class FlooringMasteryController {

    FlooringMasteryView view;
    FlooringMasteryServiceLayer service;

    @Autowired
    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean keepGoing = true;
        try {
            while (keepGoing) {
                int menuChoice = displayMenuAndGetSelection();
                switch (menuChoice) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        exportOrders();
                        break;
                    case 6:
                        keepGoing = false;
                        view.displayExitBanner();
                }
            }
        } catch (PersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private int displayMenuAndGetSelection() {
        return view.displayMenuAndGetSelection();
    }

    private void displayOrders() throws PersistenceException {
        view.displayOrdersBanner();
        LocalDate date = view.askForDate();
        try {
            List<Order> ordersOnDate = service.getOrdersByDate(date);
            view.displayOrders(ordersOnDate, date);
        } catch (NoOrdersOnDateException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void addOrder() throws PersistenceException {
        view.displayAddBanner();
        LocalDate date = view.askForDateInFuture();
        String customerName = view.askForCustomerName();
        String stateAbbreviation = view.askForState();
        State state = null;
        try {
            state = service.getStateByAbbreviation(stateAbbreviation);
        } catch (InvalidStateException e) {
            view.displayErrorMessage(e.getMessage());
            return;
        }
        List<Product> products = service.getAllProducts();
        Product product = view.askForProductType(products);
        BigDecimal area = view.askForArea();
        service.setOrderNumberCounter();
        Order order = new Order(customerName, stateAbbreviation, state.getTaxRate(),
                product.getProductType(), area, product.getCostPerSquareFoot(),
                product.getLabourCostPerSquareFoot());
        view.displaySingleOrder(order, date);
        int confirmation = view.addOrderConfirmation();
        if (confirmation == 0) {
            view.addOrderAborted();
        } else {
            order = service.addOrder(order, date);
            view.displayAddResult(order);
        }

    }

    private void editOrder() throws PersistenceException {
        view.displayEditBanner();
        LocalDate date = view.askForDateInFuture();
        int id = view.askForOrderId();
        Order order = null;
        try {
            order = service.getOrderByIdOnDate(id, date);
        } catch (InvalidOrderNumberException e) {
            view.displayErrorMessage(e.getMessage());
            return;
        }
        String customerName = view.askForCustomerName(order);
        String stateAbbreviation = view.askForState(order);
        State state = null;
        try {
            state = service.getStateByAbbreviation(stateAbbreviation);
        } catch (InvalidStateException e) {
            view.displayErrorMessage(e.getMessage());
            return;
        }
        List<Product> products = service.getAllProducts();
        Product product = view.askForProductType(products, order);
        BigDecimal area = view.askForArea(order);
        Order editedOrder = new Order(id, customerName, stateAbbreviation,
                state.getTaxRate(), product.getProductType(), area,
                product.getCostPerSquareFoot(), product.getLabourCostPerSquareFoot());
        view.displaySingleOrder(editedOrder, date);
        int confirmation = view.editOrderConfirmation();
        if (confirmation == 0) {
            view.editOrderAborted();
        } else {
            editedOrder = service.addOrder(editedOrder, date);
            view.displayAddResult(editedOrder);
        }
    }

    private void removeOrder() throws PersistenceException {
        view.displayRemoveBanner();
        LocalDate date = view.askForDateInFuture();
        int id = view.askForOrderId();
        Order order = null;
        try {
            order = service.getOrderByIdOnDate(id, date);
        } catch (InvalidOrderNumberException e) {
            view.displayErrorMessage(e.getMessage());
        }
        view.displaySingleOrder(order, date);
        int confirmation = view.removeOrderConfirmation();
        if (confirmation == 0) {
            view.removeOrderAborted();
        } else {
            Order removedOrder = service.removeOrder(id, date);
            view.displayRemoveResult(removedOrder);
        }
    }

    private void exportOrders() throws PersistenceException {
        service.exportData();
        view.displayExportSuccessBanner();
    }

}