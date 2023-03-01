package com.example.shop.repository;

import com.example.shop.entity.CartItem;
import com.example.shop.entity.Product;
import com.example.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByProductAndUser(Product product, User user);

    List<CartItem> findAllByUser(User user);
    @Transactional
    void deleteAllByUser(User user);

    @Transactional
    void deleteByProductAndUser(Product product, User user);
}