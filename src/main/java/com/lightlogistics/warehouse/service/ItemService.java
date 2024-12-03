package com.lightlogistics.warehouse.service;

import com.lightlogistics.warehouse.model.item.Item;
import com.lightlogistics.warehouse.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public Optional<Item> getById(Long id) {
        return itemRepository.findById(id);
    }

    public Item save(Item item){
        return itemRepository.save(item);
    }

    public List<Item> getAll() {
        return itemRepository.findAll();
    }


}
