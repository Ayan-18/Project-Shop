package com.example.shop.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "order_products")
@Data
public class OrderProduct {

    @Id
    @SequenceGenerator(name = "order_products_id_seq", sequenceName = "order_products_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int amount;
}
