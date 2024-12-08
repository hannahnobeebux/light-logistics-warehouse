package com.lightlogistics.warehouse.config;

import com.lightlogistics.warehouse.model.item.ItemStockHandler;
import com.lightlogistics.warehouse.repository.ItemRepository;
import com.lightlogistics.warehouse.repository.ItemStockHandlerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

//this annotation will tell our application that this file must run and interact with the database once the SpringBoot application is spun up
@Component
public class SeedData implements CommandLineRunner {

    private final ItemRepository itemRepository;
    private final ItemStockHandlerRepository itemStockHandlerRepository;

    public SeedData(ItemRepository itemRepository, ItemStockHandlerRepository itemStockHandlerRepository) {
        this.itemRepository = itemRepository;
        this.itemStockHandlerRepository = itemStockHandlerRepository;
    }

    //We need to override the "run" method that is associated with CommandLine runner
    @Override
    public void run(String... args) throws Exception {

        //OLD ITEMS
//        Item item1 = new Item();
//        item1.setName("Yogurt");
//        item1.setCategory("Food");
//        item1.setExpiryDate(LocalDate.of(2025, 3, 12));
//        itemRepository.save(item1);
//
//        Item item2 = new Item();
//        item2.setName("Laptop");
//        item2.setCategory("Electronics");
//        item2.setExpiryDate(LocalDate.of(2025, 3, 12));
//        itemRepository.save(item2);

        ItemStockHandler stockItem1 = new ItemStockHandler();
        stockItem1.setName("Printing Paper");
        stockItem1.setCategory("Office Supplies");
        stockItem1.setExpiryDate(LocalDate.of(2027, 12, 31));
        stockItem1.setQuantity(5000);  //number of sheets
        stockItem1.setWeight(new BigDecimal("5.0"));
        itemStockHandlerRepository.save(stockItem1);

        ItemStockHandler stockItem2 = new ItemStockHandler();
        stockItem2.setName("Printer Ink");
        stockItem2.setCategory("Office Supplies");
        stockItem2.setExpiryDate(LocalDate.of(2026, 6, 30));
        stockItem2.setQuantity(300);  // number of ink cartridges in stock
        stockItem2.setWeight(new BigDecimal("0.2"));  // weight of one ink cartridge
        itemStockHandlerRepository.save(stockItem2);

        ItemStockHandler stockItem3 = new ItemStockHandler();
        stockItem3.setName("Office Chair");
        stockItem3.setCategory("Furniture");
        stockItem3.setQuantity(100);  // number of office chairs
        stockItem3.setWeight(new BigDecimal("15.0"));  // weight of one office chair in kg
        itemStockHandlerRepository.save(stockItem3);

        ItemStockHandler stockItem4 = new ItemStockHandler();
        stockItem4.setName("Highlighters");
        stockItem4.setCategory("Office Supplies");
        stockItem4.setExpiryDate(LocalDate.of(2025, 12, 31));
        stockItem4.setQuantity(2000);  // number of highlighters in stock
        stockItem4.setWeight(new BigDecimal("0.05"));  // weight of one highlighter
        itemStockHandlerRepository.save(stockItem4);

        ItemStockHandler stockItem5 = new ItemStockHandler();
        stockItem5.setName("Packing Tape");
        stockItem5.setCategory("Packaging Materials");
        stockItem5.setExpiryDate(LocalDate.of(2026, 6, 15));
        stockItem5.setQuantity(1000);  // number of rolls of packing tape
        stockItem5.setWeight(new BigDecimal("0.2"));  // weight of one roll of packing tape
        itemStockHandlerRepository.save(stockItem5);

        // Item 6: Laptops
        ItemStockHandler stockItem6 = new ItemStockHandler();
        stockItem6.setName("Laptop");
        stockItem6.setCategory("Electronics");
        stockItem6.setQuantity(50);
        stockItem6.setWeight(new BigDecimal("2.5"));  // weight of one laptop
        itemStockHandlerRepository.save(stockItem6);

        // Item 7: HDMI Cables
        ItemStockHandler stockItem7 = new ItemStockHandler();
        stockItem7.setName("HDMI Cable");
        stockItem7.setCategory("Electronics");
        stockItem7.setQuantity(500);
        stockItem7.setWeight(new BigDecimal("0.1"));  //weight of one HDMI cable
        itemStockHandlerRepository.save(stockItem7);

        // Item 8: Wireless Mouses
        ItemStockHandler stockItem8 = new ItemStockHandler();
        stockItem8.setName("Wireless Mouse");
        stockItem8.setCategory("Electronics");
        stockItem8.setQuantity(300);
        stockItem8.setWeight(new BigDecimal("0.3"));  // weight of one wireless mouse
        itemStockHandlerRepository.save(stockItem8);

        System.out.println("The database has been populated with items :)");
    }
}
