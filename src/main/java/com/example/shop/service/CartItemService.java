package com.example.shop.service;

import com.example.shop.entity.CartItem;
import com.example.shop.entity.Product;
import com.example.shop.entity.User;
import com.example.shop.repository.CartItemRepository;
import com.example.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartRepository;
    private final UserService userService;
    private final ProductRepository productRepository;

    public List<CartItem> findAllCartItemByUser() {
        return cartRepository.findAllByUser(userService.getUser());
    }
    public void cartItemMinus(Long productId){
        CartItem cartItem = cartRepository.findByProductAndUser(productRepository.findById(productId).get(), userService.getUser());
        cartItem.setAmount(cartItem.getAmount() - 1);
        if (cartItem.getAmount()==0){
            cartRepository.delete(cartItem);
        }else {
            cartRepository.save(cartItem);
        }
    }

    public void addCartItems(Long productId){
        if (productId != null) {
            CartItem cartItem = new CartItem();
            User user = userService.getUser();
            Product product = productRepository.findById(productId).get();
            CartItem cartItemCheck = cartRepository.findByProductAndUser(product, user);
            if (cartItemCheck == null) {
                cartItem.setProduct(product);
                cartItem.setAmount(cartItem.getAmount() + 1);
                cartItem.setUser(user);
                cartRepository.save(cartItem);
            } else {
                cartItemCheck.setAmount(cartItemCheck.getAmount() + 1);
                cartRepository.save(cartItemCheck);
            }
        }
    }
    public void cartItemPlus(Long productId){
        CartItem cartItem = cartRepository.findByProductAndUser(productRepository.findById(productId).get(), userService.getUser());
        cartItem.setAmount(cartItem.getAmount() + 1);
        cartRepository.save(cartItem);
    }
    public void cartItemDelete(Long productId){
        cartRepository.deleteByProductAndUser(productRepository.findById(productId).get(), userService.getUser());
    }
    public void cartItemDeleteAll(){
        cartRepository.deleteAllByUser(userService.getUser());

    }
}
