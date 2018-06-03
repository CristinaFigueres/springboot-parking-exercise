package com.demo.parking.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.parking.controller.ParkingController;
import com.demo.parking.model.Parking;
import com.demo.parking.repository.ParkingBayRepository;
import com.demo.parking.repository.ParkingRepository;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ParkingControllerTest {
		
		@Autowired
		private ParkingRepository parkingRepository;
		
		@Autowired
		private ParkingBayRepository parkingBayRepository;
		
		@Autowired
		private ParkingController parkingController;
		
		private Parking parking;
		
		Long id;
		
		@Before
	    public void setUp() {
			//parking 5 has 25 bays (In Memory DB):
			//pedestrian = 8 and 12
			//disabled = 5 and 10
			Optional<Parking> parkingOp = parkingRepository.findById(new Long(5));
			parking =parkingOp.get();
			id = parking.getId();
	    }
		
		
	    @Test
	    public void testGetAvailableBays() {
	        assertEquals(23, parkingController.availableBays(parking.getId()));
	    }

	    @Test
	    public void testParkCarVehiculeTypeC() {
	        assertEquals(507, parkingController.parkCar('C', parking.getId()));
	        assertEquals(22, parkingController.availableBays(parking.getId()));
	        //unpark the parked to leave the DB as before
	        parkingController.unparkCar(parkingBayRepository.findById(new Long(507)).get());
	    }

	    @Test
	    public void testParkCarVehiculeTypeM() {
	        assertEquals(507, parkingController.parkCar('M', parking.getId()));
	        assertEquals(22, parkingController.availableBays(parking.getId()));
	        //unpark the parked to leave the DB as before
	        parkingController.unparkCar(parkingBayRepository.findById(new Long(509)).get());
	    }

	    @Test
	    public void testParkCarTwoVehicules() {
	        assertEquals(507, parkingController.parkCar('C', parking.getId()));
	        assertEquals(22, parkingController.availableBays(parking.getId()));
	        assertEquals(509, parkingController.parkCar('M', parking.getId()));
	        assertEquals(21, parkingController.availableBays(parking.getId()));
	        
	        //unpark the parked to leave the DB as before
	        parkingController.unparkCar(parkingBayRepository.findById(new Long(507)).get());
	        parkingController.unparkCar(parkingBayRepository.findById(new Long(509)).get());
	    }

	    @Test
	    public void testParkCarDisabled() {
	        assertEquals(510, parkingController.parkCar('D', parking.getId()));
	        assertEquals(22, parkingController.availableBays(parking.getId()));

	        assertEquals(505, parkingController.parkCar('D', parking.getId()));
	        assertEquals(21, parkingController.availableBays(parking.getId()));

	        assertEquals(-1, parkingController.parkCar('D', parking.getId()));
	        assertEquals(21, parkingController.availableBays(parking.getId()));
	        
	        //unpark the parked to leave the DB as before
	        parkingController.unparkCar(parkingBayRepository.findById(new Long(510)).get());
	        parkingController.unparkCar(parkingBayRepository.findById(new Long(505)).get());
	    }

	    @Test
	    public void testUnparkCar() {
	        final long firstCarBayIndex = parkingController.parkCar('X', parking.getId());
	        assertTrue(parkingController.unparkCar(parkingBayRepository.findById(firstCarBayIndex).get()));
	        assertEquals(23, parkingController.availableBays(parking.getId()));
	        assertFalse(parkingController.unparkCar(parkingBayRepository.findById(firstCarBayIndex).get()));

	        final long secondCarBayIndex = parkingController.parkCar('D', parking.getId());
	        assertTrue(parkingController.unparkCar(parkingBayRepository.findById(secondCarBayIndex).get()));
	        assertEquals(23, parkingController.availableBays(parking.getId()));
	        assertFalse(parkingController.unparkCar(parkingBayRepository.findById(secondCarBayIndex).get()));

	        assertFalse(parkingController.unparkCar(parkingBayRepository.findById(new Long(508)).get()));
	    }
	    
	    @Test
	    public void testIsPresent() {
	        assertTrue(parkingController.isPresent(5));
	        assertFalse(parkingController.isPresent(8));
	    }
	    
	    @Test
	    public void testPresentParking() {
	        assertEquals(5, parkingController.presentParking(5).getId().intValue());
	    }
	    
	    @Test
	    public void testPresentParkingBay() {
	        assertEquals(503, parkingController.presentBay(503).getIndex().intValue());
	    }
	    
		@Test
	    public void testToString() {
	        assertEquals("UUUU@\n@U=UU\nU=UUU\nUUUUU\nUUUUU", parking.toString());
	    }
	    
	    @Test
	    public void testToStringSecond() {
	    	
	    	parkingController.parkCar('C', id);
	 		Optional<Parking> parkingOp = parkingRepository.findById(id);
	 		parking =parkingOp.get();
	        assertEquals("UUUU@\n@U=CU\nU=UUU\nUUUUU\nUUUUU", parking.toString());
	
	        parkingController.parkCar('C', id);
	 		parkingOp = parkingRepository.findById(id);
	 		parking =parkingOp.get();
	        assertEquals("UUUU@\n@C=CU\nU=UUU\nUUUUU\nUUUUU", parking.toString());
	        
	        parkingController.parkCar('M', id);
	 		parkingOp = parkingRepository.findById(id);
	 		parking =parkingOp.get();
	        assertEquals("UUUU@\n@C=CU\nM=UUU\nUUUUU\nUUUUU", parking.toString());
	
	        parkingController.parkCar('M', id);
	 		parkingOp = parkingRepository.findById(id);
	 		parking =parkingOp.get();
	        assertEquals("UUUU@\n@C=CU\nM=MUU\nUUUUU\nUUUUU", parking.toString());
	        
	        parkingController.parkCar('D', id);
	        parkingOp = parkingRepository.findById(id);
	 		parking =parkingOp.get();
	        assertEquals("UUUU@\nDC=CU\nM=MUU\nUUUUU\nUUUUU", parking.toString());
	
	        parkingController.parkCar('D', id);
	 		parkingOp = parkingRepository.findById(id);
	 		parking =parkingOp.get();
	        assertEquals("UUUUD\nDC=CU\nM=MUU\nUUUUU\nUUUUU", parking.toString());
	
	        parkingController.parkCar('D', id);
	        parkingOp = parkingRepository.findById(id);
	  		parking =parkingOp.get();
	        assertEquals("UUUUD\nDC=CU\nM=MUU\nUUUUU\nUUUUU", parking.toString());
	
	        parkingController.unparkCar(parkingBayRepository.findById(new Long(503)).get());
	        parkingOp = parkingRepository.findById(id);
	  		parking =parkingOp.get();
	        assertEquals("UUUUD\nDC=CU\nM=MUU\nUUUUU\nUUUUU", parking.toString());
	        
	        parkingController.unparkCar(parkingBayRepository.findById(new Long(513)).get());
	        parkingOp = parkingRepository.findById(id);
	  		parking =parkingOp.get();
	        assertEquals("UUUUD\nDC=CU\nM=UUU\nUUUUU\nUUUUU", parking.toString());
	        
	        //unpark the parked to leave the DB as before
	        parkingController.unparkCar(parkingBayRepository.findById(new Long(505)).get());
	        parkingController.unparkCar(parkingBayRepository.findById(new Long(510)).get());
	        parkingController.unparkCar(parkingBayRepository.findById(new Long(507)).get());
	        parkingController.unparkCar(parkingBayRepository.findById(new Long(509)).get());
	        parkingController.unparkCar(parkingBayRepository.findById(new Long(511)).get());     
	    }

	    
}
