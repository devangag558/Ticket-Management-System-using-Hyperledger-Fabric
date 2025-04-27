package org.example.ledger.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

public class vehicleJson {
	
	private String vehicleId;
	private String sellerId;
	private String source;
	private String destination;
	private String departureDate;
	private String departureTime;
	private String mode;
	private int seatCapacity;
	private int availableSeats;
	private float basePrice;
	private float currentPrice;
	private float sellerRating;
	
	 public float getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(float currentPrice) {
		this.currentPrice = currentPrice;
	}

	public float getSellerRating() {
		return sellerRating;
	}

	public void setSellerRating(float sellerRating) {
		this.sellerRating = sellerRating;
	}

	public vehicleJson() {
		super();
	}

	public vehicleJson(String json) throws Exception {
	        ObjectMapper mapper = new ObjectMapper();
	        vehicleJson temp = mapper.readValue(json, vehicleJson.class);

	        this.vehicleId = temp.vehicleId;
	        this.sellerId = temp.sellerId;
	        this.source = temp.source;
	        this.destination = temp.destination;
	        this.departureDate = temp.departureDate;
	        this.departureTime = temp.departureTime;
	        this.mode = temp.mode;
	        this.seatCapacity = temp.seatCapacity;
	        this.availableSeats = temp.availableSeats;
	        this.basePrice = temp.basePrice;
	    }

	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
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
	public String getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
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
	public int getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
	public float getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(float basePrice) {
		this.basePrice = basePrice;
	}
	

}
