package com.demo.parking.service;

public class ParkingNotFoundException extends RuntimeException {

	public ParkingNotFoundException(String exception) {
		super(exception);
	}

}
