package com.lightlogistics.warehouse.controller;

import com.lightlogistics.warehouse.model.item.Item;
import com.lightlogistics.warehouse.model.item.ItemStockHandler;
import com.lightlogistics.warehouse.service.ItemService;
import com.lightlogistics.warehouse.service.ItemStockHandlerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

//FT-05: Populating the manage-stock section
    @Test
    void testPopulateManageStockSection() {
        Model mockModel = mock(Model.class);
        Item mockItem = new Item();
        mockItem.setName("Sample Item");
        when(itemService.getByName("Sample Item")).thenReturn(Optional.of(mockItem));
        String viewName = homeController.manageStock("Sample Item", 10, new BigDecimal("5.5"), mockModel);
        verify(itemService).getByName("Sample Item");
        verify(mockModel).addAttribute(eq("items"), any());
        verify(mockModel).addAttribute(eq("stockHandlers"), any());
        assertEquals("home", viewName);
    }

//FT-06: Submitting the quantity and weight
    @Test
    void testSubmitItemQuantityAndWeight() {
        // Mock dependencies
        Model mockModel = mock(Model.class);
        Item mockItem = new Item();
        mockItem.setName("Sample Item");
        mockItem.setCategory("Electronics");
        when(itemService.getByName("Sample Item")).thenReturn(Optional.of(mockItem));
        when(itemService.getAll()).thenReturn(List.of(mockItem));
        when(itemStockHandlerService.getAll()).thenReturn(List.of());
        // Call the method
        homeController.manageStock("Sample Item", 10, new BigDecimal("5.5"), mockModel);
        // Capture the ItemStockHandler saved to the service
        ArgumentCaptor<ItemStockHandler> captor = ArgumentCaptor.forClass(ItemStockHandler.class);
        verify(itemStockHandlerService).save(captor.capture());
        // Verify the attributes of the captured ItemStockHandler
        ItemStockHandler capturedHandler = captor.getValue();
        assertEquals("Sample Item", capturedHandler.getName());
        assertEquals("Electronics", capturedHandler.getCategory());
        assertEquals(10, capturedHandler.getQuantity());
        assertEquals(new BigDecimal("5.5"), capturedHandler.getWeight());
    }

//FT-07 - Populating the form with correct fields
    @Test
    void testPopulateFormWithCorrectFields() {
        // Mock dependencies
        Model mockModel = mock(Model.class);
        Item mockItem = new Item();
        mockItem.setName("Sample Item");
        when(itemService.getByName("Sample Item")).thenReturn(Optional.of(mockItem));

        // Call the method
        homeController.manageStock("Sample Item", 10, new BigDecimal("5.5"), mockModel);

        // Verify that the correct item was retrieved
        verify(itemService).getByName("Sample Item");

        // Verify no exception was thrown for missing item
        verify(mockModel).addAttribute(eq("items"), any());
    }

//FT-08: Postcode Validation
    @Test
    void testManageStockItemNotFound() {
        // Mock behavior when item is not found
        when(itemService.getByName("Nonexistent Item")).thenReturn(Optional.empty());
        // Expect RuntimeException when item is not found
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            homeController.manageStock("Nonexistent Item", 10, new BigDecimal("5.5"), mock(Model.class));
        });
        assertEquals("Item not found", exception.getMessage());
    }





}
