package com.lightlogistics.warehouse.controller;

import com.lightlogistics.warehouse.model.item.Item;
import com.lightlogistics.warehouse.model.item.ItemStockHandler;
import com.lightlogistics.warehouse.model.scanner.AddressScanner;
import com.lightlogistics.warehouse.service.ItemService;
import com.lightlogistics.warehouse.service.ItemStockHandlerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import java.util.List;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


class HomeControllerTest {

    @Mock
    private ItemService itemService;

    @Mock
    private ItemStockHandlerService itemStockHandlerService;

    @Mock
    private AddressScanner addressScanner;

    @Mock
    private Model model;

    @InjectMocks
    private HomeController homeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

//FT-01: Populating the add-item section with information for an item
    @Test
    void testPopulateAddItemSection() {
        Item newItem = new Item();
        newItem.setName("Printer ink");
        newItem.setCategory("Stationary");
        Model model = Mockito.mock(Model.class);
        homeController.addItem(newItem, model);
        // verify that the item was saved and the model was updated with all items
        verify(itemService).save(newItem);
        verify(model).addAttribute("items", itemService.getAll());
    }

//FT-02: Submitting the item by clicking the "Add item" button
//    Same as testPopulateAddItemSection because the add-item button should be tested manually on the UI
    @Test
    void testSubmitItemAndUpdateOverview() {
        Item newItem = new Item();
        newItem.setName("Printer paper");
        newItem.setCategory("Stationary");
        Model model = Mockito.mock(Model.class);
        homeController.addItem(newItem, model);
        verify(itemService).save(newItem);
        verify(model).addAttribute("items", itemService.getAll());
    }

//FT-03: Viewing the items in the warehouse on the GUI
    @Test
    void testViewItemsOnGUI() {
        Item existingItem = new Item();
        existingItem.setName("Printer ink");
        existingItem.setCategory("Stationary");
        Item newItem = new Item();
        newItem.setName("Office chair");
        newItem.setCategory("Furniture");
        List<Item> allItems = List.of(existingItem, newItem);
        when(itemService.getAll()).thenReturn(allItems);
        Model model = Mockito.mock(Model.class);
        homeController.addItem(newItem, model);
        verify(model).addAttribute("items", allItems);
    }

//FT-04: Adding a new item persists all fields of previously added items
//*BUG* This doesn't cover fields from ItemStockHandler (quantity and weight) and optional field of "Date"
    @Test
    void testAddingNewItemDoesNotAffectOtherItems() {
        Item existingItem = new Item();
        existingItem.setName("Printer ink");
        existingItem.setCategory("Stationary");
        Item newItem = new Item();
        newItem.setName("Office chair");
        newItem.setCategory("Furniture");
        List<Item> allItems = List.of(existingItem, newItem);
        when(itemService.getAll()).thenReturn(allItems);
        Model model = Mockito.mock(Model.class);
        homeController.addItem(newItem, model);
        List<Item> updatedItems = itemService.getAll();
        assertTrue(updatedItems.stream().anyMatch(item -> item.getName().equals("Printer ink")));
        assertTrue(updatedItems.stream().anyMatch(item -> item.getName().equals("Office chair")));
        verify(model).addAttribute("items", updatedItems);
    }

//FT-05: Populating the manage-stock section
    @Test
    void testPopulateManageStockSection() {
        Model mockModel = mock(Model.class);
        Item mockItem = new Item();
        mockItem.setName("Office chair");
        when(itemService.getByName("Office chair")).thenReturn(Optional.of(mockItem));
        String viewName = homeController.manageStock("Office chair", 10, new BigDecimal("5"), mockModel);
        verify(itemService).getByName("Office chair");
        verify(mockModel).addAttribute(eq("items"), any());
        verify(mockModel).addAttribute(eq("stockHandlers"), any());
        assertEquals("home", viewName);
    }

//FT-06: Submitting the quantity and weight
    @Test
    void testSubmitItemQuantityAndWeight() {
        Model mockModel = mock(Model.class);
        Item mockItem = new Item();
        mockItem.setName("Office chair");
        mockItem.setCategory("Furniture");
        when(itemService.getByName("Office chair")).thenReturn(Optional.of(mockItem));
        when(itemService.getAll()).thenReturn(List.of(mockItem));
        when(itemStockHandlerService.getAll()).thenReturn(List.of());
        homeController.manageStock("Office chair", 10, new BigDecimal("5"), mockModel);
        ArgumentCaptor<ItemStockHandler> captor = ArgumentCaptor.forClass(ItemStockHandler.class);
        verify(itemStockHandlerService).save(captor.capture());
        ItemStockHandler capturedHandler = captor.getValue();
        assertEquals("Office chair", capturedHandler.getName());
        assertEquals("Furniture", capturedHandler.getCategory());
        assertEquals(10, capturedHandler.getQuantity());
        assertEquals(new BigDecimal("5"), capturedHandler.getWeight());
    }

    // FT-07: Test that the form is populated with correct fields for a UK address
    @Test
    public void testPopulateAddressForm() throws Exception {
        mockMvc.perform(post("/scan-address")
                        .param("street", "123 Baker Street")
                        .param("city", "London")
                        .param("county", "Greater London")
                        .param("postcode", "W1U 8ED")  // valid postcode
                        .param("country", "UK"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("street"))
                .andExpect(model().attribute("street", "123 Baker Street"))
                .andExpect(model().attributeExists("city"))
                .andExpect(model().attribute("city", "London"))
                .andExpect(model().attributeExists("county"))
                .andExpect(model().attribute("county", "Greater London"))
                .andExpect(model().attributeExists("postcode"))
                .andExpect(model().attribute("postcode", "W1U 8ED"))
                .andExpect(model().attributeExists("country"))
                .andExpect(model().attribute("country", "UK"))
                .andExpect(view().name("home"));
    }

    // FT-08: Test postcode validation in UK format
    @Test
    public void testPostcodeFormatValidation() throws Exception {
        // valid UK postcode
        mockMvc.perform(post("/scan-address")
                        .param("street", "123 Baker Street")
                        .param("city", "London")
                        .param("county", "Greater London")
                        .param("postcode", "W1U 8ED")  // Valid UK postcode
                        .param("country", "UK"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "The address is valid!"))
                .andExpect(view().name("home"));

        // invalid UK postcode
        mockMvc.perform(post("/scan-address")
                        .param("street", "123 Baker Street")
                        .param("city", "London")
                        .param("county", "Greater London")
                        .param("postcode", "InvalidPostcode")  // Invalid postcode
                        .param("country", "UK"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "The address is invalid. Please check the details."))
                .andExpect(view().name("home"));
    }

    // FT-09: Test for full address validation
    @Test
    public void testFullAddressValidation() throws Exception {
        when(addressScanner.scan()).thenReturn("Valid address");
        when(addressScanner.validateAddress()).thenReturn(true);  // Mock valid address response

        mockMvc.perform(post("/scan-address")
                        .param("street", "123 Baker Street")
                        .param("city", "London")
                        .param("county", "Greater London")
                        .param("postcode", "W1U 8ED")  // Valid postcode
                        .param("country", "UK"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "The address is valid!"))
                .andExpect(view().name("home"));

        when(addressScanner.scan()).thenReturn("Invalid address");
        when(addressScanner.validateAddress()).thenReturn(false);  // Mock invalid address response

        mockMvc.perform(post("/scan-address")
                        .param("street", "123 Baker Street")
                        .param("city", "London")
                        .param("county", "Greater London")
                        .param("postcode", "InvalidPostcode")  // Invalid postcode
                        .param("country", "UK"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "The address is invalid. Please check the details."))
                .andExpect(view().name("home"));
    }
}
