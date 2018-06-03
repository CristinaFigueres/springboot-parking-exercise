package com.demo.parking.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.parking.model.Parking;
import com.demo.parking.model.ParkingBay;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParkingTest {
    private Parking parking;

    @Before
    public void setUp() {
    	// creates a parking size 3 with a pedestrian exit at index 5 and a Disabled bay at index 6
    	parking = new Parking(new Long(1), 3);
    	List<ParkingBay> pbs = new ArrayList<ParkingBay>();
    	ParkingBay pb1 = new ParkingBay(new Long(1), false, false, 'U', 4, parking);
    	pbs.add(pb1);
    	ParkingBay pb2 = new ParkingBay(new Long(2), false, false, 'U', 3, parking);
    	pbs.add(pb2);
    	ParkingBay pb3 = new ParkingBay(new Long(3), false, false, 'U', 2, parking);
    	pbs.add(pb3);
    	ParkingBay pb4 = new ParkingBay(new Long(4), false, false, '@', 1, parking);
    	pbs.add(pb4);
    	ParkingBay pb5 = new ParkingBay(new Long(5), true, false, '=', null, parking);
    	pbs.add(pb5);
    	ParkingBay pb6 = new ParkingBay(new Long(6), false, true, 'U', 1, parking);
    	pbs.add(pb6);
    	ParkingBay pb7 = new ParkingBay(new Long(7), false, false, 'U', 2, parking);
    	pbs.add(pb7);
    	ParkingBay pb8 = new ParkingBay(new Long(8), false, false, 'U', 3, parking);
    	pbs.add(pb8);    	
    	ParkingBay pb9 = new ParkingBay(new Long(9), false, false, 'U', 4, parking);
    	pbs.add(pb9);      	
    	parking.setBays(pbs);   	
    }

    @Test
    public void testToString() {
    	assertEquals("UUU\nU=@\nUUU\n", parking.toString());
    }

}
