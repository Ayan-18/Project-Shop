package com.example.shop.repository;

import com.example.shop.entity.Option;
import com.example.shop.entity.Product;
import com.example.shop.entity.Value;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValueRepository extends JpaRepository<Value, Long> {
    Value findByProductAndOption(Product product, Option option);
}
