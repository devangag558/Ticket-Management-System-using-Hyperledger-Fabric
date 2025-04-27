package org.example.ledger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.annotations.JsonAdapter;

public class transactionJson {
	
	@JsonProperty("timestamp")
	private String timestamp;
	private String txnId;
	private String source;
	private String destination;
	private float amount;
	
	
	public transactionJson() {
		super();
	}

	public transactionJson(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        transactionJson temp = mapper.readValue(json, transactionJson.class);

        this.timestamp = temp.timestamp;
        this.txnId = temp.txnId;
        this.source = temp.source;
        this.destination = temp.destination;
        this.amount = temp.amount;
    }
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
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
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public transactionJson(String timestamp, String txnId, String source, String destination, float amount) {
		super();
		this.txnId = txnId;
		this.source = source;
		this.destination = destination;
		this.amount = amount;
	}

}
