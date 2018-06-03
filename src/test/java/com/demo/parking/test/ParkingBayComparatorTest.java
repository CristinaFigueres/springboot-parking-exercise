package com.demo.parking.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.parking.model.Parking;
import com.demo.parking.model.ParkingBay;
import com.demo.parking.utils.ParkingBayComparator;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParkingBayComparatorTest {
    private Parking parking;
    private ParkingBayComparator parkingComp;

    @Before
    public void setUp() {
    	// creates a parking size 3 with a pedestrian exit at index 5 and a Disabled bay at index 6
    	parking = new Parking(new Long(1), 3);
    	List<ParkingBay> pbs = new ArrayList<ParkingBay>();
    	ParkingBay pb1 = new ParkingBay(new Long(1), false, false, 'U', 4, parking);
    	ParkingBay pb2 = new ParkingBay(new Long(2), false, false, 'U', 3, parking);
    	ParkingBay pb3 = new ParkingBay(new Long(3), false, false, 'U', 2, parking);
    	ParkingBay pb4 = new ParkingBay(new Long(4), false, false, 'U', 1, parking);
    	ParkingBay pb5 = new ParkingBay(new Long(5), true, false, '=', null, parking);
    	ParkingBay pb6 = new ParkingBay(new Long(6), false, true, '@', 1, parking);
    	ParkingBay pb7 = new ParkingBay(new Long(7), false, false, 'U', 2, parking);
    	ParkingBay pb8 = new ParkingBay(new Long(8), false, false, 'U', 3, parking);
    	ParkingBay pb9 = new ParkingBay(new Long(9), false, false, 'U', 4, parking);
    	
    	//reverse order
    	pbs.add(pb9);
    	pbs.add(pb8);
    	pbs.add(pb7);
    	pbs.add(pb6);
    	pbs.add(pb5);
    	pbs.add(pb4);
    	pbs.add(pb3);
    	pbs.add(pb2);
    	pbs.add(pb1);
    	
    	parking.setBays(pbs);
    	//creates the Comparator
    	parkingComp = new ParkingBayComparator();
    }

    
    @Test
    public void testEqual() {
    	ParkingBay pb = parking.getBays().get(2);
    	ParkingBay pbbis = parking.getBays().get(2);
        int result = parkingComp.compare(pb, pbbis);
        assertTrue(result == 0);
    }

    @Test
    public void testGreaterThan() {
    	ParkingBay pb3 = parking.getBays().get(2);
    	ParkingBay pb5 = parking.getBays().get(4);
        int result = parkingComp.compare(pb3, pb5);
        assertTrue(result >= 1);
    }

    @Test
    public void testLessThan() {
    	ParkingBay pb3 = parking.getBays().get(2);
    	ParkingBay pb5 = parking.getBays().get(4);
        int result = parkingComp.compare(pb5, pb3);
        assertTrue(result <= -1);
    }
    
    @Test
    public void testSort() {
    	//sort by index
    	Collections.sort(parking.getBays(), parkingComp);
    	ParkingBay pb3 = parking.getBays().get(2);
    	ParkingBay pb3bis = parking.getBays().get(2);
    	ParkingBay pb5 = parking.getBays().get(4);
        int result = parkingComp.compare(pb3, pb3bis);
        assertTrue(result == 0);
        result = parkingComp.compare(pb3, pb5);
        assertTrue(result <= -1);
        result = parkingComp.compare(pb5, pb3);
        assertTrue(result >= 1);
    }

}
	