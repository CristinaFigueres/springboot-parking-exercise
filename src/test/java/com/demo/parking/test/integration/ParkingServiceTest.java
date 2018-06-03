package com.demo.parking.test.integration;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.parking.model.Parking;
import com.demo.parking.repository.ParkingRepository;
import com.demo.parking.service.ParkingService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ParkingServiceTest {
	
	@Autowired
	private ParkingService parkingService;
	
	
	@Before
    public void setUp() {
    }

    @Test
    public void testParkCarNoParking() {
        assertEquals(ResponseEntity.notFound().build(), parkingService.parkCar('C', 8));
    }


    @Test
    public void testUnparkCarNoCar() {
        assertEquals(ResponseEntity.notFound().build(), parkingService.unparkCar(508, 8));
        assertEquals(ResponseEntity.notFound().build(), parkingService.unparkCar(808, 5));        
    }

}
