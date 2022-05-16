/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author solomon
 */
@Component
public class FlooringMasteryView {

    @Autowired
    UserIo io;

    public int displayMenuAndGetSelection() {
        io.print("<<Flooring Program>>");
        io.print("1. Display orders");
        io.print("2. Add an order");
        io.print("3. Edit an order");
        io.print("4. Remove an order");
        io.print("5. Export data");
        io.print("6. Quit\n");
        return io.readInt("Please choose from the above selection:", 1, 6);
    }
    
    public LocalDate askForDateInFuture() {
        return io.readLocalDateInFuture();
    }

    public LocalDate askForDate() {
        return io.readLocalDate();
    }

    public int askForOrderId() {
        return io.readInt("Please enter the order number: ");
    }

    public void displaySingleOrder(Order order, LocalDate date) {
        io.print("\n***ORDER #" + order.getOrderNumber() + "***");
        io.print("Date: " + date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
        io.print("Customer name: " + order.getCustomerName());
        io.print("State abbreviation: " + order.getStateAbbreviation());
        io.print("Tax Rate: " + order.getTaxRate() + "%");
        io.print("Product: " + order.getProductType());
        io.print("Area: " + order.getArea() + " sq ft.");
        io.print("Cost per square foot: $" + order.getCostPerSquareFoot());
        io.print("Labour cost per square foot: $" + order.getLabourCostPerSquareFoot());
        io.print("Material cost: $" + order.getMaterialCost());
        io.print("Labour cost: $" + order.getLabourCost());
        io.print("Tax: $" + order.getTax());
        io.print("Total cost: $" + order.getTotalCost() + "\n");
    }

    public void displayOrders(List<Order> orders, LocalDate date) {
        for (Order order : orders) {
            displaySingleOrder(order, date);
        }
    }

    public String askForCustomerName() {
        String input = io.readNonEmptyString("Customer name: ");
        String regex = "^[a-zA-Z0-9\\.\\,\\s]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (!matcher.matches()) {
            input = io.readNonEmptyString("Use only alphanumeric, periods, spaces, and commas"
                    + " are valid. Please try again: ");
            matcher = pattern.matcher(input);
        }
        return input;
    }

    public String askForCustomerName(Order order) {
        String input = io.readString("Customer name (currently "
                + order.getCustomerName() + "): ");
        if (input.isEmpty()) {
            return order.getCustomerName();
        }
        String regex = "^[a-zA-Z0-9.,\\s]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (!matcher.matches()) {
            input = io.readString("Use only alphanumeric, periods, spaces, and commas"
                    + " are valid. Please try again: ");
            matcher = pattern.matcher(input);
        }
        return input;
    }

    public String askForState() {
        return io.readNonEmptyString("State abbreviation: ");
    }

    public String askForState(Order order) {
        String input = io.readString("State abbreviation (currently "
                + order.getStateAbbreviation() + "): ");
        if (input.isEmpty()) {
            return order.getStateAbbreviation();
        }
        return input;
    }

    public Product askForProductType(List<Product> products) {
        int i = 0;
        for (Product product : products) {
            io.print("\n***PRODUCT #" + i + "***");
            io.print("Product: " + product.getProductType());
            io.print("Cost per square foot: $" + product.getCostPerSquareFoot());
            io.print("Labour cost per square foot: $" + product.getLabourCostPerSquareFoot() + "\n");
            i++;
        }
        int index = io.readInt("Product number: ", 0, i - 1);
        return products.get(index);
    }

    public Product askForProductType(List<Product> products, Order order) {
        int i = 0;
        int currentProductNumber = 0;
        for (Product product : products) {
            io.print("\n***PRODUCT #" + i + "***");
            io.print("Product: " + product.getProductType());
            io.print("Cost per square foot: $" + product.getCostPerSquareFoot());
            io.print("Labour cost per square foot: $" + product.getLabourCostPerSquareFoot() + "\n");
            if (product.getProductType().equals(order.getProductType())
                    && product.getCostPerSquareFoot().equals(order.getLabourCostPerSquareFoot())
                    && product.getLabourCostPerSquareFoot().equals(order.getLabourCostPerSquareFoot())) {
                currentProductNumber = i;
            }
            i++;
        }
        int index = io.readIntPossiblyEmpty("Product number (currently " + currentProductNumber
                + "): ", 0, i - 1);
        if (index == -1) { 
            return products.get(currentProductNumber);
        }
        return products.get(index);
    }

    public BigDecimal askForArea() {
        return io.readBigDecimal("Area (minimum 100 sq ft): ",
                new BigDecimal(100));
    }

    public BigDecimal askForArea(Order order) {
        BigDecimal input = io.readBigDecimalPossiblyEmpty("Area (minimum 100 sq ft, currently "
                + order.getArea() + "sq ft): ", new BigDecimal(100));
        if (input.equals(new BigDecimal(99))) { 
            return order.getArea();
        }
        return input;
    }
    
    public int editOrderConfirmation() {
        return io.readInt("Enter 1 to confirm the edit or 0 to abort: ", 0, 1);
    }

    public int addOrderConfirmation() {
        return io.readInt("Enter 1 to confirm the new order or 0 to abort: ", 0, 1);
    }

    public int removeOrderConfirmation() {
        return io.readInt("Enter 1 to confirm the removal or 0 to abort: ", 0, 1);
    }

    public void displayErrorMessage(String message) {
        io.print("\nERROR: " + message + "\n");
    }

    public void displayOrdersBanner() {
        io.print("\n***DISPLAY ORDERS***");
    }

    public void displayAddBanner() {
        io.print("\n***ADD ORDER***");
    }

    public void displayEditBanner() {
        io.print("\n***EDIT ORDER***");
    }

    public void displayRemoveBanner() {
        io.print("\n***REMOVE ORDER***");
    }

    public void addOrderAborted() {
        io.print("\n***ADD ORDER ABORTED***\n");
    }

    public void editOrderAborted() {
        io.print("\n***EDIT ORDER ABORTED***\n");
    }

    public void removeOrderAborted() {
        io.print("\n***REMOVE ORDER ABORTED***\n");
    }

    public void displayAddResult(Order order) {
        if (order == null) {
            io.print("\n***ORDER SUCCESSFULLY ADDED***\n");
        } else {
            io.print("\n***ORDER #" + order.getOrderNumber()
                    + " SUCCESSFULLY EDITED***\n");
        }
    }
    
    public void displayExportSuccessBanner() {
        io.print("\n***DATA SUCCESSFULLY EXPORTED***\n");
    }

    public void displayRemoveResult(Order order) {
        if (order == null) {
            io.print("\nERROR: Removal failed.\n");
        } else {
            io.print("\n***ORDER #" + order.getOrderNumber()
                    + " SUCCESSFULLY REMOVED***\n");
        }
    }

    public void displayExitBanner() {
        io.print("\nThank you, see you round!!!");
    }

}