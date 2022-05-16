package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author solomon
 */
public class ProductDaoFileImplTest {

    private ProductDao testDao;

    @BeforeEach
    public void setUp() throws PersistenceException {
        String directory = "FileData/TestFiles/TestData/";
        String testFile = "testproduct.txt";
        try (PrintWriter out = new PrintWriter(
                new FileWriter(directory + testFile))) {
            out.println("ProductType,CostPerSquareFoot,LaborCostPerSquareFoot");
            out.println("Carpet,2.25,2.10");
            out.println("Laminate,1.75,2.10");
            out.flush();
        } catch (IOException e) {
            throw new PersistenceException("Could not set up test inventory"
                    + "file.", e);
        }
        testDao = new ProductDaoFileImpl(directory, testFile);
    }

    @Test
    public void getAllProductsTest() throws PersistenceException {
        List<Product> listOfProducts = testDao.getAllProducts();
        assertNotNull(listOfProducts, "List should not be null.");
        assertEquals(2, listOfProducts.size(), "List should have 2 products.");
        Product carpet = new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10"));
        Product laminate = new Product("Laminate", new BigDecimal("1.75"), new BigDecimal("2.10"));
        assertTrue(listOfProducts.contains(carpet), "List should contain "
                + "carpet.");
        assertTrue(listOfProducts.contains(laminate), "List should contain "
                + "laminate.");
    }

}