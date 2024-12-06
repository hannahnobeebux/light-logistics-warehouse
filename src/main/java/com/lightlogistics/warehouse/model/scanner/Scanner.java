package com.lightlogistics.warehouse.model.scanner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

public class Scanner {

    private String scannerName;

    public Scanner(String scannerName) {
        this.scannerName = scannerName;
    }

    public String getScannerName() {
        return scannerName;
    }

    public void setScannerName(String scannerName) {
        this.scannerName = scannerName;
    }

    public String scan() {
        return "Scanning not implemented";
    }

}
