package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.ProductDao;
import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author solomon
 */
@Component
public class ProductDaoStub implements ProductDao {

    @Override
    public List<Product> getAllProducts() {
        Product carpet = new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10"));
        List<Product> products = new ArrayList<>();
        products.add(carpet);
        return products;
    }

}