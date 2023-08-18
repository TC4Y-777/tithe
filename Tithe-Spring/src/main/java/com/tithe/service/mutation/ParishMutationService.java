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
import com.tithe.utils.ObjectValidation;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class ParishMutationService {

	@Autowired
	private ObjectValidation objectValidation;

	@Autowired
	private ParishRepository parishRepository;

	@Autowired
	private AddressQueryService addressQueryService;

	@Autowired
	private ForaneQueryService foraneQueryService;

	public ParishEntity createOneParish(ParishMutationInput parishMutationInput) {

		objectValidation.validateObject(parishMutationInput);

		ParishEntity parish = new ParishEntity();
		parish.setParishName(parishMutationInput.getParishName());

		AddressMutationInput addressInput = parishMutationInput.getAddress();
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
		parish.setForane(foraneQueryService.getOneForane(parishMutationInput.getForaneId()));
		parish.setActive(parishMutationInput.getActive());

		return parishRepository.save(parish);
	}

}
