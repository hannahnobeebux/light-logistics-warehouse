package com.lightlogistics.warehouse.service.item;

import com.lightlogistics.warehouse.repository.item.ItemStockHandlerRepository;
import com.lightlogistics.warehouse.model.item.ItemStockHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemStockHandlerService {

    private final ItemStockHandlerRepository itemStockHandlerRepository;

    public ItemStockHandlerService(ItemStockHandlerRepository itemStockHandlerRepository) {
        this.itemStockHandlerRepository = itemStockHandlerRepository;
    }

    public void save(ItemStockHandler itemStockHandler) {
        itemStockHandlerRepository.save(itemStockHandler);
    }

    public List<ItemStockHandler> getAll() {
        return itemStockHandlerRepository.findAll();
    }
}
