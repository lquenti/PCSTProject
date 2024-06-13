/**
 * Outgoing Customer Record.
 * 
 *
 */

package com.softwaretesting.testing.dto.outbound;


import com.softwaretesting.testing.model.Customer;

public class CustomerOutDTO {

	private Long id;
	private String userName;
	private String name;
	private String phoneNumber;
	
	public CustomerOutDTO(Long id, String userName, String name, String phoneNumber) {
		this.id = id;
		this.userName = userName;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	
	public CustomerOutDTO(Customer entity) {
		this.id = entity.getId();
		this.userName = entity.getUserName();
		this.name = entity.getName();
		this.phoneNumber = entity.getPhoneNumber();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {return userName;}

	public void setUserName(String userName) {this.userName = userName;}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
