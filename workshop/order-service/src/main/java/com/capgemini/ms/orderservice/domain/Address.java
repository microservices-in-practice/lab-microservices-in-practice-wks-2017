package com.capgemini.ms.orderservice.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;
import javax.persistence.Column;

@Embeddable
public class Address implements Serializable {

	@Column
	private String city;

	@Column
	private String zip;

	@Column
	private String street;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (city != null && !city.trim().isEmpty())
			result += "city: " + city;
		if (zip != null && !zip.trim().isEmpty())
			result += ", zip: " + zip;
		if (street != null && !street.trim().isEmpty())
			result += ", street: " + street;
		return result;
	}
}