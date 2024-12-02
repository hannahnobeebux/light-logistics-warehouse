package com.lightlogistics.warehouse.controller;

import com.lightlogistics.warehouse.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lightlogistics.warehouse.service.ItemService;
import java.util.Optional;

//functionality for adding / removing items
@RestController
@RequestMapping("/api")
public class ItemController {

    @Autowired
    private ItemService itemService;

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


}
