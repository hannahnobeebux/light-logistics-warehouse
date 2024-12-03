package com.lightlogistics.warehouse.model.item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


//Add all attributes for creating an item model
//This needs to be accessible when doing a POST
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column
    private LocalDate expiryDate;

//    public Long getId() {
//        return id;
//    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }


//    SETTER
//    public void setId(Long id) {
//        this.id = id;
//    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    //METHODS






}
