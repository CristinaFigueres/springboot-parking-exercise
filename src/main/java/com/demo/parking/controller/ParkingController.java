package com.demo.parking.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.demo.parking.model.Parking;
import com.demo.parking.model.ParkingBay;
import com.demo.parking.repository.ParkingBayRepository;
import com.demo.parking.repository.ParkingRepository;
import com.demo.parking.utils.ParkingConstants;

@Component
public class ParkingController {
	
	//logger
	private static Logger log = LogManager.getLogger(ParkingController.class);
	
	@Autowired
	private ParkingRepository parkingRepository;
	
	@Autowired
	private ParkingBayRepository parkingBayRepository;
	
	public long availableBays(long idParking) {
		return parkingBayRepository.countAvailableBays(idParking, getAvailableTypes());
	}
	
	public boolean isPresent(long idParking) {
		Optional<Parking> parking = parkingRepository.findById(idParking);
		return parking.isPresent();
	}
	
	public Parking presentParking(long idParking) {
		Optional<Parking> parking = parkingRepository.findById(idParking);
		if (parking.isPresent()) {
			return parking.get();
		}
		log.info("There is no parking with id: " + idParking);
		return null;
	}
	
	public ParkingBay presentBay(long index) {
		Optional<ParkingBay> pb = parkingBayRepository.findById(new Long(index));
		if (pb.isPresent()) {
			return pb.get();
		}
		log.info("There is no parking bay with position: " + index);
		return null;
	}

	
	public long parkCar(char carType, long idParking) {
		//define the order needed to obtain the first available
		Sort sort = new Sort(Sort.Direction.ASC, "minDistance").and(new Sort(Sort.Direction.ASC, "index"));
		
		List<Character> types = null;
		if (carType == ParkingConstants.DISABLED_OCCUPIED) {
			//adding condition Disabled bay
			types = getAvailableDisabledTypes();
		} else {
			types = getAvailableTypes();
		}
		List<ParkingBay> pb = parkingBayRepository.firstAvailable(idParking, types, sort);
		
		if (pb != null && !pb.isEmpty()) {
			//get the first one because the list of results is ordered
			ParkingBay pbAvail = pb.get(0);
			pbAvail.setOccupied(carType);
			parkingBayRepository.save(pbAvail);
			return pbAvail.getIndex();
		} else {
			log.info("There is no parking bay for the type: " + carType);
			return -1;
		}
	}
	
	public boolean unparkCar(ParkingBay bay) {
		if (bay.getOccupied() == ParkingConstants.NON_DISABLED_EMPTY || bay.getOccupied() == ParkingConstants.DISABLED_EMPTY || bay.getOccupied() == ParkingConstants.PEDESTRIAN) {
			//the bay wasn't occupied then return false
			log.info("There is no car parked in the bay: " + bay.getIndex());
			return false;
		} else {
			//set the bay as empty again
			if (bay.isDisabledBay()) {
				bay.setOccupied(ParkingConstants.DISABLED_EMPTY);
			} else {
				bay.setOccupied(ParkingConstants.NON_DISABLED_EMPTY);
			}
			parkingBayRepository.save(bay);
			//the bay was occupied then return true
			return true;
		}
	}
	
	private List<Character> getAvailableTypes(){
		//define the mining of available with the types
		List<Character> types = new ArrayList<Character>();
		types.add(ParkingConstants.DISABLED_EMPTY);
		types.add(ParkingConstants.NON_DISABLED_EMPTY);
		return types;
	}
	
	private List<Character> getAvailableDisabledTypes(){
		//define the mining of available with the types
		List<Character> types = new ArrayList<Character>();
		types.add(ParkingConstants.DISABLED_EMPTY);
		return types;
	}
	
	

}
