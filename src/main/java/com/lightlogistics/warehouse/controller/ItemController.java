package com.lightlogistics.warehouse.controller;

import com.lightlogistics.warehouse.model.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lightlogistics.warehouse.service.ItemService;

import java.util.List;
import java.util.Optional;

//functionality for adding / removing items
@RestController
@RequestMapping("/api/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    //FOR POSTMAN
    @PostMapping("/item/new")
    public ResponseEntity saveNewItem(@ModelAttribute Item item) {
        itemService.save(item);
//        return "redirect:/items/" + item.getId();
         return ResponseEntity.ok().build();
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<Item> getItem(@PathVariable Long id, Model model) {
        Optional<Item> optionalItem = itemService.getById(id);
        //if the post exists, populate the UI model
        if (optionalItem.isPresent()) {
//            Item item = optionalItem.get();
//            model.addAttribute("item", item);
//            return "item";
            return ResponseEntity.ok(optionalItem.get());
        } else {
            //error handling
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //FOR THYMELEAF
    // get all items as an API endpoint
    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAll();
    }

    // option to add an item via this API rather than in HomeController
    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        Item savedItem = itemService.save(item);
        // return saved item as response
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }
}
