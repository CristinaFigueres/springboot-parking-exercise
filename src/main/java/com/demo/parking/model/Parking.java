package com.demo.parking.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Entity
@ApiModel(description="All details about the Parking Lot.")
public class Parking {
	@Id
	@GeneratedValue	
	@ApiModelProperty(hidden=true)
	private Long id;
	
	@ApiModelProperty(notes="Size must be in meters, at least 1 digit, at most 3. ")
	@Size(min=1, max = 3, message="Size must be in meters, at least 1 digit, at most 3.")
	private int size;
	
	@OneToMany(mappedBy = "parking", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ParkingBay> bays = new ArrayList<ParkingBay>();
	
	public Parking() {
		super();
	}

	public Parking(Long id,
			@Size(min = 1, max = 3, message = "Size must be in meters, at least 1 digit, at most 3.") int size) {
		super();
		this.id = id;
		this.size = size;
	}

	public Parking(@Size(min = 1, max = 3, message = "Size must be in meters, at least 1 digit, at most 3.") int size) {
		super();
		this.size = size;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	@Override
    public String toString() {
		 StringBuffer sb = new StringBuffer();
		 StringBuffer line = new StringBuffer();
		 //Ordering the bays in the parking lot to obtain always the same string
		 Collections.sort(this.bays, new ParkingBayComparator());
		 boolean reversed = false;
		 int count = 0;
		 for (ParkingBay b : this.bays) {
			 //we have to be aware of the size to reverse the line
			 if (count % this.size != this.size -1) {
				 line.append(b.getOccupied());
			 } else {
				 line.append(b.getOccupied());
				 if (reversed) {
					 sb.append(line.reverse());
				 } else {
					 sb.append(line);					 
				 }
				 sb.append("\n");
				 line.setLength(0);
				 //one line with normal direction, the other reversed
				 reversed = !reversed;
			 }
			 count++;
		 }
		 return sb.toString();
    }
	 
}
