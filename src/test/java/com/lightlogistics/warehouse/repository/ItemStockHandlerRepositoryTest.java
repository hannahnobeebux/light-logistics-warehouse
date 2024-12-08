//package com.lightlogistics.warehouse.repository;
//
//import com.lightlogistics.warehouse.model.item.Item;
//import com.lightlogistics.warehouse.model.item.ItemStockHandler;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.lang.reflect.Field;
//import java.math.BigDecimal;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class ItemStockHandlerRepositoryTest {
//
//    @Autowired
//    private ItemStockHandlerRepository itemStockHandlerRepository;
//
//    @Autowired
//    private ItemRepository itemRepository;
//
//    @Test
//    void testFindByItem() throws NoSuchFieldException, IllegalAccessException {
//        // Create an item
//        Item item = new Item();
//        item.setName("Laptop");
//        itemRepository.save(item);
//
//        // Create and save ItemStockHandler
//        ItemStockHandler stockHandler = new ItemStockHandler();
//        stockHandler.setItem(item);  // Associating Item
//        stockHandler.setQuantity(10);
//        stockHandler.setWeight(new BigDecimal("1.5"));
//        itemStockHandlerRepository.save(stockHandler);
//
//        // Retrieve ItemStockHandler by Item
//        ItemStockHandler retrievedStock = itemStockHandlerRepository.findByItem(item).orElseThrow();
//
//        // Access 'item' field through reflection
//        Field itemField = ItemStockHandler.class.getDeclaredField("item");
//        itemField.setAccessible(true);  // Allow access to private field
//        Item retrievedItem = (Item) itemField.get(retrievedStock);
//
//        // Assert the equality of the item names
//        assertEquals(item.getName(), retrievedItem.getName());
//    }
//}

