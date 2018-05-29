package com.demo.parking.model;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class ParkingService {

	@Autowired
	private ParkingRepository parkingRepository;
	
	@Autowired
	private ParkingBayRepository parkingBayRepository;

	@GetMapping("/parking/{id}")
	public long availableBays(@PathVariable long id) {
		
		//TODO: make it available
		return parkingBayRepository.countByParkingIdAndOccupiedFalse(id);
	}
//	
//	@PutMapping("/parking/{id}/parkCar")
//	@ApiOperation(value = "Find student by id",
//    notes = "Also returns a link to retrieve all students with rel - all-students")
//	public ResponseEntity<Object> parkCar(@PathParam("disabled") boolean disabled, @PathVariable long id) {
//
//		Optional<Parking> parking = parkingRepository.findById(id);
//
//		if (!parking.isPresent())
//			return ResponseEntity.notFound().build();
//
//		//TODO: search for a available bay
////		student.setId(id);
////		
////		studentRepository.save(student);
//
//		return ResponseEntity.noContent().build();
//	}
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
