package com.lightlogistics.warehouse.integrationTests;

import com.lightlogistics.warehouse.WarehouseApplication;
import com.lightlogistics.warehouse.model.item.Item;
import com.lightlogistics.warehouse.model.item.ItemStockHandler;
import com.lightlogistics.warehouse.repository.ItemStockHandlerRepository;
import com.lightlogistics.warehouse.service.ItemService;
import com.lightlogistics.warehouse.service.ItemStockHandlerService;
import com.lightlogistics.warehouse.repository.ItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest(classes = WarehouseApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ApplicationIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemStockHandlerService itemStockHandlerService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ItemStockHandlerRepository itemStockHandlerRepository;


    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
        Item item = new Item();
        item.setName("Printer");
        item.setCategory("Electronics");
        itemService.save(item);
    }

    @BeforeEach
    public void cleanPersistenceContext() {
        entityManager.clear();
    }

//IT-01: GET /: Retrieving and displaying homepage
    @Test
    void testShowHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("items", "stockHandlers", "street", "city", "county", "postcode", "country", "message"));
    }

//IT-02: POST /items: for adding a new item
    @Test
    void testAddItem() throws Exception {
        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Printer Ink")
                        .param("category", "Office Supplies"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("items"));
    }

//IT-03: POST /scan-address: checking a valid address
    @Test
    void testScanAddress() throws Exception {
        mockMvc.perform(post("/scan-address")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("street", "221B Baker Street")
                        .param("city", "London")
                        .param("county", "Greater London")
                        .param("postcode", "NW1 6XE")
                        .param("country", "UK"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("message", "The address is valid!"));
    }

//IT-04: POST /scan-address: checking an invalid address and throws an exception
    @Test
    void testScanAddressInvalid() throws Exception {
        mockMvc.perform(post("/scan-address")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("street", "Unknown Street")
                        .param("city", "Unknown")
                        .param("county", "")
                        .param("postcode", "INVALID")
                        .param("country", "Unknown"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("message", "The address is invalid. Please check the details."));
    }

//IT-05: POST /manage-stock: for handling stock related attributes
//    @Test
//    public void testManageStock() throws Exception {
//        // Add a unique item for the test
//        Item uniqueItem = new Item();
//        uniqueItem.setName("TestItem_" + UUID.randomUUID()); // Ensures a unique name
//        uniqueItem.setCategory("Office Supplies");
//        itemService.save(uniqueItem);
//
//        // Detach the saved Item to avoid conflicts
//        entityManager.detach(uniqueItem);
//
//        // Perform the POST request to manage stock
//        mockMvc.perform(post("/manage-stock")
//                        .param("itemName", uniqueItem.getName()) // Use the unique item name
//                        .param("quantity", "25")
//                        .param("weight", "2.0"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("home"))
//                .andExpect(model().attributeExists("stockHandlers"));
//
//        // Verify that the stock handler was created
//        List<ItemStockHandler> stockHandlers = itemStockHandlerService.getAll();
//        assertFalse(stockHandlers.isEmpty());
//        assertEquals(1, stockHandlers.size());
//        assertEquals(uniqueItem.getName(), stockHandlers.get(0).getName());
//        assertEquals(Integer.valueOf(25), stockHandlers.get(0).getQuantity());
//        assertEquals(new BigDecimal("2.0"), stockHandlers.get(0).getWeight());
//    }

}

