package com.lightlogistics.warehouse.controller;

import com.lightlogistics.warehouse.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/") // Maps the root URL (homepage)
    public String showHomePage(Model model) {
        // Optionally, add any items or data to the model
        return "home";  // Correct view name, Thymeleaf will automatically look for home.html
    }
}
