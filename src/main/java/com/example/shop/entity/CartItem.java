package com.example.shop.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cart_items")
@Data
public class CartItem {

    @Id
    @SequenceGenerator(name = "carts_id_seq", sequenceName = "carts_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int amount;

}
