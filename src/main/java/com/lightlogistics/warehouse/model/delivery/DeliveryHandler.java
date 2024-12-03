package com.lightlogistics.warehouse.model.delivery;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class DeliveryHandler {
    @Id
    private Long id;

    @Column (nullable = false)
    private String deliveryStatus;

    @Column (nullable = false)
    private String deliveryContents;

    @Column (nullable = false)
    private String deliveryAddress;

    @Column (nullable = false)
    private LocalDate deliveryTime;

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryContents() {
        return deliveryContents;
    }

    public void setDeliveryContents(String deliveryContents) {
        this.deliveryContents = deliveryContents;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDate getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDate deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    //METHODS
}
