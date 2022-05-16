/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import static java.math.RoundingMode.HALF_UP;
import java.util.Objects;

/**
 *
 * @author solomon
 */
public class Order {

    private static int orderNumberCounter = 0;
    private int orderNumber;
    private String customerName;
    private String stateAbbreviation;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal labourCostPerSquareFoot;
    private final BigDecimal materialCost;
    private final BigDecimal labourCost;
    private final BigDecimal tax;
    private final BigDecimal totalCost;

 
    public Order(String customerName, String stateAbbreviation, BigDecimal taxRate,
            String productType, BigDecimal area, BigDecimal costPerSquareFoot,
            BigDecimal labourCostPerSquareFoot) {
        this.orderNumber = orderNumberCounter++;
        this.customerName = customerName;
        this.stateAbbreviation = stateAbbreviation;
        this.taxRate = taxRate.setScale(2, HALF_UP);
        this.productType = productType;
        this.area = area.setScale(2, HALF_UP);
        this.costPerSquareFoot = costPerSquareFoot.setScale(2, HALF_UP);
        this.labourCostPerSquareFoot = labourCostPerSquareFoot.setScale(2, HALF_UP);
        materialCost = area.multiply(costPerSquareFoot).setScale(2, HALF_UP);
        labourCost = area.multiply(labourCostPerSquareFoot).setScale(2, HALF_UP);
        tax = (materialCost.add(labourCost))
                .multiply(taxRate
                        .divide(new BigDecimal(100))).setScale(2, HALF_UP);
        totalCost = materialCost.add(labourCost).add(tax);
    }

  
    public Order(int orderNumber, String customerName, String stateAbbreviation,
            BigDecimal taxRate, String productType, BigDecimal area,
            BigDecimal costPerSquareFoot, BigDecimal labourCostPerSquareFoot) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.stateAbbreviation = stateAbbreviation;
        this.taxRate = taxRate.setScale(2, HALF_UP);
        this.productType = productType;
        this.area = area.setScale(2, HALF_UP);
        this.costPerSquareFoot = costPerSquareFoot.setScale(2, HALF_UP);
        this.labourCostPerSquareFoot = labourCostPerSquareFoot.setScale(2, HALF_UP);
        materialCost = area.multiply(costPerSquareFoot).setScale(2, HALF_UP);
        labourCost = area.multiply(labourCostPerSquareFoot).setScale(2, HALF_UP);
        tax = (materialCost.add(labourCost))
                .multiply(taxRate
                        .divide(new BigDecimal(100))).setScale(2, HALF_UP);
        totalCost = materialCost.add(labourCost).add(tax);
    }

    public static int getOrderNumberCounter() {
        return orderNumberCounter;
    }

    public static void setOrderNumberCounter(int orderNumberCounter) {
        Order.orderNumberCounter = orderNumberCounter;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLabourCostPerSquareFoot() {
        return labourCostPerSquareFoot;
    }

    public void setLabourCostPerSquareFoot(BigDecimal labourCostPerSquareFoot) {
        this.labourCostPerSquareFoot = labourCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public BigDecimal getLabourCost() {
        return labourCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.orderNumber;
        hash = 89 * hash + Objects.hashCode(this.customerName);
        hash = 89 * hash + Objects.hashCode(this.stateAbbreviation);
        hash = 89 * hash + Objects.hashCode(this.taxRate);
        hash = 89 * hash + Objects.hashCode(this.productType);
        hash = 89 * hash + Objects.hashCode(this.area);
        hash = 89 * hash + Objects.hashCode(this.costPerSquareFoot);
        hash = 89 * hash + Objects.hashCode(this.labourCostPerSquareFoot);
        hash = 89 * hash + Objects.hashCode(this.materialCost);
        hash = 89 * hash + Objects.hashCode(this.labourCost);
        hash = 89 * hash + Objects.hashCode(this.tax);
        hash = 89 * hash + Objects.hashCode(this.totalCost);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.stateAbbreviation, other.stateAbbreviation)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.costPerSquareFoot, other.costPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.labourCostPerSquareFoot, other.labourCostPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.labourCost, other.labourCost)) {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.totalCost, other.totalCost)) {
            return false;
        }
        return true;
    }


    public String toString(String DELIMITER) {
        return orderNumber + DELIMITER + customerName + DELIMITER
                + stateAbbreviation + DELIMITER + taxRate + DELIMITER
                + productType + DELIMITER + area + DELIMITER + costPerSquareFoot
                + DELIMITER + labourCostPerSquareFoot + DELIMITER + materialCost
                + DELIMITER + labourCost + DELIMITER + tax + DELIMITER
                + totalCost;
    }

}