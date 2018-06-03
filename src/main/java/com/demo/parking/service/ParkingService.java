package com.demo.parking.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.parking.controller.ParkingController;
import com.demo.parking.model.Parking;
import com.demo.parking.model.ParkingBay;

import io.swagger.annotations.ApiOperation;

@RestController
public class ParkingService {
	
	//logger
	private static Logger log = LogManager.getLogger(ParkingService.class);

	@Autowired
	private ParkingController parkingController;

	@GetMapping("/parking/{id}")
	@ApiOperation(value = "Number of available bays in the parking ")
	public ResponseEntity<Object> availableBays(@PathVariable long id) {
		//return the available bays, the empty ones
		log.info("Init REST call /parking/" + id);
		return ResponseEntity.ok(parkingController.availableBays(id));
	}
	
	@PutMapping("/parking/{id}/parkCar")
	@ApiOperation(value = "Park a car with a specified type ",httpMethod="PUT")
	public ResponseEntity<Object> parkCar(@RequestParam("carType") char carType, @PathVariable long id) {
		log.info("Init REST call [GET] /parking/" + id + "/parkCar with carType: " + carType + " for the parking " + id);

		//if the parking lot is not present return notFound
		if (!parkingController.isPresent(id))
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(parkingController.parkCar(carType, id));
	}
	
	@PutMapping("/parking/{id}/unparkCar")
	@ApiOperation(value = "Unpark the car in the specified bay ")
	public ResponseEntity<Object> unparkCar(@RequestParam("index") int indexUnpark, @PathVariable long id) {
		log.info("Init REST call [PUT] /parking/" + id + "/unparkCar with position: " + indexUnpark + " for the parking " + id);
		
		//if the parking lot is not present return notFound
		if (!parkingController.isPresent(id))
			return ResponseEntity.notFound().build();
		
		//search for the parking bay to be unparked, if not return notFound
		ParkingBay bay = parkingController.presentBay(indexUnpark);
		if (bay == null)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok().body(parkingController.unparkCar(bay));
	}
	
	@GetMapping("/parking/{id}/print")
	@ApiOperation(value = "Prints the configuration of the parking lot ")
	public ResponseEntity<Object> printParking(@PathVariable long id) {
		log.info("Init REST call [GET] /parking/" + id + "/print");
		
		//if the parking lot is not present return notFound
		Parking parking = parkingController.presentParking(id);
		if (parking == null)
			return ResponseEntity.notFound().build();
				
		//return the 2-dimension print of the parking selected by the param id
		return ResponseEntity.ok(new ResponsePrint(parking.toString()));
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
