package com.demo.parking.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingBayRepository extends JpaRepository<ParkingBay, Long>{
	
	//TODO: no tiene en cuenta el False en la columna correcta
	long countByParkingIdAndOccupiedFalse(long id);
	

}
