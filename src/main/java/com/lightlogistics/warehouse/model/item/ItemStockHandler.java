package com.lightlogistics.warehouse.model.item;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Entity
@Setter
public class ItemStockHandler extends Item{

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal weight;

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public void setItem(Item item) {
        this.setName(item.getName());
        this.setCategory(item.getCategory());
    }

}
