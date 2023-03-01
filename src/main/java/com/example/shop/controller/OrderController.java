package com.example.shop.controller;

import com.example.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ModelAndView order() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order");
        return modelAndView;
    }

    @PostMapping
    public String addOrder(
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String building,
            @RequestParam(required = false) Integer flat
    ) {
        orderService.addOrder(street, building, flat);
        return "redirect:/order";
    }

    @GetMapping(path = "/watch")
    public ModelAndView watch(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("watch");
        modelAndView.addObject("orders",orderService.findOrderByUser());
        return modelAndView;
    }

    @GetMapping(path = "/watch/more")
    public ModelAndView more(
            @RequestParam Long orderId
    ){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("more");
        modelAndView.addObject("order",orderService.findOrderById(orderId));
        return modelAndView;
    }
}
