package com.demo.parking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Entity
@ApiModel(description="All details about the Parking Lot.")
public class ParkingBay {
	@Id
//	@GeneratedValue	
	@Column(name="index")
	private Long index;
	
	@ApiModelProperty(notes="Has a pedestrian exit. ")
	@Column(name="pedestrianExit")
	private boolean pedestrianExit;
	
	@ApiModelProperty(notes="Is a disabled bay. ")
	@Column(name="disabledBay")
	private boolean disabledBay;
	
	@ApiModelProperty(notes="Bay occupied. ")
	@Column(name="occupied")
	private boolean occupied;
	
	@ManyToOne
	@JoinColumn(name = "id")
	private Parking parking;


	public ParkingBay(Long index, boolean pedestrianExit, boolean disabledBay, boolean occupied, Parking parking) {
		super();
		this.index = index;
		this.pedestrianExit = pedestrianExit;
		this.disabledBay = disabledBay;
		this.occupied = occupied;
		this.parking = parking;
	}
	
	
	public Parking getParking() {
		return parking;
	}

	public void setParking(Parking parking) {
		this.parking = parking;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public boolean isPedestrianExit() {
		return pedestrianExit;
	}

	public void setPedestrianExit(boolean pedestrianExit) {
		this.pedestrianExit = pedestrianExit;
	}

	public boolean isDisabledBay() {
		return disabledBay;
	}

	public void setDisabledBay(boolean disabledBay) {
		this.disabledBay = disabledBay;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
		
}
