package com.lightlogistics.warehouse.model.scanner;

import jakarta.persistence.Entity;


public class AddressScanner extends Scanner {
    // Constructor
    public AddressScanner(String scannerName) {
        super(scannerName);
    }

    // Override the scan method for address scanning
    @Override
    public void scan() {
        System.out.println("Address scanning in progress...");
        // You can add more logic here to simulate actual address scanning
    }

    // Method to validate an address (this can be a simple example for now)
    public boolean validateAddress(String address) {
        // Simple validation to check if address is not empty (you can improve it)
        return address != null && !address.isEmpty();
    }


}
