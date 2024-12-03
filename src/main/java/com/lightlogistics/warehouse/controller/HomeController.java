package com.lightlogistics.warehouse.controller;

import com.lightlogistics.warehouse.model.item.Item;
import com.lightlogistics.warehouse.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class HomeController {

    public final ItemService itemService;

    public HomeController(ItemService itemService) {
        this.itemService = itemService;
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
        model.addAttribute("items", items); // Add all items to the model
        model.addAttribute("item", new Item()); // Add an empty Item object for the form
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
}
