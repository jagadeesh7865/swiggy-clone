package com.swiggy.swiggy_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.swiggy.swiggy_backend.dto.MenuRequest;
import com.swiggy.swiggy_backend.dto.MenuResponse;
import com.swiggy.swiggy_backend.entity.MenuItem;
import com.swiggy.swiggy_backend.entity.Restaurant;
import com.swiggy.swiggy_backend.exception.ResourceNotFoundException;
import com.swiggy.swiggy_backend.repository.MenuRepository;
import com.swiggy.swiggy_backend.repository.RestaurantRepository;
import com.swiggy.swiggy_backend.service.impl.MenuServiceImpl;

@ExtendWith(MockitoExtension.class)
class MenuServiceImplTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private MenuServiceImpl menuService;
    
    
    
    @Test
    void shouldAddMenuItemSuccessfully() {

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        MenuRequest request = new MenuRequest();
        request.setName("Pizza");
        request.setDescription("Cheese Pizza");
        request.setPrice(250.0);
        request.setCategory("Veg");
        request.setAvailable(true);

        MenuItem savedItem = new MenuItem();
        savedItem.setId(1L);
        savedItem.setName("Pizza");
        savedItem.setDescription("Cheese Pizza");
        savedItem.setPrice(250.0);
        savedItem.setCategory("Veg");
        savedItem.setAvailable(true);
        savedItem.setRestaurant(restaurant);

        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.of(restaurant));

        when(menuRepository.save(any(MenuItem.class)))
                .thenReturn(savedItem);

        MenuResponse response =
                menuService.addMenuItem(1L, request);

        assertNotNull(response);
        assertEquals("Pizza", response.getName());
        assertEquals(1L, response.getRestaurantId());

        verify(menuRepository).save(any(MenuItem.class));
    }
    
    @Test
    void shouldThrowExceptionWhenRestaurantNotFound() {

        MenuRequest request = new MenuRequest();

        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> menuService.addMenuItem(1L, request));

        verify(menuRepository, never()).save(any());
    }
    
    @Test
    void shouldGetMenuByRestaurant() {

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        MenuItem item = new MenuItem();
        item.setId(1L);
        item.setName("Burger");
        item.setRestaurant(restaurant);

        when(menuRepository.findByRestaurantId(1L))
                .thenReturn(List.of(item));

        List<MenuResponse> response =
                menuService.getMenuByRestaurant(1L);

        assertEquals(1, response.size());
        assertEquals("Burger", response.get(0).getName());

        verify(menuRepository).findByRestaurantId(1L);
    }
    
    @Test
    void shouldReturnEmptyMenuList() {

        when(menuRepository.findByRestaurantId(1L))
                .thenReturn(Collections.emptyList());

        List<MenuResponse> response =
                menuService.getMenuByRestaurant(1L);

        assertTrue(response.isEmpty());

        verify(menuRepository).findByRestaurantId(1L);
    }
    
    @Test
    void shouldGetMenuItemById() {

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        MenuItem item = new MenuItem();
        item.setId(1L);
        item.setName("Pizza");
        item.setRestaurant(restaurant);

        when(menuRepository.findById(1L))
                .thenReturn(Optional.of(item));

        MenuResponse response =
                menuService.getMenuItemById(1L);

        assertEquals("Pizza", response.getName());

        verify(menuRepository).findById(1L);
    }
    
    @Test
    void shouldThrowWhenMenuItemNotFound() {

        when(menuRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> menuService.getMenuItemById(1L));

        verify(menuRepository).findById(1L);
    }
    
    @Test
    void shouldUpdateMenuItem() {

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        MenuItem item = new MenuItem();
        item.setId(1L);
        item.setRestaurant(restaurant);

        MenuRequest request = new MenuRequest();
        request.setName("Updated Pizza");
        request.setPrice(300.0);

        when(menuRepository.findById(1L))
                .thenReturn(Optional.of(item));

        when(menuRepository.save(any(MenuItem.class)))
                .thenReturn(item);

        MenuResponse response =
                menuService.updateMenuItem(1L, request);

        verify(menuRepository).save(any(MenuItem.class));
    }
    
    @Test
    void shouldDeleteMenuItem() {

        MenuItem item = new MenuItem();

        when(menuRepository.findById(1L))
                .thenReturn(Optional.of(item));

        menuService.deleteMenuItem(1L);

        verify(menuRepository).delete(item);
    }
    
    
    @Test
    void shouldThrowWhenDeletingUnknownMenuItem() {

        when(menuRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> menuService.deleteMenuItem(1L));

        verify(menuRepository, never()).delete(any());
    }
    
    @Test
    void shouldSearchMenu() {

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        MenuItem item = new MenuItem();
        item.setName("Pizza");
        item.setRestaurant(restaurant);

        when(menuRepository.findByNameContainingIgnoreCase("Pizza"))
                .thenReturn(List.of(item));

        List<MenuResponse> response =
                menuService.searchMenu("Pizza");

        assertEquals(1, response.size());

        verify(menuRepository)
                .findByNameContainingIgnoreCase("Pizza");
    }
    

}