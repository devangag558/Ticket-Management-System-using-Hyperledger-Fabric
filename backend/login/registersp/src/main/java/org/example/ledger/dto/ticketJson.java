package org.example.ledger.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ticketJson {
	private String ticketId;
    private String vehicleId;
    private String customerId;
    private int seatCount;
    private String bookingTime;
    private float pricePaid;
    private List<String> txnId;
    private float basePrice;
    private boolean  cancelled;
    private int bookingBlock;
    @Override
	public String toString() {
		return "ticketJson [ticketId=" + ticketId + ", vehicleId=" + vehicleId + ", customerId=" + customerId
				+ ", seatCount=" + seatCount + ", bookingTime=" + bookingTime + ", pricePaid=" + pricePaid + ", txnId="
				+ txnId + ", basePrice=" + basePrice + ", cancelled=" + cancelled + ", bookingBlock=" + bookingBlock
				+ ", isConfirmed=" + isConfirmed + "]";
	}

	private boolean isConfirmed;
    
    
    public ticketJson() {}

    public ticketJson(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ticketJson temp = mapper.readValue(json, ticketJson.class);

        this.ticketId = temp.ticketId;
        this.vehicleId = temp.vehicleId;
        this.customerId = temp.customerId;
        this.seatCount = temp.seatCount;
        this.bookingTime = temp.bookingTime;
        this.pricePaid = temp.pricePaid;
        this.txnId = temp.txnId;
        this.basePrice = temp.basePrice;
        this.cancelled = temp.cancelled;
        this.bookingBlock = temp.bookingBlock;
        this.isConfirmed = temp.isConfirmed;
    }

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public int getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

	public String getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}

	public float getPricePaid() {
		return pricePaid;
	}

	public void setPricePaid(float pricePaid) {
		this.pricePaid = pricePaid;
	}

	public List<String> getTxnId() {
		return txnId;
	}

	public void setTxnId(List<String> txnId) {
		this.txnId = txnId;
	}

	public float getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(float basePrice) {
		this.basePrice = basePrice;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.cancelled = isCancelled;
	}

	public int getBookingBlock() {
		return bookingBlock;
	}

	public void setBookingBlock(int bookingBlock) {
		this.bookingBlock = bookingBlock;
	}

	public boolean getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public ticketJson(String ticketId, String vehicleId, String customerId, int seatCount, String bookingTime,
			float pricePaid, List<String> txnId, float basePrice, boolean isCancelled, int bookingBlock,
			boolean isConfirmed) {
		super();
		this.ticketId = ticketId;
		this.vehicleId = vehicleId;
		this.customerId = customerId;
		this.seatCount = seatCount;
		this.bookingTime = bookingTime;
		this.pricePaid = pricePaid;
		this.txnId = txnId;
		this.basePrice = basePrice;
		this.cancelled = isCancelled;
	}
}
