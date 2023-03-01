package com.example.shop.service;

import com.example.shop.entity.Category;
import com.example.shop.entity.Option;
import com.example.shop.entity.Product;
import com.example.shop.entity.Value;
import com.example.shop.repository.CategoryRepository;
import com.example.shop.repository.OptionRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.ValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ValueRepository valueRepository;
    private final OptionRepository optionRepository;

    public List<Category> allCategory() {
        return categoryRepository.findAll();
    }

    public Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow();
    }

    public List<Product> filteredProducts(Long categoryId, Integer minPrice, Integer maxPrice) {
        List<Product> products;
        if (categoryId != null && minPrice != null && maxPrice != null) {
            if (categoryId == 0L) {
                products = productRepository.findAllByPriceBetween(minPrice, maxPrice);
            } else {
                products = productRepository.findAllByCategoryIdAndPriceBetween(categoryId, minPrice, maxPrice);
            }
        } else if (categoryId != null && minPrice == null && maxPrice != null || categoryId != null && minPrice != null) {
            if (minPrice == null) {
                products = productRepository.findAllByPriceBefore(maxPrice);
            } else {
                products = productRepository.findAllByPriceAfter(minPrice);
            }
        } else if (categoryId != null) {
            products = productRepository.findAllByCategoryId(categoryId);
        } else {
            products = productRepository.findAll();
        }
        return products;
    }

    public void productSave(Long category, String name, Integer price, List<Long> optionId, List<String> valueName) {
        Category c = new Category();
        c.setId(category);
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCategory(c);
        productRepository.save(product);

        for (int i = 0; i < optionId.size(); i++) {
            Option option = new Option();
            option.setId(optionId.get(i));
            Value value = new Value();
            value.setProduct(product);
            value.setValue(valueName.get(i));
            value.setOption(option);
            valueRepository.save(value);
        }
    }

    public void productEdit(Long productId, String name, Integer price, List<Long> optionId, List<String> valueName) {
        Product product = productRepository.findById(productId).get();
        if (!name.isEmpty()) {
            product.setName(name);
        }
        if (price != null) {
            product.setPrice(price);
        }
        productRepository.save(product);
        for (int i = 0; i < optionId.size(); i++) {
            Option option = new Option();
            option.setId(optionId.get(i));
            Value value = valueRepository.findByProductAndOption(product, option);
            if(value==null){
                value=new Value();
                value.setProduct(product);
                value.setOption(option);
            }
            if (!valueName.get(i).isBlank()) {
                value.setValue(valueName.get(i));
            }
            valueRepository.save(value);
        }
    }

    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    public Product findProductById(Long productId) {
        return productRepository.findById(productId).get();
    }

    public Category findCategoryByProductId(Long productId) {
        Product product = productRepository.findById(productId).get();
        return product.getCategory();
    }

    public String findValueByProductAndOption(Long productId, Long optionId) {
        Product product = productRepository.findById(productId).get();
        Option option = optionRepository.findById(optionId).get();
        Value value = valueRepository.findByProductAndOption(product, option);
        if (value != null) {
            return value.getValue();
        } else {
            return " ";
        }
    }
}
