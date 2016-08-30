package ru.okpdmarket.services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.okpdmarket.dao.ProductRepository;

/**
 * Controller for users web-service
 */
@RestController
@RequestMapping("/product")
public class ProductServiceController {

    private ProductRepository productRepo;



}
