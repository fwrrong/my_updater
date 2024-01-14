package com.fwrrong.my_updater.service;

import com.fwrrong.my_updater.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SearchService {
    public ArrayList<Product> searchProduct(String query) {
        ArrayList<Product> products = new ArrayList<>();
        return products;
    }
}
