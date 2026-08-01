package com.swiggy.swiggy_backend.controller;

import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debug")
public class CacheDebugController {

    private final CacheManager cacheManager;

    public CacheDebugController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @GetMapping("/cache-names")
    public Object cacheNames() {
        return cacheManager.getCacheNames();
    }

    @GetMapping("/cache-put-test")
    public String cachePutTest() {
        cacheManager.getCache("restaurants").put("manual", "hello");
        return "done";
    }
}