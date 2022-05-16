/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author solomon
 */
@Component
public class ProductDaoFileImpl implements ProductDao {

    private final String DATA_DIRECTORY;
    private final String PRODUCT_FILE;
    private final String DELIMITER = ",";
    private final List<Product> products = new ArrayList<>();

    public ProductDaoFileImpl() {
        DATA_DIRECTORY = "FileData/Data/";
        PRODUCT_FILE = "Products.txt";
    }

    public ProductDaoFileImpl(String directory, String fileName) {
        DATA_DIRECTORY = directory;
        PRODUCT_FILE = fileName;
    }

    @Override
    public List<Product> getAllProducts() throws PersistenceException {
        loadProducts();
        return products;
    }

    private void loadProducts() throws PersistenceException {
        products.clear();
        try (Scanner sc = new Scanner(
                new BufferedReader(
                        new FileReader(DATA_DIRECTORY + PRODUCT_FILE)))) {
            String currentLine = "";
            if (sc.hasNextLine()) {
                currentLine = sc.nextLine();
            }
            Product currentProduct;
            while (sc.hasNextLine()) {
                currentLine = sc.nextLine();
                currentProduct = unmarshallProduct(currentLine);
                products.add(currentProduct);
            }
        } catch (IOException e) {
            throw new PersistenceException("Could not load Product data into"
                    + " memory.", e);
        }
    }

    private Product unmarshallProduct(String productAsText) {
        String[] productTokens = productAsText.split(DELIMITER);
        return new Product(productTokens[0], new BigDecimal(productTokens[1]),
                new BigDecimal(productTokens[2]));
    }

}