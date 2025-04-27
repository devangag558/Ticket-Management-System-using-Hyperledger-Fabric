package org.example.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.example.entity.vehicle;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
//import jakarta.validation.Constraints.;

public class vehicleDTO {

//    private String vehicleId;
    private String sellerId; // Assuming you want to expose seller's email, not the entire object
    private String source;
    private String destination;
    @Future
    private LocalDate departureDate;
    private LocalTime departureTime;
    private String mode;
    @Min(value = 10, message = "seat capacity must be greater than 0")
    private int seatCapacity;
    @Min(value = 300, message = "Base price must be greater than 300")
    private float basePrice;

    // Constructors
    public vehicleDTO() {}

    public vehicleDTO(String sellerId, String source, String destination,
                      LocalDate departureDate, LocalTime departureTime, String mode,
                      int seatCapacity, float basePrice) {
//        this.vehicleId = vehicleId;
        this.sellerId = sellerId;
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.mode = mode;
        this.seatCapacity = seatCapacity;
        this.basePrice = basePrice;
    }

    // Getters and Setters
//    public String getVehicleId() {
//        return vehicleId;
//    }
//    public void setVehicleId(String vehicleId) {
//        this.vehicleId = vehicleId;
//    }
    public String getSellerId() {
        return sellerId;
    }
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public LocalDate getDepartureDate() {
        return departureDate;
    }
    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }
    public LocalTime getDepartureTime() {
        return departureTime;
    }
    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }
    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }
    public int getSeatCapacity() {
        return seatCapacity;
    }
    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }
    public float getBasePrice() {
        return basePrice;
    }
    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
    }
   
    public vehicle toEntity() {
    	
    	return new vehicle(this.sellerId,
		this.source,
		this.destination,
		this.departureDate,
		this.departureTime,
		this.mode,
		this.seatCapacity,
		this.seatCapacity,
		this.basePrice);
	}
    
}
