package com.example.shop.controller;

import com.example.shop.entity.enumiration.OrderStatus;
import com.example.shop.service.OrderService;
import com.example.shop.service.ProductService;
import com.example.shop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class ProductAddController {
    private final ProductService productService;
    private final ReviewService reviewService;
    private final OrderService orderService;

    @GetMapping
    public ModelAndView allProduct(
    ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product");
        modelAndView.addObject("products", productService.findAllProduct());
        modelAndView.addObject("categories", productService.allCategory());
        return modelAndView;
    }

    @GetMapping(path = "/add")
    public ModelAndView product(
            @RequestParam Long category
    ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product_add");
        modelAndView.addObject("category", productService.findCategoryById(category));
        return modelAndView;
    }

    @PostMapping(path = "/add")
    public String productAdd(
            @RequestParam(name = "category") Long category,
            @RequestParam String name,
            @RequestParam Integer price,
            @RequestParam List<Long> optionId,
            @RequestParam List<String> valueName
    ) {
        productService.productSave(category, name, price, optionId, valueName);
        return "redirect:/admin";
    }

    @GetMapping(path = "/edit")
    public ModelAndView edit(
            @RequestParam Long productId
    ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/product_edit");
        modelAndView.addObject("category", productService.findCategoryByProductId(productId));
        modelAndView.addObject("product", productService.findProductById(productId));
        return modelAndView;
    }

    @PostMapping(path = "/edit")
    public String productEdit(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer price,
            @RequestParam(required = false) List<Long> optionId,
            @RequestParam(required = false) List<String> valueName
    ) {
        productService.productEdit(productId, name, price, optionId, valueName);
        return "redirect:/admin/edit?productId=" + productId;
    }

    @GetMapping(path = "/review_edit")
    public ModelAndView review() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("review_edit");
        modelAndView.addObject("reviews", reviewService.findAllReviewsNotPublished());
        return modelAndView;
    }

    @PostMapping(path = "/review_edit")
    public String reviewEdit(
            @RequestParam (required = false) Long productId,
            @RequestParam(required = false) Long reviewId,
            @RequestParam(required = false) boolean published
    ) {
        reviewService.reviewEdit(reviewId, published);
        if (productId==null){
            return "redirect:/admin/review_edit";
        }else {
            return "redirect:/products/info?productId=" + productId;
        }
    }

    @GetMapping(path = "/order_edit")
    public ModelAndView edit() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order_edit");
        modelAndView.addObject("orders", orderService.orderList());
        return modelAndView;
    }

    @PostMapping(path = "/order_edit")
    public String orderEdit(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) OrderStatus status
    ) {
        orderService.changeStatus(id, status);
        return "redirect:/admin/order_edit";
    }
}
