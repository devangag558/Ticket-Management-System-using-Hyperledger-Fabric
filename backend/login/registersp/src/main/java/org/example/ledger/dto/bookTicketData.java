package org.example.ledger.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

public class bookTicketData {
	
	private transactionJson txnData;
	private ticketJson ticketData;
	public transactionJson getTxnData() {
		return txnData;
	}
	public void setTxnData(transactionJson txnData) {
		this.txnData = txnData;
	}
	public ticketJson getTicketData() {
		return ticketData;
	}
	public void setTicketData(ticketJson ticketData) {
		this.ticketData = ticketData;
	}
	public bookTicketData(transactionJson txnData, ticketJson ticketData) {
		super();
		this.txnData = txnData;
		this.ticketData = ticketData;
	}
	public bookTicketData() {
		super();
	}
	@Override
	public String toString() {
		return "bookTicketData [txnData=" + txnData + ", ticketData=" + ticketData + "]";
	}
	public bookTicketData(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        bookTicketData temp = mapper.readValue(json, bookTicketData.class);
        this.txnData = temp.txnData;
        this.ticketData = temp.ticketData;
    }
	
}
