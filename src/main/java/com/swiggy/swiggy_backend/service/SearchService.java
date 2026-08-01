package com.swiggy.swiggy_backend.service;

import java.util.List;

import com.swiggy.swiggy_backend.dto.SearchResponse;

public interface SearchService {

    List<SearchResponse> searchRestaurants(String keyword);

}