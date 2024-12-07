package com.lightlogistics.warehouse.controller;

import com.lightlogistics.warehouse.model.item.Item;
import com.lightlogistics.warehouse.model.item.ItemStockHandler;
import com.lightlogistics.warehouse.model.scanner.AddressScanner;
import com.lightlogistics.warehouse.service.ItemService;
import com.lightlogistics.warehouse.service.ItemStockHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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

        model.addAttribute("street", "");
        model.addAttribute("city", "");
        model.addAttribute("county", "");
        model.addAttribute("postcode", "");
        model.addAttribute("country", "");
        model.addAttribute("message", "");

        return "home";
    }

// used for the form
    @PostMapping("/items")
    public String addItem(@ModelAttribute Item item, Model model) {
        // Save the new item to the database
        itemService.save(item);

        // fetch updated list of items after adding the new one
        List<Item> items = itemService.getAll();
//        adding update items to the screen
        model.addAttribute("items", items);

        return "home";
    }

//    @PostMapping("/manage-stock")
//    public String manageStock(@ModelAttribute ItemStockHandler itemStockHandler, Model model) {
//        itemStockHandlerService.save(itemStockHandler);
//
////        refresh homepage for data
//        List<Item> items = itemService.getAll();
//        List<ItemStockHandler> stockHandlers = itemStockHandlerService.getAll();
//
//        // update the model with refreshed data
//        model.addAttribute("items", items);
//        model.addAttribute("stockHandlers", stockHandlers);
////        clear form after submission
//        model.addAttribute("itemStockHandler", new ItemStockHandler());
//
//        return "home";
//    }

    @PostMapping("/scan-address")
    public String scanAddress(@RequestParam String street,
                              @RequestParam String city,
                              @RequestParam(required = false) String county,
                              @RequestParam String postcode,
                              @RequestParam String country,
                              Model model) {

        AddressScanner addressScanner = new AddressScanner("Address Scanner");

        // set the address fields
        addressScanner.setStreet(street);
        addressScanner.setCity(city);
        addressScanner.setCounty(county);
        addressScanner.setPostcode(postcode);
        addressScanner.setCountry(country);

        // validate address using methods
        String result = addressScanner.scan();
        boolean isValid = addressScanner.validateAddress();
        String message = isValid ? "The address is valid!" : "The address is invalid. Please check the details.";

        // add result and address fields to model
        model.addAttribute("street", street);
        model.addAttribute("city", city);
        model.addAttribute("county", county);
        model.addAttribute("postcode", postcode);
        model.addAttribute("country", country);
        model.addAttribute("message", message);

        return "home";
    }

    @PostMapping("/manage-stock")
    public String manageStock(
        @RequestParam String itemName,
        @RequestParam Integer quantity,
        @RequestParam BigDecimal weight,
        Model model) {
    // Find the item by ID
    Item item = itemService.getByName(itemName).orElseThrow(() -> new RuntimeException("Item not found"));

    // Create and save a new ItemStockHandler
    ItemStockHandler stockHandler = new ItemStockHandler();
    stockHandler.setItem(item);
    stockHandler.setQuantity(quantity);
    stockHandler.setWeight(weight);
    itemStockHandlerService.save(stockHandler);

    // Update the model attributes
    model.addAttribute("items", itemService.getAll());
    model.addAttribute("stockHandlers", itemStockHandlerService.getAll());

    return "home";
    }
}
