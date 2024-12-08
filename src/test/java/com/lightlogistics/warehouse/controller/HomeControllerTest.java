package com.lightlogistics.warehouse.controller;

import com.lightlogistics.warehouse.model.item.Item;
import com.lightlogistics.warehouse.model.item.ItemStockHandler;
import com.lightlogistics.warehouse.service.ItemService;
import com.lightlogistics.warehouse.service.ItemStockHandlerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class HomeControllerTest {

    @Mock
    private ItemService itemService;

    @Mock
    private ItemStockHandlerService itemStockHandlerService;

    @InjectMocks
    private HomeController homeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }


    @Test
    void testAddItem() {
        Model mockModel = mock(Model.class);
        Item mockItem = new Item();
        mockItem.setName("Sample Item");
        mockItem.setCategory("Electronics");
        List<Item> mockItems = List.of(mockItem);
        when(itemService.getAll()).thenReturn(mockItems);
        String viewName = homeController.addItem(mockItem, mockModel);
        verify(itemService).save(mockItem);
        verify(mockModel).addAttribute("items", mockItems);
        assertEquals("home", viewName);
    }
}
