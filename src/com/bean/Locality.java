package com.bean;

import java.io.Serializable;

public class Locality implements Serializable {

	String pin;
	String city;
	String area;
	public Locality() {
		
	}
	public Locality(String lpin, String city, String area) {
		super();		
		this.pin = lpin;
		this.city = city;
		this.area = area;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	@Override
	public String toString() {
		return "Locality [pin=" + pin + ", city=" + city + ", area=" + area + "]";
	}
	

}
