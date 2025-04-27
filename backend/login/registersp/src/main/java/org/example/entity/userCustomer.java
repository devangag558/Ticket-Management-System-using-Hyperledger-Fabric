package org.example.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.example.dto.userCustomerDTO;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name="Customer")
public class userCustomer {
	
	@Id
    @Email(message = "Invalid email format")
	@Column(name = "email")
	private String email;
	@NotNull
	@Column(name = "name")
	private String name;
	@Column(name = "phone")
	@NotNull
	@Pattern(regexp = "^(\\d{10}|\\d{12})$", message = "Phone number must be 10 or 12 digits")
	private String mobileNumber;
	@Column(name = "address")
	private String address;
	@Column(name = "city")
	private String city;
	@Column(name = "gender")
	private String gender;
	@Column(name = "age")
	private int age;
	@Transient
	private String password; // Will not be stored in DB
	@Column(name = "hash")
	@NotBlank(message = "Password is required")
	private String hash;
	@Column(name = "balance")
	private float balance;
	@Column(name = "visibility")
	private boolean visibility;
	@Column(name = "caRegistered")
	private boolean caRegistered = false;
	@Column(name = "ledgerRegistered")
	private boolean ledgerRegistered = false;
	
	@ElementCollection
	@CollectionTable(name = "my_tickets", joinColumns = @JoinColumn(name = "email"))
	@Column(name = "tickets")
	private List<String> ticketList = new ArrayList<String>();
	
	@ElementCollection
	@CollectionTable(name = "customer_transactions", joinColumns = @JoinColumn(name = "email"))
	@Column(name = "transactions")
	private List<String> transactionList = new ArrayList<String>();
	
	public List<String> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<String> ticketList) {
		this.ticketList = ticketList;
	}

	public List<String> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<String> transactionList) {
		this.transactionList = transactionList;
	}

	public boolean addTransaction(String arg0) {
		return transactionList.add(arg0);
	}

	public boolean removeTransaction(Object arg0) {
		return transactionList.remove(arg0);
	}

	public int getNumberOfTransaction() {
		return transactionList.size();
	}
	

	public boolean addTicket(String arg0) {
		return ticketList.add(arg0);
	}

	public boolean removeTicket(Object arg0) {
		return ticketList.remove(arg0);
	}

	public int getNumberOfTickets() {
		return ticketList.size();
	}

	public static String hashPassword(String password) {
        return org.springframework.security.crypto.bcrypt.BCrypt.hashpw(password, org.springframework.security.crypto.bcrypt.BCrypt.gensalt(12));
    }
	
	public userCustomerDTO toDTO() {
        userCustomerDTO dto = new userCustomerDTO();
        dto.setEmail(this.getEmail());
        dto.setName(this.getName());
        dto.setMobileNumber(this.getMobileNumber());
        dto.setAddress(this.getAddress());
        dto.setCity(this.getCity());
        dto.setGender(this.getGender());
        dto.setAge(this.getAge());
        // Password is not stored in this, so skip
//        dto.setHash(hashPassword(this.getPassword()));
//        dto.setBalance(this.getBalance());
        dto.setVisibility(this.isVisibility());
        return dto;
    }
	
	
	public boolean isCaRegistered() {
		return caRegistered;
	}
	public void setCaRegistered(boolean caRegistered) {
		this.caRegistered = caRegistered;
	}
	public boolean isLedgerRegistered() {
		return ledgerRegistered;
	}
	public void setLedgerRegistered(boolean ledgerRegistered) {
		this.ledgerRegistered = ledgerRegistered;
	}
	public userCustomer() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address1) {
		this.address = address1;
	}public String getCity() {
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
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
	@Override
	public int hashCode() {
		return Objects.hash(email);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		userCustomer other = (userCustomer) obj;
		return Objects.equals(email, other.email);
	}
	public userCustomer(@Email(message = "Invalid email format") String email, @NotNull String name,
			@NotNull @Pattern(regexp = "^(\\d{10}|\\d{12})$", message = "Phone number must be 10 or 12 digits") String mobileNumber,
			String address, String city, String gender, int age, String password,
			float balance, boolean visibility,
			boolean caRegistered, boolean ledgerRegistered) {
		super();
		this.email = email;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.address = address;
		this.city = city;
		this.gender = gender;
		this.age = age;
		this.password = password;
		this.hash = hashPassword(password);
		this.balance = balance;
		this.visibility = visibility;
		this.caRegistered = caRegistered;
		this.ledgerRegistered = ledgerRegistered;
	}
	@Override
	public String toString() {
		return "userCustomer [email=" + email + ", name=" + name + ", mobileNumber=" + mobileNumber + ", address="
				+ address + ", city=" + city + ", gender=" + gender + ", age=" + age
				+ ", hash=" + hash + ", balance=" + balance + ", visibility=" + visibility + ", caRegistered="
				+ caRegistered + ", ledgerRegistered=" + ledgerRegistered + "]";
	}
	
	
	
	
	
	
	
}