package com.example.shop.service;

import com.example.shop.entity.*;
import com.example.shop.entity.enumiration.OrderStatus;
import com.example.shop.repository.CartItemRepository;
import com.example.shop.repository.OrderProductRepository;
import com.example.shop.repository.OrderRepository;
import com.example.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserService userService;
    private final CartItemRepository cartItemRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private  final CartItemRepository cartRepository;

    public void addOrder(String street, String building, Integer flat) {
        List<CartItem> cartItems = cartItemRepository.findAllByUser(userService.getUser());
        Order order = new Order();
        order.setStreet(street);
        order.setBuilding(building);
        order.setFlat(flat);
        order.setUser(userService.getUser());
        order.setStatus(OrderStatus.INSTOCK);
        order.setDateOfReg(LocalDateTime.now());
        orderRepository.save(order);
        for (CartItem cart : cartItems) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(cart.getProduct());
            orderProduct.setAmount(cart.getAmount());
            orderProduct.setOrder(order);
            orderProductRepository.save(orderProduct);
        }
        cartItemRepository.deleteAllByUser(userService.getUser());
    }


    public List<Order> findOrderByUser() {
        return orderRepository.findAllByUser(userService.getUser());
    }

    public List<Order> orderList() {
        return orderRepository.findAll();
    }

    public void changeStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id).get();
        order.setStatus(status);
        orderRepository.save(order);
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).get();
    }

    public List<OrderProduct> orderProducts(Long orderId) {
        return orderProductRepository.findAllByOrder(orderRepository.findById(orderId).get());
    }

    public int totalPrice(Long orderId) {
        List<OrderProduct> orderProducts = orderProductRepository.findAllByOrder(orderRepository.findById(orderId).get());
        int allPrice = 0;
        for (OrderProduct op : orderProducts) {
            int amount = op.getAmount();
            int price = op.getProduct().getPrice();
            allPrice += amount * price;
        }
        return allPrice;
    }
}
