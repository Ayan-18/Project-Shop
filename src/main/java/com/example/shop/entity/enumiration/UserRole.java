package com.example.shop.entity.enumiration;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("Администратор"),
    MODERATOR("Модератор"),
    USER("Пользователь");

    private final String displayName;

    UserRole(String displayName){
        this.displayName=displayName;
    }
}
