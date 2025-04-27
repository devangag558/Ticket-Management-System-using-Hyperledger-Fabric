package org.example.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "ticket")
public class ticket {
	
	@Id
	@Column(name = "ticketId")
	private String ticketId= "ticket-" + java.util.UUID.randomUUID().toString();
	@JsonProperty("vehicle")
	@Column(name = "vehicleId")
	private String vehicleId;
	@Column(name = "customerID")
	private String customerId;
	@Column(name = "seatCount")
	private int seatCount;
	@Column(name = "bookingTime")
	private LocalDateTime bookingTime;
	@Column(name = "pricePaid")
	private float pricePaid;
	@Column(name = "txnId")
	private String txnId;
	@Column(name = "basePrice")
	private float basePrice;
	@Column(name = "bookingBlock")
	private int bookingBlock;
	@Column(name = "isCancelled")
	private boolean isCancelled;
	@Column(name = "isConfirmed")
	private boolean isConfirmed;
	
	
	
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public boolean isCancelled() {
		return isCancelled;
	}
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	public int getBookingBlock() {
		return bookingBlock;
	}
	public void setBookingBlock(int bookingBlock) {
		this.bookingBlock = bookingBlock;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public LocalDateTime getBookingTime() {
		return bookingTime;
	}
	public void setBookingTime(LocalDateTime bookingTime) {
		this.bookingTime = bookingTime;
	}
	public int getSeatCount() {
		return seatCount;
	}
	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}
	public float getPricePaid() {
		return pricePaid;
	}
	public void setPricePaid(float pricePaid) {
		this.pricePaid = pricePaid;
	}
	public float getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(float basePrice) {
		this.basePrice = basePrice;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	public boolean isConfirmed() {
		return isConfirmed;
	}
	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	public ticket(String vehicleId, String customerId, int seatCount) {
		super();
		this.vehicleId = vehicleId;
		this.customerId = customerId;
		this.seatCount = seatCount;
	}
	
	
	public ticket(String vehicleId, String customerId, int seatCount, float pricePaid, String txnId, float basePrice) {
		super();
		this.vehicleId = vehicleId;
		this.customerId = customerId;
		this.seatCount = seatCount;
		this.pricePaid = pricePaid;
		this.txnId = txnId;
		this.basePrice = basePrice;
	}
	@Override
	public String toString() {
		return "ticket [ticketId=" + ticketId + ", vehicleId=" + vehicleId + ", customerId=" + customerId
				+ ", seatCount=" + seatCount + ", bookingTime=" + bookingTime + ", pricePaid=" + pricePaid + ", txnId="
				+ txnId + ", basePrice=" + basePrice + ", bookingBlock=" + bookingBlock + ", isCancelled=" + isCancelled
				+ ", isConfirmed=" + isConfirmed + "]";
	}
	public ticket() {
		super();
	}
	
	
	
	

}
