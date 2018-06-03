package com.demo.parking.utils;

import java.util.Comparator;

import com.demo.parking.model.ParkingBay;

public class ParkingBayComparator implements Comparator<ParkingBay> {

	@Override
	public int compare(ParkingBay pb1, ParkingBay pb2) {
		Long res = pb1.getIndex() - pb2.getIndex();	  
		return res.intValue();
	}

}
	