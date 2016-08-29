package ru.okpdmarket.dao;

import ru.okpdmarket.model.Product;

/**
 * Created by Vladislav on 15.08.2016.
 */
public interface ProductRepository {

    List<Product> getProducts();

}
