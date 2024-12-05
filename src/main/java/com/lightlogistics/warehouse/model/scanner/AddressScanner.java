package com.lightlogistics.warehouse.model.scanner;

import java.util.regex.Pattern;

public class AddressScanner extends Scanner {

    private String street;
    private String city;
    private String county;
    private String postcode;
    private String country;

    private static final Pattern UK_POSTCODE_PATTERN =
            Pattern.compile("^[A-Z]{1,2}\\d[A-Z\\d]? ?\\d[A-Z]{2}$", Pattern.CASE_INSENSITIVE);

    public AddressScanner(String scannerName) {
        super(scannerName);
    }

    // Getters and setters for address fields
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    // Validation method
    public boolean validateAddress() {
        if (street == null || street.isBlank()) return false;
        if (city == null || city.isBlank()) return false;
        if (postcode == null || !UK_POSTCODE_PATTERN.matcher(postcode).matches()) return false;
        if (country == null || country.isBlank()) return false;

        // Optionally, validate county if provided
        if (county != null && county.isBlank()) return false;

        return true;
    }

    @Override
    public String scan() {
        if (validateAddress()) {
            return "Scanned Address: " + street + ", " + city + ", " + postcode + ", " + country;
        } else {
            return "Invalid address. Please check the details and try again.";
        }
    }
}

