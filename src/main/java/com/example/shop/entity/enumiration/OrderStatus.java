package com.example.shop.entity.enumiration;

import lombok.Getter;

@Getter
public enum OrderStatus {
    INSTOCK("На складе"),
    DELIVERY("Доставка"),
    DELIVERED("Доставлен");
    private final String displayStatus;

    OrderStatus(String displayStatus) {
        this.displayStatus = displayStatus;
    }
}
