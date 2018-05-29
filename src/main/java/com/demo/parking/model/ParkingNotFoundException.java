package com.demo.parking.model;

public class ParkingNotFoundException extends RuntimeException {

	public ParkingNotFoundException(String exception) {
		super(exception);
	}

}
