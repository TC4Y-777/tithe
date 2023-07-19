/**
 * 
 */
package com.tithe.service.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.AddressEntity;
import com.tithe.entity.ParishEntity;
import com.tithe.model.AddressMutationInput;
import com.tithe.model.ParishMutationInput;
import com.tithe.repository.ParishRepository;
import com.tithe.service.query.AddressQueryService;
import com.tithe.service.query.ForaneQueryService;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class ParishMutationService {
	
	@Autowired
	private ParishRepository parishRepository;
	
	@Autowired
	private AddressQueryService addressQueryService;

	@Autowired
	private ForaneQueryService foraneQueryService;
	
	public ParishEntity createOneParish(ParishMutationInput parishMutationInput) {
		ParishEntity parish = new ParishEntity();
		parish.setParishName(parishMutationInput.getParishName());
		
		AddressMutationInput addressInput = parishMutationInput.getAddressMutationInput();
		if (addressInput!=null) {
			AddressEntity address = addressQueryService.getOneAddress(addressInput);
			if (address!=null) {
				parish.setAddress(address);
			}
			else {
				parish.setAddress(addressQueryService.buildAddressEntity(addressInput));
			}
		}
		
		parish.setPhone(parishMutationInput.getPhone());
		
		if (parishMutationInput.getForaneId()!=null) {
			parish.setForane(foraneQueryService.getOneForane(parishMutationInput.getForaneId()));
		}
		
		parish.setActive(parishMutationInput.getActive());
		
		return parishRepository.save(parish);
	}
	
}
