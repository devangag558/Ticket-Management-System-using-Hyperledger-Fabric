package org.example.dto;

import java.util.Objects;

import org.example.entity.userCustomer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public class customerUpdateDTO {
	
	@Email
	private String email;
	private String name;
	@Pattern(regexp = "^(\\d{10}|\\d{12})$", message = "Phone number must be 10 or 12 digits")
	private String mobileNumber;
	private String address;
	private String city;
	private String gender;
	private int age;
	private String password; // Will not be stored in DB
	private boolean visibility;
	private String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	public userCustomer toEntity() {
        userCustomer entity = new userCustomer();
        entity.setEmail(this.getEmail());
        entity.setName(this.getName());
        entity.setMobileNumber(this.getMobileNumber());
        entity.setAddress(this.getAddress());
        entity.setCity(this.getCity());
        entity.setGender(this.getGender());
        entity.setAge(this.getAge());
        entity.setPassword(this.getPassword());
        entity.setBalance(0);
        entity.setVisibility(this.isVisibility());
        return entity;
    }
	
	public customerUpdateDTO() {
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
		customerUpdateDTO other = (customerUpdateDTO) obj;
		return Objects.equals(email, other.email);
	}
	
	public customerUpdateDTO(@Email(message = "Invalid email format") String email, @NotNull String name,
			@NotNull @Pattern(regexp = "^(\\d{10}|\\d{12})$", message = "Phone number must be 10 or 12 digits") String mobileNumber,
			String address, String city, String gender, int age, String password,  boolean visibility){
		super();
		this.email = email;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.address = address;
		this.city = city;
		this.gender = gender;
		this.age = age;
		this.password = password;
		this.visibility = visibility;
	}
	@Override
	public String toString() {
		return "userCustomer [email=" + email + ", name=" + name + ", mobileNumber=" + mobileNumber + ", address="
				+ address + ", city=" + city + ", gender=" + gender + ", age=" + age
				+ ", visibility=" + visibility + "]";
	}
	
	
	
	
	
	
	
}