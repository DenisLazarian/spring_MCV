package com.example.pruvajvmvc;

import com.example.pruvajvmvc.entities.Product;
import com.example.pruvajvmvc.repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(App.class, args);
        System.out.println("Funciona");

        var repository = context.getBean(ProductRepository.class);

        List<Product> products = List.of(
                new Product(null, "product1", 5.99, 44),
                new Product(null, "product2", 7.99, 5),
                new Product(null, "product3", 2.99, 4),
                new Product(null, "product4", 3.99, 1),
                new Product(null, "product5", 1.99, 9),
                new Product(null, "product6", 99.99, 9)
        );

        repository.saveAll(products);
    }

}
