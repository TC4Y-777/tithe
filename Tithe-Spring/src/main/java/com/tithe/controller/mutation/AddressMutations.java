/**
 * 
 */
package com.tithe.controller.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.tithe.entity.AddressEntity;
import com.tithe.entity.CityEntity;
import com.tithe.entity.DistrictEntity;
import com.tithe.entity.PincodeEntity;
import com.tithe.entity.StateEntity;
import com.tithe.entity.StreetEntity;
import com.tithe.model.AddressMutationInput;
import com.tithe.service.mutation.AddressMutationService;

/**
 * @author Ashish Sam T George
 *
 */
@Controller
public class AddressMutations {
	
	@Autowired
	private AddressMutationService addressMutationService;

	@MutationMapping(name = "createOneAddress")
	public AddressEntity createOneAddress(@Argument AddressMutationInput addressMutationInput) {
		return addressMutationService.createOneAddress(addressMutationInput);
	}
	
	@MutationMapping(name = "createOneStreet")
	public StreetEntity createOneStreet(@Argument String streetName) {
		return addressMutationService.createOneStreet(streetName);
	}
	
	@MutationMapping(name = "createOneCity")
	public CityEntity createOneCity(@Argument String cityName) {
		return addressMutationService.createOneCity(cityName);
	}
	
	@MutationMapping(name = "createOneDistrict")
	public DistrictEntity createOneDistrict(@Argument String districtName) {
		return addressMutationService.createOneDistrict(districtName);
	}
	
	@MutationMapping(name = "createOneState")
	public StateEntity createOneState(@Argument String stateName) {
		return addressMutationService.createOneState(stateName);
	}
	
	@MutationMapping(name = "createOnePincode")
	public PincodeEntity createOnePincode(@Argument String pincode) {
		return addressMutationService.createOnePincode(pincode);
	}
	
}
