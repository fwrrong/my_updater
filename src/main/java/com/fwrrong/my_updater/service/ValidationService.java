package com.fwrrong.my_updater.service;

import com.fwrrong.my_updater.model.Product;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

//URL_WHITELIST = List[ "https://apple.com"] // fetch from database

@Service
public class ValidationService {

    private static final List<String> URL_WHITELIST = Arrays.asList("https://apple.com");
    public boolean validateProduct(Product product) {
//        String url = product.getLink();
//        if (!URL_WHITELIST.contains(url)) {
//            return false;
//        }
        return true;
    }
}
