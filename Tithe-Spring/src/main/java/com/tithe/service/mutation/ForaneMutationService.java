/**
 *
 */
package com.tithe.service.mutation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.AddressEntity;
import com.tithe.entity.ForaneEntity;
import com.tithe.model.AddressMutationInput;
import com.tithe.model.ForaneMutationInput;
import com.tithe.repository.ForaneRepository;
import com.tithe.service.query.AddressQueryService;
import com.tithe.utils.ObjectValidation;

import graphql.GraphQLException;

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

	public ForaneEntity activateOneForane(Long foraneId) {
		if (foraneId!=null) {
			Optional<ForaneEntity> obtainedForane = foraneRepository.findById(foraneId);
			ForaneEntity forane = obtainedForane.orElseThrow();
			forane.setActive(true);
			return foraneRepository.save(forane);
		}
//		TODO Is this the right way to throw an exception?
		GraphQLException exception = new GraphQLException("Some Error Occured") ;
		throw exception;
	}

	public ForaneEntity deactivateOneForane(Long foraneId) {
		if (foraneId!=null) {
			Optional<ForaneEntity> obtainedForane = foraneRepository.findById(foraneId);
			ForaneEntity forane = obtainedForane.orElseThrow();
//			Deactivate only if no parishes are under it
			if (forane.getParishes().size()==0) {
				forane.setActive(false);
				return foraneRepository.save(forane);
			}
		}
//		TODO Is this the right way to throw an exception?
		GraphQLException exception = new GraphQLException("Some Error Occured") ;
		throw exception;
	}

}
