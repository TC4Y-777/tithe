/**
 * 
 */
package com.tithe.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Sam T George
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AddressEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long addressId;
	
	@ManyToOne
	@JoinColumn(name = "street_id")
	private StreetEntity street;
	
	@ManyToOne
	@JoinColumn(name = "city_id")
	private CityEntity city;
	
	@ManyToOne
	@JoinColumn(name = "district_id")
	private DistrictEntity district;
	
	@ManyToOne
	@JoinColumn(name = "state_id")
	private StateEntity state;
	
	@ManyToOne
	@JoinColumn(name = "pincode_id")
	private PincodeEntity pincode;
	
	@OneToMany(mappedBy = "address")
	private List<FamilyEntity> families;
	
	@OneToMany(mappedBy = "address")
	private List<ParishEntity> parishes;
	
	@OneToMany(mappedBy = "address")
	private List<ForaneEntity> foranes;

}
