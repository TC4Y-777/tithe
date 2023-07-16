/**
 * 
 */
package com.tithe.controller.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.tithe.entity.AddressEntity;
import com.tithe.entity.CityEntity;
import com.tithe.entity.DistrictEntity;
import com.tithe.entity.PincodeEntity;
import com.tithe.entity.StateEntity;
import com.tithe.entity.StreetEntity;
import com.tithe.model.AddressQueryFilter;
import com.tithe.service.query.AddressQueryService;

/**
 * @author Ashish Sam T George
 *
 */
@Controller
public class AddressQueries {
	
	@Autowired
	private AddressQueryService addressQueryService;
	
	@QueryMapping(name = "getOneAddress")
	public AddressEntity getOneAddress(@Argument Long id) {
		return addressQueryService.getOneAddress(id);
	}
	
	@QueryMapping(name = "getManyAddresses")
	public List<AddressEntity> getManyAddresses(@Argument AddressQueryFilter addressQueryFilter) {
		return addressQueryService.getManyAddresses(addressQueryFilter);
	}
	
	@QueryMapping(name = "getAllAddresses")
	public List<AddressEntity> getAllAddresses(){
		return addressQueryService.getAllAddresses();
	}
	
	@QueryMapping(name = "getOneStreet")
	public StreetEntity getOneStreet(@Argument Long id) {
		return addressQueryService.getOneStreet(id);
	}
	
	@QueryMapping(name = "getAllStreets")
	public List<StreetEntity> getAllStreets(){
		return addressQueryService.getAllStreets();
	}
	
	@QueryMapping(name = "getOneCity")
	public CityEntity getOneCity(@Argument Long id) {
		return addressQueryService.getOneCity(id);
	}
	
	@QueryMapping(name = "getAllCities")
	public List<CityEntity> getAllCities(){
		return addressQueryService.getAllCities();
	}
	
	@QueryMapping(name = "getOneDistrict")
	public DistrictEntity getOneDistrict(@Argument Long id) {
		return addressQueryService.getOneDistrict(id);
	}
	
	@QueryMapping(name = "getAllDistricts")
	public List<DistrictEntity> getAllDistricts(){
		return addressQueryService.getAllDistricts();
	}
	
	@QueryMapping(name = "getOneState")
	public StateEntity getOneState(@Argument Long id) {
		return addressQueryService.getOneState(id);
	}
	
	@QueryMapping(name = "getAllStates")
	public List<StateEntity> getAllStates(){
		return addressQueryService.getAllStates();
	}
	
	@QueryMapping(name = "getOnePincode")
	public PincodeEntity getOnePincode(@Argument Long id) {
		return addressQueryService.getOnePincode(id);
	}
	
	@QueryMapping(name = "getAllPincodes")
	public List<PincodeEntity> getAllPincodes(){
		return addressQueryService.getAllPincodes();
	}

}
