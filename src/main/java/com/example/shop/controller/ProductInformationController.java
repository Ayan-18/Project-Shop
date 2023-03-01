package com.example.shop.controller;


import com.example.shop.service.ProductService;
import com.example.shop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/products")
public class ProductInformationController {
    private final ReviewService reviewService;
    private final ProductService productService;


    @GetMapping
    public ModelAndView mainPage(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice
    ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("repository/product_list_practice");
        modelAndView.addObject("products", productService.filteredProducts(categoryId, minPrice, maxPrice));
        modelAndView.addObject("categories", productService.allCategory());
        return modelAndView;
    }

    @GetMapping(path = "/info")
    public ModelAndView info(
            @RequestParam(required = false) Long productId
    ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("repository/product_information");
        modelAndView.addObject("reviews", reviewService.findAllReviewsByProduct(productId));
        modelAndView.addObject("product", productService.findProductById(productId));
        return modelAndView;
    }

    @PostMapping(path = "/info")
    public String infoAdd(
            @RequestParam Long productId,
            @RequestParam String newReview,
            @RequestParam Integer rateReview
    ) {
        reviewService.reviewSave(newReview, productId, rateReview);
        return "redirect:/products/info?productId=" + productId;
    }
}
