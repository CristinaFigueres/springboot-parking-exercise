package com.demo.parking.model;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingBayRepository extends JpaRepository<ParkingBay, Long>{

	 @Query("select count(*) from ParkingBay p where p.parking.id = ?1 and p.occupied in (?2)")
	 long countAvailableBays(long idParking, List<Character> typeBay);
	
	 //TODO: make it return the first result
	 @Query("select p from ParkingBay p where p.parking.id = ?1 and p.occupied in (?2)")
	 List<ParkingBay> firstAvailable(long id, List<Character> typeBay, Sort sort);

}
