package com.lightlogistics.warehouse.repository;

import com.lightlogistics.warehouse.model.item.ItemStockHandler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemStockHandlerRepository extends JpaRepository<ItemStockHandler, Long> {

}
