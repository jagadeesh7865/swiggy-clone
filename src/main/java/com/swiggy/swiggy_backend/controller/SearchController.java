package com.swiggy.swiggy_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swiggy.swiggy_backend.dto.SearchResponse;
import com.swiggy.swiggy_backend.service.SearchService;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public List<SearchResponse> searchRestaurants(
            @RequestParam String keyword) {

        return searchService.searchRestaurants(keyword);
    }
}