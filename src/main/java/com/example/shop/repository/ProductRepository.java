package com.example.shop.repository;

import com.example.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // select p from Product p where p.price = ?
    List<Product> findAllByPriceAfter(int from);
    List<Product>findAllByPriceBefore(int before);

    // select p from Product p join Category c on p.category_id = c.id where c.id = ?
    List<Product> findAllByCategoryId(Long id);

    // select p from Product p join Category c on c.id=p.category_id where c.name = ?
    List<Product> findAllByCategory_Name(String name);

    //select p from Product p where p.name like'%?%' and p.price between ? and ?
    List<Product> findAllByNameContainsAndPriceBetween(String name, Integer price, Integer price2);


    @Query(value = "select p from Product p where p.name like ?1 and p.price between ?2 and ?3")
    List<Product> findAllByNameAndPrice(String name, int price1, int price2);

    List<Product> findAllByCategoryIdAndPriceBetween(Long id, int price1, int price2);

    List<Product> findAllByPriceBetween(int price1, int price2);
    Optional<Product> findById(Long id);
}
