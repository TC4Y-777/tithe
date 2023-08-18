/**
 *
 */
package com.tithe.service.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.tithe.entity.AddressEntity;
import com.tithe.entity.CityEntity;
import com.tithe.entity.DistrictEntity;
import com.tithe.entity.PincodeEntity;
import com.tithe.entity.StateEntity;
import com.tithe.entity.StreetEntity;
import com.tithe.model.AddressQueryFilter;
import com.tithe.repository.AddressRepository;
import com.tithe.repository.CityRepository;
import com.tithe.repository.DistrictRepository;
import com.tithe.repository.PincodeRepository;
import com.tithe.repository.StateRepository;
import com.tithe.repository.StreetRepository;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class AddressQueryService {

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

	public AddressEntity getOneAddress(Long id) {
		Optional<AddressEntity> address = addressRepository.findById(id);
		return address.orElse(null);
	}

	public AddressEntity getOneAddress(AddressQueryFilter addressQueryFilter) {
		 List<AddressEntity> addresses = getManyAddresses(addressQueryFilter);
		 if (addresses!=null) {
			if (!addresses.isEmpty()) {
				return addresses.get(0);
			}
		 }
		 return null;
	}

	public List<AddressEntity> getManyAddresses(AddressQueryFilter addressQueryFilter) {
		AddressEntity address = buildAddressEntity(addressQueryFilter);

		Example<AddressEntity> exampleAddress = Example.of(address);
		List<AddressEntity> matchingAddresses = addressRepository.findAll(exampleAddress);

		if (matchingAddresses.size()!=0) {
			return matchingAddresses;
		}
		return null;
	}

	public List<AddressEntity> getAllAddresses() {
		List<AddressEntity> addresses = addressRepository.findAll();
		if (addresses.size()!=0) {
			return addresses;
		}
		return null;
	}

	public StreetEntity getOneStreet(Long id) {
		Optional<StreetEntity> street = streetRepository.findById(id);
		return street.orElse(null);
	}

	public List<StreetEntity> getAllStreets() {
		List<StreetEntity> streets = streetRepository.findAll();
		if (streets.size()!=0) {
			return streets;
		}
		return null;
	}

	public CityEntity getOneCity(Long id) {
		Optional<CityEntity> city = cityRepository.findById(id);
		return city.orElse(null);
	}

	public List<CityEntity> getAllCities() {
		List<CityEntity> cities = cityRepository.findAll();
		if (cities.size()!=0) {
			return cities;
		}
		return null;
	}

	public DistrictEntity getOneDistrict(Long id) {
		Optional<DistrictEntity> district = districtRepository.findById(id);
		return district.orElse(null);
	}

	public List<DistrictEntity> getAllDistricts() {
		List<DistrictEntity> districts = districtRepository.findAll();
		if (districts.size()!=0) {
			return districts;
		}
		return null;
	}

	public StateEntity getOneState(Long id) {
		Optional<StateEntity> state = stateRepository.findById(id);
		return state.orElse(null);
	}

	public List<StateEntity> getAllStates() {
		List<StateEntity> states = stateRepository.findAll();
		if (states.size()!=0) {
			return states;
		}
		return null;
	}

	public PincodeEntity getOnePincode(Long id) {
		Optional<PincodeEntity> pincode = pincodeRepository.findById(id);
		return pincode.orElse(null);
	}

	public List<PincodeEntity> getAllPincodes() {
		List<PincodeEntity> pincodes = pincodeRepository.findAll();
		if (pincodes.size()!=0) {
			return pincodes;
		}
		return null;
	}

	public AddressEntity buildAddressEntity(AddressQueryFilter addressQueryFilter) {
		AddressEntity address = new AddressEntity();

		if (addressQueryFilter.getBuildingName()!=null && !addressQueryFilter.getBuildingName().isBlank()) {
			address.setBuildingName(addressQueryFilter.getBuildingName());
		}
		if (addressQueryFilter.getStreetId()!=null) {
			address.setStreet(getOneStreet(addressQueryFilter.getStreetId()));
		}
		if (addressQueryFilter.getCityId()!=null) {
			address.setCity(getOneCity(addressQueryFilter.getCityId()));
		}
		if (addressQueryFilter.getDistrictId()!=null) {
			address.setDistrict(getOneDistrict(addressQueryFilter.getDistrictId()));
		}
		if (addressQueryFilter.getStateId()!=null) {
			address.setState(getOneState(addressQueryFilter.getStateId()));
		}
		if (addressQueryFilter.getPincodeId()!=null) {
			address.setPincode(getOnePincode(addressQueryFilter.getPincodeId()));
		}
		return address;
	}

}
