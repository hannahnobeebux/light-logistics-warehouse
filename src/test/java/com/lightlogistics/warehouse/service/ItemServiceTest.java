package com.lightlogistics.warehouse.service;

import com.lightlogistics.warehouse.model.item.Item;
import com.lightlogistics.warehouse.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        // Initialize the mocks
    }

    @Test
    void testGetByName() {
        Item item = new Item();
        item.setName("Laptop");

        // Mock the repository behavior
        when(itemRepository.findByName("Laptop")).thenReturn(Optional.of(item));

        // Test the service method
        Optional<Item> result = itemService.getByName("Laptop");

        assertTrue(result.isPresent());
        assertEquals("Laptop", result.get().getName());
    }
}
