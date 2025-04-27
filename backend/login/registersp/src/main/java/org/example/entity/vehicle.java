package org.example.entity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;

@Entity
@Table(name="vehicle")
public class vehicle {
	
	@Id
	@Column(name = "vehicleId")
	private String vehicleId = "vehicle-"+java.util.UUID.randomUUID().toString();
	@Column(name = "sellerId")
	private String sellerId;
	@Column(name = "source")
	private String source;
	@Column(name = "destination")
	private String destination;
	@Column(name = "departureDate")
	@Future
    private LocalDate departureDate;
	@Column(name = "departureTime")
	private LocalTime departureTime;
	@Column(name = "mode")
	private String mode;
	@Column(name = "seatCapacity")
	@Min(value = 10, message = "seat capacity must be greater than 0")
    private int seatCapacity;
	@Column(name = "availableSeats")
	private int availableSeats;
	@Column(name = "basePrice")
	@Min(value = 300, message = "Base price must be greater than 300")
    private float basePrice;
	
	@ElementCollection
	@CollectionTable(name = "booked_tickets", joinColumns = @JoinColumn(name = "vehicleId"))
	@Column(name = "tickets")
	private List<String> ticketList = new ArrayList<String>();
	

	public vehicle(String sellerId, String source, String destination, @Future LocalDate departureDate,
			LocalTime departureTime, String mode,
			@Min(value = 10, message = "seat capacity must be greater than 0") int seatCapacity, int availableSeats,
			@Min(value = 300, message = "Base price must be greater than 300") float basePrice) {
		super();
		this.sellerId = sellerId;
		this.source = source;
		this.destination = destination;
		this.departureDate = departureDate;
		this.departureTime = departureTime;
		this.mode = mode;
		this.seatCapacity = seatCapacity;
		this.availableSeats = availableSeats;
		this.basePrice = basePrice;
	}

	public boolean add(String arg0) {
		return ticketList.add(arg0);
	}

	public boolean remove(Object arg0) {
		return ticketList.remove(arg0);
	}

	public int size() {
		return ticketList.size();
	}
	
	public int getDayOfMonth() {
		return departureDate.getDayOfMonth();
	}

	public DayOfWeek getDayOfWeek() {
		return departureDate.getDayOfWeek();
	}
	
	public int getDayOfYear() {
		return departureDate.getDayOfYear();
	}

	private LocalDate parseLocalDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return null; // Or throw an exception if you prefer
        }
    }

    private LocalTime parseLocalTime(String timeString) {
        try {
            return LocalTime.parse(timeString, DateTimeFormatter.ISO_LOCAL_TIME);
        } catch (DateTimeParseException e) {
            return null; // Or throw an exception if you prefer
        }
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
	public LocalDate getDepartureDate() {
        return departureDate;
    }
    public void setDepartureDate(String departureDate) {
        this.departureDate = parseLocalDate(departureDate);
    }
    public LocalTime getDepartureTime() {
        return departureTime;
    }
    public void setDepartureTime(String departureTime) {
        this.departureTime = parseLocalTime(departureTime);
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
	public vehicle(String vehicleId, String sellerId, String source, String destination, String departureDate,
			String departureTime, String mode, int seatCapacity, int availableSeats, float basePrice) {
		super();
		this.vehicleId = vehicleId;
		this.sellerId = sellerId;
		this.source = source;
		this.destination = destination;
		this.departureDate = parseLocalDate(departureDate);
		this.departureTime = parseLocalTime(departureTime);
		this.mode = mode;
		this.seatCapacity = seatCapacity;
		this.availableSeats = availableSeats;
		this.basePrice = basePrice;
	}
	public vehicle(String vehicleId, String source, String destination, String departureDate,
			String departureTime, String mode, int seatCapacity, int availableSeats, float basePrice) {
		super();
		this.vehicleId = vehicleId;
//		this.seller = seller;
		this.source = source;
		this.destination = destination;
		this.departureDate = parseLocalDate(departureDate);
		this.departureTime = parseLocalTime(departureTime);
		this.mode = mode;
		this.seatCapacity = seatCapacity;
		this.availableSeats = availableSeats;
		this.basePrice = basePrice;
	}
	

	public vehicle() {
	}
	@Override
	public String toString() {
		return "vehicle [vehicleId=" + vehicleId + ", sellerId=" + sellerId + ", source=" + source + ", destination="
				+ destination + ", departureDate=" + departureDate + ", departureTime=" + departureTime
				+ ", mode=" + mode  + ", seatCapacity=" + seatCapacity
				+ ", availableSeats=" + availableSeats + ", basePrice=" + basePrice + "]";
	}
	

}
