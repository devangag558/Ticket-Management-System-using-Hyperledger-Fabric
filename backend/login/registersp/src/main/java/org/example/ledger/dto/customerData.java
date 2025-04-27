package org.example.ledger.dto;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class customerData {
	
	private String role;
	private String email;
	private String name;
	private String address;
	private String city;
	private String gender;
	private int age;
	private List<transactionJson> transactions;
	private List<String> bookings;
	private String mobileNumber;
	private float balance;
	private boolean visibility;
	private String hash;
	

	 public customerData(String json) throws Exception {
	        ObjectMapper mapper = new ObjectMapper();
	        // Map JSON to a temporary object
	        customerData temp = mapper.readValue(json, customerData.class);

	        // Copy fields from temp to this
	        this.role = temp.role;
	        this.email = temp.email;
	        this.name = temp.name;
	        this.address = temp.address;
	        this.city = temp.city;
	        this.gender = temp.gender;
	        this.age = temp.age;
	        this.transactions = temp.transactions;
	        this.bookings = temp.bookings;
	    }
	
	
	public customerData() {
		super();
	}
	public customerData(String role, String email, String name, String address, String city, String gender, int age,
			List<transactionJson> transactions, List<String> bookings, String mobileNumber, float balance,
			boolean visibility) {
		super();
		this.role = role;
		this.email = email;
		this.name = name;
		this.address = address;
		this.city = city;
		this.gender = gender;
		this.age = age;
		this.transactions = transactions;
		this.bookings = bookings;
		this.mobileNumber = mobileNumber;
		this.balance = balance;
		this.visibility = visibility;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public List<transactionJson> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<transactionJson> transactions) {
		this.transactions = transactions;
	}
	public List<String> getBookings() {
		return bookings;
	}
	public void setBookings(List<String> bookings) {
		this.bookings = bookings;
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
	public boolean isVisibility() {
		return visibility;
	}
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	
	
	
	
	

}
