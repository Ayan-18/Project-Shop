package com.example.shop.repository;

import com.example.shop.entity.Product;
import com.example.shop.entity.Review;
import com.example.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByProductAndPublished(Product product,boolean bool);
    List<Review> findAllByPublished(boolean bool);
    Review findByUserAndProduct(User user, Product product);
}
