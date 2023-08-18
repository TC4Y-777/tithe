/**
 *
 */
package com.tithe.service.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.AddressEntity;
import com.tithe.entity.CityEntity;
import com.tithe.entity.DistrictEntity;
import com.tithe.entity.PincodeEntity;
import com.tithe.entity.StateEntity;
import com.tithe.entity.StreetEntity;
import com.tithe.model.AddressMutationInput;
import com.tithe.repository.AddressRepository;
import com.tithe.repository.CityRepository;
import com.tithe.repository.DistrictRepository;
import com.tithe.repository.PincodeRepository;
import com.tithe.repository.StateRepository;
import com.tithe.repository.StreetRepository;
import com.tithe.service.query.AddressQueryService;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class AddressMutationService {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private StreetRepository streetRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private PincodeRepository pincodeRepository;

	@Autowired
	private AddressQueryService addressQueryService;

	public AddressEntity createOneAddress(AddressMutationInput addressMutationInput) {
		AddressEntity address = addressQueryService.buildAddressEntity(addressMutationInput);
		return addressRepository.save(address);
	}

	public StreetEntity createOneStreet(String streetName) {
		StreetEntity street = new StreetEntity();
		street.setStreetName(streetName);
		return streetRepository.save(street);
	}

	public CityEntity createOneCity(String cityName) {
		CityEntity city = new CityEntity();
		city.setCityName(cityName);
		return cityRepository.save(city);
	}

	public DistrictEntity createOneDistrict(String districtName) {
		DistrictEntity district = new DistrictEntity();
		district.setDistrictName(districtName);
		return districtRepository.save(district);
	}

	public StateEntity createOneState(String stateName) {
		StateEntity state = new StateEntity();
		state.setStateName(stateName);
		return stateRepository.save(state);
	}

	public PincodeEntity createOnePincode(String pincode) {
		PincodeEntity pincodeEntity = new PincodeEntity();
		pincodeEntity.setPincode(pincode);
		return pincodeRepository.save(pincodeEntity);
	}

}
