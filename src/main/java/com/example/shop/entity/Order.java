package com.example.shop.entity;

import com.example.shop.entity.enumiration.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @SequenceGenerator(name = "orders_id_seq", sequenceName = "orders_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = EnumType.ORDINAL)
    private OrderStatus status;

    private String street;
    private String building;
    private int flat;
    private LocalDateTime dateOfReg;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;
}
