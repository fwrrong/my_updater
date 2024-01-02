package com.fwrrong.my_updater.controller;

import com.fwrrong.my_updater.model.Product;
import com.fwrrong.my_updater.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class SearchServiceController {
    private SearchService searchService;

    @GetMapping("/v1/search/")
    public ResponseEntity<ArrayList<Product>> searchProduct(@RequestParam String query){
//        GET /v1/search/?query=xxxxxxx
//        Response: List<SearchResult>
        ArrayList<Product> products = searchService.searchProduct(query);
        return ResponseEntity.ok(products);
    }
}
