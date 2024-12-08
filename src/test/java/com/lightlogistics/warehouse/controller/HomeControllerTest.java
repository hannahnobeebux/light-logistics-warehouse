package com.lightlogistics.warehouse.controller;

import com.lightlogistics.warehouse.model.item.Item;
import com.lightlogistics.warehouse.model.item.ItemStockHandler;
import com.lightlogistics.warehouse.service.ItemService;
import com.lightlogistics.warehouse.service.ItemStockHandlerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class HomeControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private ItemStockHandlerService itemStockHandlerService;

    @Autowired
    private HomeController homeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        // Manually initialize MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();

        // Mock service behavior
        when(itemService.getAll()).thenReturn(List.of(new Item("Test Item")));
        when(itemStockHandlerService.getAll()).thenReturn(List.of(new ItemStockHandler()));
    }

    @Test
    void testHomePage() throws Exception {
        // Simulate a GET request to the home page and check the status code
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}