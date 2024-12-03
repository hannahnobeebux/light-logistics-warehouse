package com.lightlogistics.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lightlogistics.warehouse.model.item.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
