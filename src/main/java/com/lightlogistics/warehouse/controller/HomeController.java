package com.lightlogistics.warehouse.controller;

import com.lightlogistics.warehouse.model.item.Item;
import com.lightlogistics.warehouse.model.item.ItemStockHandler;
import com.lightlogistics.warehouse.service.item.ItemService;
import com.lightlogistics.warehouse.service.item.ItemStockHandlerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {
    private final ItemService itemService;
    private final ItemStockHandlerService itemStockHandlerService;

    public HomeController(ItemService itemService, ItemStockHandlerService itemStockHandlerService) {
        this.itemService = itemService;
        this.itemStockHandlerService = itemStockHandlerService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World";
    }

    // Show homepage and list all items
    @GetMapping("/")
    public String showHomePage(Model model) {
        // Fetch all items, including seed data and newly added items
        List<Item> items = itemService.getAll();
        List<ItemStockHandler> stockHandlers = itemStockHandlerService.getAll();

//        model.addAttribute("items", items); // For dropdown and display
//        model.addAttribute("stockHandlers", stockHandlers); // For stock list display
//        model.addAttribute("itemStockHandler", new ItemStockHandler()); // Form binding

        // Fetch all items and stock handlers from their respective databases
        model.addAttribute("items", itemService.getAll());
        model.addAttribute("stockHandlers", itemStockHandlerService.getAll());

        return "home"; // Return home.html view
    }

    // Handle adding new items via form
    @PostMapping("/items")
    public String addItem(@ModelAttribute Item item, Model model) {
        // Save the new item to the database
        itemService.save(item);

        // Fetch updated list of items after adding the new one
        List<Item> items = itemService.getAll();
        model.addAttribute("items", items); // Add updated list of items to the model

        // Return the homepage with the updated list and the add item form
        return "home"; // Stay on the homepage and show updated list
    }

    @PostMapping("/manage-stock")
    public String manageStock(@ModelAttribute ItemStockHandler itemStockHandler, Model model) {
        // Save the new stock entry
        itemStockHandlerService.save(itemStockHandler);

        // Refresh data for the homepage
        List<Item> items = itemService.getAll();
        List<ItemStockHandler> stockHandlers = itemStockHandlerService.getAll();

        // Update the model with refreshed data
        model.addAttribute("items", items);
        model.addAttribute("stockHandlers", stockHandlers);
        model.addAttribute("itemStockHandler", new ItemStockHandler()); // Clear form after submission

        return "home"; // Stay on the homepage
    }

//    @PostMapping("/manage-stock")
//    public String manageStock(
//            @RequestParam Long itemId,
//            @RequestParam Integer quantity,
//            @RequestParam BigDecimal weight,
//            Model model) {
//        // Find the item by ID
//        Item item = itemService.getById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
//
//        // Create and save a new ItemStockHandler
//        ItemStockHandler stockHandler = new ItemStockHandler();
//        stockHandler.setItem(item);
//        stockHandler.setQuantity(quantity);
//        stockHandler.setWeight(weight);
//        itemStockHandlerService.save(stockHandler);
//
//        // Update the model attributes
//        model.addAttribute("items", itemService.getAll());
//        model.addAttribute("stockHandlers", itemStockHandlerService.getAll());
//
//        return "home";
//    }
}
