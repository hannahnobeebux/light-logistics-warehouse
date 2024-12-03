package com.lightlogistics.warehouse.model.scanner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Scanner {

    private String scannerName;

    // Constructor
    public Scanner(String scannerName) {
        this.scannerName = scannerName;
    }

    //GETTERS AND SETTERS

    //METHOD
    public void scan() {
        System.out.println("Scanning with " + scannerName);
    }

}
