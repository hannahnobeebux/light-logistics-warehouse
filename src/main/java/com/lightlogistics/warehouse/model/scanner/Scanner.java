package com.lightlogistics.warehouse.model.scanner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Scanner {

    @Id
    private Long id;

    @Column (nullable = false)
    private String scannerName;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    //METHODS
}
