package com.demo.parking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
		
}
