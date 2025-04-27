package org.example.dto;

import java.util.Objects;

import org.example.entity.userSeller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class sellerUpdateDTO {
	
	@Email
	private String email;
	private String Name;
	@Pattern(regexp = "^(\\d{10}|\\d{12})$", message = "Phone number must be 10 or 12 digits")
	private String mobileNumber;
	@Pattern(regexp = "^(0[1-9]|1[0-9]|2[0-9]|3[0-5])[A-Z]{5}[0-9]{4}[A-Z]{1}[0-9A-Z]{1}[Z]{1}[0-9A-Z]{1}$", message = "Invalid GST number format")@Column(name = "GST")
	private String gstNumber;
	private String address;
	private String city;
	private String password; // Will not be stored in DB
	private String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public userSeller toEntity() {
        userSeller entity = new userSeller();
        entity.setEmail(this.getEmail());
        entity.setName(this.getName());
        entity.setMobileNumber(this.getMobileNumber());
        entity.setGstNumber(this.getGstNumber());
        entity.setAddress(this.getAddress());
        entity.setCity(this.getCity());
        entity.setPassword(this.getPassword()); // This is marked as transient and will not be persisted.
        return entity;
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
	}
	public String getCity() {
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
		sellerUpdateDTO other = (sellerUpdateDTO) obj;
		return Objects.equals(email, other.email);
	}
	@Override
	public String toString() {
		return "userSeller [email=" + email + ", Name=" + Name + ", mobileNumber=" + mobileNumber + ", gstNumber="
				+ gstNumber + ", address1=" + address + ", city=" + city + ", password="
				+ password + "]";
	}
	public sellerUpdateDTO(String email, String name, String mobileNumber, String gstNumber, String address,
		String city, String password, String hashPassword) {
		super();
		this.email = email;
		Name = name;
		this.mobileNumber = mobileNumber;
		this.gstNumber = gstNumber;
		this.address = address;
		this.city = city;
		this.password = password;
	}
	public sellerUpdateDTO() {
		super();
	}
}