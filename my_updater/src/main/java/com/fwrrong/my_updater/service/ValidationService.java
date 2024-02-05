package com.fwrrong.my_updater.service;

import com.fwrrong.my_updater.model.Product;
import com.fwrrong.my_updater.repository.ProductRepository;
import com.fwrrong.my_updater.repository.UrlRepository;
import com.fwrrong.my_updater.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

//URL_WHITELIST = List[ "https://apple.com"] // fetch from database

@Service
public class ValidationService {

    UrlRepository urlRepository;
    ProductRepository productRepository;
    UserRepository userRepository;

    public boolean validateProductId(UUID productId) {
        // Check if the product ID is null
        if (productId == null) {
            return false;
        }
        // Check if the product ID exists in the product repository
        return productRepository.existsById(productId);
    }

    public boolean validateProductUrl(String url){
        if (url == null || url.isEmpty() || !urlRepository.findUrlByUrl(url)) {
            return false;
        }
        return true;
    }

    public boolean validateProductName(String name) {
        // Check if the name is not null and not just whitespace
        return name != null && !name.trim().isEmpty();
    }

    public boolean validateProductInStock(Boolean inStock) {
        // Check if inStock is not null - assuming every product must explicitly be marked in/out of stock
        return inStock != null;
    }

    public boolean validateProductImage(String imageUrl) {
        // Check if the imageUrl is not null and matches a basic URL format
        // This regex checks for a simple URL format and can be expanded to be more specific if needed
        return imageUrl != null && imageUrl.matches("^(http[s]?://).*(\\.png|\\.jpg|\\.jpeg)$");
    }

    public boolean validateProductSize(String size) {
        // Check if the size is not null and not just whitespace
        // Additional specific checks can be added based on the expected format or values of size
        return size != null && !size.trim().isEmpty();
    }

    public boolean validateProduct(Product product) {
        // Validate product ID
        if (product.getId() == null || !productRepository.existsById(product.getId())) {
            return false;
        }

        // Validate product URL
        String url = product.getUrl();
        if (url == null || url.isEmpty() || !urlRepository.findUrlByUrl(url)) {
            return false;
        }

        // Validate product name
        String name = product.getName();
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        // Validate image URL format
        String imageUrl = product.getImage();
        if (imageUrl != null && !imageUrl.matches("^(http[s]?://).*(\\.png|\\.jpg|\\.jpeg)$")) {
            return false;
        }

        // Validate size
        String size = product.getSize();
        if (size == null || size.trim().isEmpty()) {
            return false;
        }

        return true; // If all validations pass
    }

    public boolean validateUserId(UUID id) {
        if (id == null || !userRepository.existsById(id)) {
            return false;
        }
        return true;
    }
    public boolean validateUserName(String name) {
        return name != null && !name.trim().isEmpty(); // Basic check for non-null and non-empty
        // Add more conditions here if there are specific rules for name (e.g., length, characters)
    }
    public boolean validateUserPassword(String password) {
        // Example: Minimum eight characters, at least one letter, one number, and one special character
        String passwordRegex = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$";
        return password != null && password.matches(passwordRegex);
    }
    public boolean validateUserEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }
}
