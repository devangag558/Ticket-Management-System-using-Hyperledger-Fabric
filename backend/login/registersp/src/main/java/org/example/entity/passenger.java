//package org.example.entity;
//
//import java.util.Objects;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Pattern;
//
//
//@Entity
//@Table(name="passenger")
//public class passenger {
//	
//	@Id
//	@Column(name = "ticketid")
//	@NotNull
//	private String ticketid;
//	@NotNull
//	@Column(name = "name")
//	private String name;
//	@Column(name = "phone")
//	@NotNull
//	@Pattern(regexp = "^(\\d{10}|\\d{12})$", message = "Phone number must be 10 or 12 digits")
//	private String mobileNumber;
//	@Column(name = "gender")
//	private String gender;
//	@Column(name = "age")
//	private int age;
//	public String getTicketid() {
//		return ticketid;
//	}
//	public void setTicketid(String ticketid) {
//		this.ticketid = ticketid;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getMobileNumber() {
//		return mobileNumber;
//	}
//	public void setMobileNumber(String mobileNumber) {
//		this.mobileNumber = mobileNumber;
//	}
//	public String getGender() {
//		return gender;
//	}
//	public void setGender(String gender) {
//		this.gender = gender;
//	}
//	public int getAge() {
//		return age;
//	}
//	public void setAge(int age) {
//		this.age = age;
//	}
//	@Override
//	public int hashCode() {
//		return Objects.hash(ticketid);
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		passenger other = (passenger) obj;
//		return Objects.equals(ticketid, other.ticketid);
//	}
//	/**
//	 * @param ticketid
//	 * @param name
//	 * @param mobileNumber
//	 * @param gender
//	 * @param age
//	 */
//	public passenger(@NotNull String ticketid, @NotNull String name,
//			@NotNull @Pattern(regexp = "^(\\d{10}|\\d{12})$", message = "Phone number must be 10 or 12 digits") String mobileNumber,
//			String gender, int age) {
//		super();
//		this.ticketid = ticketid;
//		this.name = name;
//		this.mobileNumber = mobileNumber;
//		this.gender = gender;
//		this.age = age;
//	}	
//}