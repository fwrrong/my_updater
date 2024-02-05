package com.fwrrong.my_updater.service;

import com.fwrrong.my_updater.exception.DeleteProductException;
import com.fwrrong.my_updater.exception.GetProductException;
import com.fwrrong.my_updater.exception.AddProductException;
import com.fwrrong.my_updater.exception.ModifyProductException;
import com.fwrrong.my_updater.model.Product;
import com.fwrrong.my_updater.repository.ProductRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getFeaturedProduct() {
        // TODO: modify this to be featured products instead of all products
        return productRepository.findAll();
    }

//    public Product addProduct(String name, String image, String url, String size) {
////        TODO: unit test
//        Product product = new Product(name, image, url, size);
//
//        try {
//            productRepository.save(product);
//        } catch (IllegalArgumentException e) {
//            throw new AddProductException("Invalid product", e);
//        } catch (OptimisticLockingFailureException e) {
//            throw new AddProductException("Internal Error, please try again", e);
//        }
//
//        return product;
//    }

    public Product getProduct(UUID uuid) {
//        TODO: unit test
        Optional<Product> product;
        System.out.println(productRepository.findAll());
        try{
            product = productRepository.findById(uuid);
        } catch (IllegalArgumentException e) {
            throw new GetProductException("Invalid product ID", e);
        }

        return product.orElse(null);
    }
    public Product getProductByName(String name) {
        return productRepository.findByName(name).orElse(null);
    }

    public Product modifyProduct(UUID id, String name, Boolean inStock, String image, String url, String size) {
//        TODO: unit test
        Product product = new Product(id, name, inStock, image, url, size);
        Product updatedProduct;

        try {
            updatedProduct = productRepository.save(product);
        } catch (IllegalArgumentException e) {
            throw new ModifyProductException("Invalid product", e);
        } catch (OptimisticLockingFailureException e) {
            throw new ModifyProductException("Internal Error, please try again", e);
        }

        return updatedProduct;
    }

    public void deleteProduct(UUID uuid) {
//        TODO: unit test
        try {
            productRepository.deleteById(uuid);
        } catch (IllegalArgumentException e) {
            throw new DeleteProductException("Invalid ID", e);
        }

    }
}
