package com.demo.parking.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ITestParkingService {
	
	@Autowired
	private ParkingService parkingService;
	
	@Autowired
	private ParkingRepository parkingRepository;
	
	private Parking parking;
	private List<ParkingBay> pbs;
	
	@Before
    public void setUp() {
		//parking 5 has 25 bays (In Memory DB):
		//pedestrian = 8 and 12
		//disabled = 5 and 10
		Optional<Parking> parkingOp = parkingRepository.findById(new Long(5));
		parking =parkingOp.get();
    }
	
	@Test
    public void testToString() {
        assertEquals("UUUU@\n@U=UU\nU=UUU\nUUUUU\nUUUUU\n", parking.toString());
    }
	
    @Test
    public void testGetAvailableBays() {
        assertEquals(23, ((Long)parkingService.availableBays(parking.getId()).getBody()).longValue());
    }

    @Test
    public void testParkCarVehiculeTypeC() {
        assertEquals(507, ((Long)parkingService.parkCar('C', parking.getId()).getBody()).longValue());
        assertEquals(22, ((Long)parkingService.availableBays(parking.getId()).getBody()).longValue());
        //unpark the parked to leave the DB as before
        parkingService.unparkCar(507, parking.getId());
    }

    @Test
    public void testParkCarVehiculeTypeM() {
        assertEquals(509, ((Long)parkingService.parkCar('M', parking.getId()).getBody()).longValue());
        assertEquals(21, ((Long)parkingService.availableBays(parking.getId()).getBody()).longValue());
        //unpark the parked to leave the DB as before
        parkingService.unparkCar(509, parking.getId());
    }

    @Test
    public void testParkCarTwoVehicules() {
        assertEquals(511, ((Long)parkingService.parkCar('C', parking.getId()).getBody()).longValue());
        assertEquals(20, ((Long)parkingService.availableBays(parking.getId()).getBody()).longValue());

        assertEquals(513, ((Long)parkingService.parkCar('M', parking.getId()).getBody()).longValue());
        assertEquals(19, ((Long)parkingService.availableBays(parking.getId()).getBody()).longValue());
        
        //unpark the parked to leave the DB as before
        parkingService.unparkCar(511, parking.getId());
        parkingService.unparkCar(513, parking.getId());
    }

    @Test
    public void testParkCarDisabled() {
        assertEquals(506, ((Long)parkingService.parkCar('D', parking.getId()).getBody()).longValue());
        assertEquals(18, ((Long)parkingService.availableBays(parking.getId()).getBody()).longValue());

        assertEquals(505, ((Long)parkingService.parkCar('D', parking.getId()).getBody()).longValue());
        assertEquals(17, ((Long)parkingService.availableBays(parking.getId()).getBody()).longValue());

        assertEquals(-1, ((Long)parkingService.parkCar('D', parking.getId()).getBody()).longValue());
        assertEquals(17, ((Long)parkingService.availableBays(parking.getId()).getBody()).longValue());
        
        //unpark the parked to leave the DB as before
        parkingService.unparkCar(506, parking.getId());
        parkingService.unparkCar(505, parking.getId());
    }

    @Test
    public void testUnparkCar() {
        final int firstCarBayIndex = ((Long)parkingService.parkCar('X', parking.getId()).getBody()).intValue();
        assertTrue(((boolean)parkingService.unparkCar(firstCarBayIndex, parking.getId()).getBody()));
        assertEquals(17, ((Long)parkingService.availableBays(parking.getId()).getBody()).longValue());
        assertFalse(((boolean)parkingService.unparkCar(firstCarBayIndex, parking.getId()).getBody()));

        final int secondCarBayIndex = ((Long)parkingService.parkCar('D', parking.getId()).getBody()).intValue();
        assertTrue(((boolean)parkingService.unparkCar(secondCarBayIndex, parking.getId()).getBody()));
        assertEquals(17, ((Long)parkingService.availableBays(parking.getId()).getBody()).longValue());
        assertFalse(((boolean)parkingService.unparkCar(secondCarBayIndex, parking.getId()).getBody()));

        assertFalse(((boolean)parkingService.unparkCar(8, parking.getId()).getBody()));
    }

    @Test
    public void testToStringAfter() {
        parkingService.parkCar('C', parking.getId());
        parkingService.parkCar('M', parking.getId());
        parkingService.parkCar('C', parking.getId());
        parkingService.parkCar('M', parking.getId());
        parkingService.parkCar('D', parking.getId());
        parkingService.parkCar('D', parking.getId());
        
		Optional<Parking> parkingOp = parkingRepository.findById(new Long(5));
		parking =parkingOp.get();
        
        String t = parking.toString();

        assertEquals("UUUUD\nDC=MU\nC=MUU\nUUUUU\nUUUUU\n", parking.toString());
        
        //unpark the parked to leave the DB as before
        parkingService.unparkCar(507, parking.getId());
        parkingService.unparkCar(509, parking.getId());
        parkingService.unparkCar(511, parking.getId());
        parkingService.unparkCar(513, parking.getId());
        parkingService.unparkCar(506, parking.getId());
        parkingService.unparkCar(505, parking.getId());
    }

}
