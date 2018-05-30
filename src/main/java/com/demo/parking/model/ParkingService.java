package com.demo.parking.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingService {

	@Autowired
	private ParkingRepository parkingRepository;
	
	@Autowired
	private ParkingBayRepository parkingBayRepository;

	@GetMapping("/parking/{id}")
	public ResponseEntity<Object> availableBays(@PathVariable long id) {
		//return the available bays, the empty ones
		return ResponseEntity.ok(parkingBayRepository.countAvailableBays(id, getAvailableTypes()));
	}
	
	@PutMapping("/parking/{id}/parkCar")
//	@ApiOperation(value = "Park a car by its type",
//    notes = "Also returns the index of the parking bay where is parked the car")
	public ResponseEntity<Object> parkCar(@RequestParam("carType") char carType, @PathVariable long id) {
		Optional<Parking> parking = parkingRepository.findById(id);

		if (!parking.isPresent())
			return ResponseEntity.notFound().build();
		
		//define the order needed to obtain the first available
		Sort sort = new Sort(Sort.Direction.DESC, "minDistance").and(new Sort(Sort.Direction.ASC, "index"));
		
		List<Character> types = null;
		if (carType == 'D') {
			//adding condition Disabled bay
			types = getAvailableDisabledTypes();
		} else {
			types = getAvailableTypes();
		}
		List<ParkingBay> pb = parkingBayRepository.firstAvailable(id, types, sort);
		
		if (pb != null) {
			//get the first one because the list of results is ordered
			ParkingBay pbAvail = pb.get(0);
			pbAvail.setOccupied(carType);
			parkingBayRepository.save(pbAvail);
			return ResponseEntity.ok(pbAvail.getIndex());
		}

		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/parking/{id}/unparkCar")
	public ResponseEntity<Object> unparkCar(@RequestParam("index") int indexUnpark, @PathVariable long id) {

		Optional<Parking> parking = parkingRepository.findById(id);

		if (!parking.isPresent())
			return ResponseEntity.notFound().build();
		
		//search for the parking bay to be unpark
		Optional<ParkingBay> pb = parkingBayRepository.findById(new Long(indexUnpark));
		if (!pb.isPresent())
			return ResponseEntity.notFound().build();
		
		ParkingBay bay = pb.get();
		if (bay.getOccupied() == 'U' || bay.getOccupied() == '@') {
			//the bay wasn't occupied then return false
			return ResponseEntity.ok().body(false);
		} else {
			//set the bay as empty again
			if (bay.isDisabledBay()) {
				bay.setOccupied('@');
			} else {
				bay.setOccupied('U');
			}
			parkingBayRepository.save(bay);
			//the bay was occupied then return true
			return ResponseEntity.ok().body(true);
		}
	}
	
	@GetMapping("/parking/{id}/print")
	public ResponseEntity<Object> printParking(@PathVariable long id) {
		
		Optional<Parking> parking = parkingRepository.findById(id);

		if (!parking.isPresent())
			return ResponseEntity.notFound().build();
		
		Parking p = parking.get();
		
		//return the 2-dimension print of the parking selected by the param id
		
		return ResponseEntity.ok(new ResponsePrint(p.toString()));
	}

	
	private List<Character> getAvailableTypes(){
		//define the mining of available with the types
		List<Character> types = new ArrayList<Character>();
		types.add('U');
		types.add('@');
		return types;
	}
	
	private List<Character> getAvailableDisabledTypes(){
		//define the mining of available with the types
		List<Character> types = new ArrayList<Character>();
		types.add('@');
		return types;
	}
	
	class ResponsePrint{
		private String content;
		
		public ResponsePrint(String content) {
			super();
			this.content = content;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}
}
