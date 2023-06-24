package com.example.pruvajvmvc.repository;

import com.example.pruvajvmvc.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
