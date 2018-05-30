package com.demo.parking.model;

import java.util.Comparator;

public class ParkingBayComparator implements Comparator<ParkingBay> {

	@Override
	public int compare(ParkingBay pb1, ParkingBay pb2) {
		Long res = pb1.getIndex() - pb2.getIndex();	  
		return res.intValue();
	}

}
	