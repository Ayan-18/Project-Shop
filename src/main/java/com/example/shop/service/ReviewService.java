package com.example.shop.service;

import com.example.shop.entity.Product;
import com.example.shop.entity.Review;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final ProductService productService;

    public void reviewSave(String newReview, Long productId, int rateReview) {
        Product product = productRepository.findById(productId).get();
        Review review = new Review();
        review.setReview(newReview);
        review.setUser(userService.getUser());
        review.setPublished(false);
        review.setProduct(product);
        review.setRating(rateReview);
        review.setDate_of_write(LocalDateTime.now());

        if (reviewRepository.findByUserAndProduct(userService.getUser(), product) == null) {
            reviewRepository.save(review);
        }
    }

    public double productRating(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        List<Review> reviews = reviewRepository.findAllByProductAndPublished(product, true);
        if (reviews.isEmpty()) {
            return 0;
        }
        double rating = 0;
        for (Review review : reviews) {
            rating += review.getRating();
        }
        return rating / reviews.size();
    }

    public List<Review> findAllReviewsByProduct(Long productId) {
        return reviewRepository.findAllByProductAndPublished(productService.findProductById(productId), true);
    }

    public List<Review> findAllReviewsNotPublished() {
        return reviewRepository.findAllByPublished(false);
    }

    public boolean ifReviewExist(Long productId) {
        Product product = productRepository.findById(productId).get();
        return reviewRepository.findByUserAndProduct(userService.getUser(), product) == null;
    }

    public void reviewEdit(Long reviewId, boolean published) {
        Review review = reviewRepository.findById(reviewId).get();
        review.setPublished(published);
        reviewRepository.save(review);
    }
}
