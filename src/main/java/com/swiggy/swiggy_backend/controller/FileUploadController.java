package com.swiggy.swiggy_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.swiggy.swiggy_backend.service.FileStorageService;
import com.swiggy.swiggy_backend.service.MenuService;
import com.swiggy.swiggy_backend.service.RestaurantService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuService menuService;

    @PostMapping(
            value = "/restaurant/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> uploadRestaurantImage(
            @PathVariable Long id,

            @Parameter(
                    description = "Restaurant Image",
                    schema = @Schema(type = "string", format = "binary")
            )
            @RequestPart("file") MultipartFile file) {

        String fileName = fileStorageService.storeFile(file);

        restaurantService.uploadRestaurantImage(id, fileName);

        return ResponseEntity.ok("Restaurant image uploaded successfully.");
    }

    @PostMapping(
            value = "/menu-item/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> uploadMenuItemImage(
            @PathVariable Long id,

            @Parameter(
                    description = "Menu Item Image",
                    schema = @Schema(type = "string", format = "binary")
            )
            @RequestPart("file") MultipartFile file) {

        String fileName = fileStorageService.storeFile(file);

        menuService.uploadMenuItemImage(id, fileName);

        return ResponseEntity.ok("Menu item image uploaded successfully.");
    }
}