package com.example.shop.controller;

import com.example.shop.service.CartItemService;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/cart")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;
    private final UserService userService;

    @GetMapping
    public ModelAndView cart() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cart");
        modelAndView.addObject("cartItemList", cartItemService.findAllCartItemByUser());
        modelAndView.addObject("user", userService.getUser());
        return modelAndView;
    }

    @GetMapping(path = "/add")
    public String cartAdd(@RequestParam(required = false) Long productId) {
        cartItemService.addCartItems(productId);
        return "redirect:/products";
    }

    @GetMapping(path = "/minus")
    public ModelAndView cartMinus(
            @RequestParam Long productId
    ) {
        cartItemService.cartItemMinus(productId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/cart");
        return modelAndView;
    }

    @GetMapping(path = "/plus")
    public ModelAndView cartPlus(
            @RequestParam Long productId
    ) {
        cartItemService.cartItemPlus(productId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/cart");
        return modelAndView;
    }

    @GetMapping(path = "/delete")
    public ModelAndView delete(
            @RequestParam Long productId
    ) {
        cartItemService.cartItemDelete(productId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/cart");
        return modelAndView;
    }

    @GetMapping(path = "/deleteall")
    public ModelAndView deleteAll() {
        cartItemService.cartItemDeleteAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/cart");
        return modelAndView;
    }
}