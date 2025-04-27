package org.example.ledger.dto;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class sellerData {

	private String role;
    private String email;
    private String name;
    private String gstNumber;
    private String address;
    private String city;
    private List<vehicleJson> vehicles;
    private List<transactionJson> transactions;
    private String mobileNumber;
    private float balance;
    private String hashPassword;
    private float rating;
    private int ratingCount;
    
    
    public sellerData() {
		super();
	}


	public sellerData(String json) throws Exception {
		System.out.println("inside the constructor");
        ObjectMapper mapper = new ObjectMapper();
        // Map JSON to a temporary object
        sellerData temp = mapper.readValue(json, sellerData.class);
        
        System.out.println("printing temp "+temp);
        
        // Copy fields from temp to this
        this.role = temp.role;
        this.email = temp.email;
        this.name = temp.name;
        this.gstNumber = temp.gstNumber;
        this.address = temp.address;
        this.city = temp.city;
        this.vehicles = temp.vehicles;
        this.transactions = temp.transactions;
    }
    
    
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGstNumber() {
		return gstNumber;
	}
	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<vehicleJson> getVehicles() {
		return vehicles;
	}
	public void setVehicles(List<vehicleJson> vehicles) {
		this.vehicles = vehicles;
	}
	public List<transactionJson> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<transactionJson> transactions) {
		this.transactions = transactions;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public String getHashPassword() {
		return hashPassword;
	}
	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public int getRatingCount() {
		return ratingCount;
	}
	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}


	public sellerData(String role, String email, String name, String gstNumber, String address, String city,
			List<vehicleJson> vehicles, List<transactionJson> transactions, String mobileNumber, float balance,
			String hashPassword, float rating, int ratingCount) {
		super();
		this.role = role;
		this.email = email;
		this.name = name;
		this.gstNumber = gstNumber;
		this.address = address;
		this.city = city;
		this.vehicles = vehicles;
		this.transactions = transactions;
		this.mobileNumber = mobileNumber;
		this.balance = balance;
		this.hashPassword = hashPassword;
		this.rating = rating;
		this.ratingCount = ratingCount;
	}
    
    
    
    
    
}
