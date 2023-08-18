/**
 *
 */
package com.tithe.service.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.AddressEntity;
import com.tithe.entity.ForaneEntity;
import com.tithe.model.AddressMutationInput;
import com.tithe.model.ForaneMutationInput;
import com.tithe.repository.ForaneRepository;
import com.tithe.service.query.AddressQueryService;
import com.tithe.utils.ObjectValidation;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class ForaneMutationService {

	@Autowired
	private ObjectValidation objectValidation;

	@Autowired
	private AddressQueryService addressQueryService;

	@Autowired
	private ForaneRepository foraneRepository;

	public ForaneEntity createOneForane(ForaneMutationInput foraneMutationInput) {

		objectValidation.validateObject(foraneMutationInput);

		ForaneEntity forane = new ForaneEntity();
		forane.setForaneName(foraneMutationInput.getForaneName());

		AddressMutationInput addressInput = foraneMutationInput.getAddress();
		if (addressInput!=null) {
			AddressEntity address = addressQueryService.getOneAddress(addressInput);
			if (address!=null) {
				forane.setAddress(address);
			}
			else {
				forane.setAddress(addressQueryService.buildAddressEntity(addressInput));
			}
		}

		forane.setPhone(foraneMutationInput.getPhone());
		forane.setActive(foraneMutationInput.getActive());

		return foraneRepository.save(forane);
	}

}
