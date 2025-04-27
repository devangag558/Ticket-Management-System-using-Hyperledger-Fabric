package org.example.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.example.dto.userSellerDTO;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonProperty;

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
@Table(name="ServiceProvider")
public class userSeller {
	
	@Id
    @Column(name = "email")	
	@NotNull
	@Email(message = "invalid email format")
	private String email;
	@Column(name = "CompanyName")
	@NotNull
	@JsonProperty("name")
	private String Name;
	@Column(name = "phone")
	@NotNull
	@Pattern(regexp = "^(\\d{10}|\\d{12})$", message = "Phone number must be 10 or 12 digits")
	private String mobileNumber;
	@NotNull
	@Pattern(regexp = "^(0[1-9]|1[0-9]|2[0-9]|3[0-5])[A-Z]{5}[0-9]{4}[A-Z]{1}[0-9A-Z]{1}[Z]{1}[0-9A-Z]{1}$", message = "Invalid GST number format")@Column(name = "GST")
	private String gstNumber;
	@Column(name = "address")
	private String address;
	@Column(name = "city")
	private String city;
	@Transient
	private String password; // Will not be stored in DB
	@Column(name = "hash")
	@NotBlank(message = "Password is required")
	private String hashPassword;
	@Column(name = "balance")
	private float balance = 0;
	@Column(name = "ratingCount")
	private int ratingCount = 0;
	@Column(name = "rating")
	@Range(min=0, max=5)
	private float rating = 0;
	@Column(name = "caRegistered")
	private boolean caRegistered = false;
	@Column(name = "ledgerRegistered")
	private boolean ledgerRegistered = false;
	
	@ElementCollection
	@CollectionTable(name = "my_vehicles", joinColumns = @JoinColumn(name = "email"))
	@Column(name = "vehicles")
	private List<String> vehicleList = new ArrayList<String>();
	
	@ElementCollection
	@CollectionTable(name = "seller_transactions", joinColumns = @JoinColumn(name = "email"))
	@Column(name = "transactions")
	private List<String> transactionList = new ArrayList<String>();
	
	
	public boolean addVehicle(String arg0) {
		return vehicleList.add(arg0);
	}

	public boolean removeVehicle(Object arg0) {
		return vehicleList.remove(arg0);
	}

	public int getNumberOfVehicle() {
		return vehicleList.size();
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
	
	public userSellerDTO toDto() {
        userSellerDTO dto = new userSellerDTO();
        dto.setEmail(this.getEmail());
        dto.setName(this.getName());
        dto.setMobileNumber(this.getMobileNumber());
        dto.setGstNumber(this.getGstNumber());
        dto.setAddress(this.getAddress());
        dto.setCity(this.getCity());
        dto.setPassword(this.getPassword());
//        //Note: password is not mapped from entity
//        dto.setHashPassword(this.getHashPassword());
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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
	}public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHashPassword() {
		return hashPassword;
	}
	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public int getRatingCount() {
		return ratingCount;
	}
	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float sum) {
		this.rating = sum;
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
		userSeller other = (userSeller) obj;
		return Objects.equals(email, other.email);
	}
	@Override
	public String toString() {
		return "userSeller [email=" + email + ", Name=" + Name + ", mobileNumber=" + mobileNumber + ", gstNumber="
				+ gstNumber + ", address1=" + address  + ", city=" + city + ", password="
				+ password + ", hashPassword=" + hashPassword + ", balance=" + balance + ", ratingCount=" + ratingCount
				+ ", rating=" + rating + ", caRegistered=" + caRegistered + ", ledgerRegistered=" + ledgerRegistered
				+ "]";
	}
	public userSeller(@NotNull @Email(message = "invalid email format") String email, @NotNull String name,
			@NotNull @Pattern(regexp = "^(\\d{10}|\\d{12})$", message = "Phone number must be 10 or 12 digits") String mobileNumber,
			@NotNull @Pattern(regexp = "^(0[1-9]|1[0-9]|2[0-9]|3[0-5])[A-Z]{5}[0-9]{4}[A-Z]{1}[0-9A-Z]{1}[Z]{1}[0-9A-Z]{1}$", message = "Invalid GST number format") String gstNumber,
			String address, String city, @NotBlank(message = "Password is required") String password,
			String hashPassword, float balance, int ratingCount, @Range(min = 0, max = 5) float rating,
			boolean caRegistered, boolean ledgerRegistered) {
		super();
		this.email = email;
		Name = name;
		this.mobileNumber = mobileNumber;
		this.gstNumber = gstNumber;
		this.address = address;
		this.city = city;
		this.password = password;
		this.hashPassword = hashPassword;
		this.balance = balance;
		this.ratingCount = ratingCount;
		this.rating = rating;
		this.caRegistered = caRegistered;
		this.ledgerRegistered = ledgerRegistered;
	}

	public userSeller() {
	}
	
	
	
	
}