package com.demo.parking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	
	@ApiModelProperty(notes="Bay occupied by. ")
	@Column(name="occupied")
	private char occupied;
	
	@ApiModelProperty(notes="Minim distance to a pedestrian exit. ")
	@Column(name="minDistance")
	private Integer minDistance;
	
	@ManyToOne
	@JoinColumn(name = "id")
	private Parking parking;
	
	public ParkingBay(Long index, boolean pedestrianExit, boolean disabledBay, char occupied,
			Integer minDistance, Parking parking) {
		super();
		this.index = index;
		this.pedestrianExit = pedestrianExit;
		this.disabledBay = disabledBay;
		this.occupied = occupied;
		this.minDistance = minDistance;
		this.parking = parking;
	}


	public ParkingBay() {
		super();
		// TODO Auto-generated constructor stub
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

	public char getOccupied() {
		return occupied;
	}


	public void setOccupied(char occupied) {
		this.occupied = occupied;
	}


	public Integer getMinDistance() {
		return minDistance;
	}


	public void setMinDistance(Integer minDistance) {
		this.minDistance = minDistance;
	}


//	public enum BayType {
//		PEDESTRIAN('='),
//		DISABLED_EMPTY('@'),  
//		NON_DISABLED_EMPTY('U'),
//		DISABLED_OCCUPIED('D');
//		
//		private char description;
//		
//		private BayType(char charact){
//			this.description = charact;
//		}
//		
//		public char getDescription(){
//			return this.description;
//		}
//
//	};
		
}


