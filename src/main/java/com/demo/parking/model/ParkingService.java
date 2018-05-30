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
//	@ApiOperation(value = "Find student by id",
//    notes = "Also returns a link to retrieve all students with rel - all-students")
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
			return ResponseEntity.ok(pbAvail);
		}

		return ResponseEntity.noContent().build();
	}
	
	private List<Character> getAvailableTypes(){
		//define the mining of available with the enum types
		List<Character> types = new ArrayList<Character>();
		types.add('U');
		types.add('@');
		return types;
	}
	
	private List<Character> getAvailableDisabledTypes(){
		//define the mining of available with the enum types
		List<Character> types = new ArrayList<Character>();
		types.add('@');
		return types;
	}
//	
//	@PutMapping("/parking/{id}/unparkCar")
//	public ResponseEntity<Object> unparkCar(@PathParam("index") int indexUnpark, @PathVariable long id) {
//
//		Optional<Parking> parking = parkingRepository.findById(id);
//
//		if (!parking.isPresent())
//			return ResponseEntity.notFound().build();
//
//		//TODO: make free the index bay
////		student.setId(id);
////		
////		studentRepository.save(student);
//
//		return ResponseEntity.noContent().build();
//	}
//	
//	@GetMapping("/parking/{id}/print")
//	public ResponseEntity<Object> printParking() {
//		
//		List<Parking> parking = parkingRepository.findAll();
//		
//		//TODO: print the info of the parking lot
//		return null;
//	}
	
}
