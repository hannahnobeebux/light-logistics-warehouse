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

        //ITEMS
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
        stockItem1.setName("Laptop");
        stockItem1.setCategory("Electronics");
        stockItem1.setExpiryDate(LocalDate.of(2025, 12, 3));
        stockItem1.setQuantity(10);  // Ensure quantity is set
        stockItem1.setWeight(new BigDecimal("1.5"));  // Set weight using BigDecimal
        itemStockHandlerRepository.save(stockItem1);

        ItemStockHandler stockItem2 = new ItemStockHandler();
        stockItem2.setName("Yogurt");
        stockItem2.setCategory("Food");
        stockItem2.setExpiryDate(LocalDate.of(2025, 12, 3).plusDays(30));
        stockItem2.setQuantity(50);
        stockItem2.setWeight(new BigDecimal("0.5"));
        itemStockHandlerRepository.save(stockItem2);

        ItemStockHandler stockItem3 = new ItemStockHandler();
        stockItem3.setName("Jane Eyre Novel");
        stockItem3.setCategory("Books");
//        stockItem3.setExpiryDate(LocalDate.of(2025, 12, 3).plusDays(30));
        stockItem3.setQuantity(3);
        stockItem3.setWeight(new BigDecimal("0.3"));
        itemStockHandlerRepository.save(stockItem3);



            System.out.println("Database populated");
    }
}
