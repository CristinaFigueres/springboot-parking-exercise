package com.demo.parking.test.integration;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.parking.model.Parking;
import com.demo.parking.model.ParkingBay;
import com.demo.parking.repository.ParkingBayRepository;
import com.demo.parking.repository.ParkingRepository;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ParkingBayRepositoryTest {
	
	@Autowired
	private ParkingRepository parkingRepository;
	
	@Autowired
	private ParkingBayRepository parkingBayRepository;
	
	private Parking parking;
	private Sort sort;
	List<Character> types;
	List<Character> typesDisabled;
	
	@Before
    public void setUp() {
		//parking 5 has 25 bays (In Memory DB):
		//pedestrian = 8 and 12
		//disabled = 5 and 10
		Optional<Parking> parkingOp = parkingRepository.findById(new Long(5));
		parking =parkingOp.get();
		sort = new Sort(Sort.Direction.ASC, "minDistance").and(new Sort(Sort.Direction.ASC, "index"));
		types = new ArrayList<Character>();
		types.add('U');
		types.add('@');
		typesDisabled = new ArrayList<Character>();
		typesDisabled.add('@');
    }
	
	@Test
    public void testCountAvailableBays() {
        assertEquals(23, parkingBayRepository.countAvailableBays(parking.getId(), types));
    }
	
	@Test
    public void testCountAvailableDisabledBays() {
        assertEquals(2, parkingBayRepository.countAvailableBays(parking.getId(), typesDisabled));
    }
	
	@Test
    public void testFirstAvailable() {
		List<ParkingBay> pb = parkingBayRepository.firstAvailable(parking.getId(), types, sort);
        assertEquals(507, pb.get(0).getIndex().longValue());
    }
	
	@Test
    public void testFirstAvailableDisabled() {
		List<ParkingBay> pb = parkingBayRepository.firstAvailable(parking.getId(), typesDisabled, sort);
        assertEquals(510, pb.get(0).getIndex().longValue());
    }
	
}
