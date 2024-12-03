package com.lightlogistics.warehouse.controller;

import com.lightlogistics.warehouse.model.item.Item;
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
import com.lightlogistics.warehouse.service.item.ItemService;

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
        //Pulls out the path of the post with this Id
        //find the post by id
        Optional<Item> optionalItem = itemService.getById(id);
        //if the post exists, populate the UI model
        if (optionalItem.isPresent()) {
//            Item item = optionalItem.get();
//            model.addAttribute("item", item);
//            return "item";
            return ResponseEntity.ok(optionalItem.get());
        } else {
            //error page
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //FOR THYMELEAF

    // Get all items as an API endpoint
    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAll(); // Fetch all items from the database
    }

    // Add a new item via the API (if desired)
    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        Item savedItem = itemService.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem); // Return saved item as response
    }


}
