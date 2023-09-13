/**
 *
 */
package com.tithe.service.mutation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.AddressEntity;
import com.tithe.entity.FamilyEntity;
import com.tithe.entity.KoottaymaEntity;
import com.tithe.model.AddressMutationInput;
import com.tithe.model.FamilyMutationInput;
import com.tithe.repository.FamilyRepository;
import com.tithe.service.query.AddressQueryService;
import com.tithe.service.query.KoottaymaQueryService;
import com.tithe.utils.ObjectValidation;

import graphql.GraphQLException;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class FamilyMutationService {

	@Autowired
	private ObjectValidation objectValidation;

	@Autowired
	private AddressQueryService addressQueryService;

	@Autowired
	private KoottaymaQueryService koottaymaQueryService;

	@Autowired
	private FamilyRepository familyRepository;

	public FamilyEntity createOneFamily(FamilyMutationInput familyMutationInput) {

		objectValidation.validateObject(familyMutationInput);

		FamilyEntity family = new FamilyEntity();
		family.setFamilyName(familyMutationInput.getFamilyName());

		AddressMutationInput addressInput = familyMutationInput.getAddress();
		if (addressInput!=null) {
			AddressEntity address = addressQueryService.getOneAddress(addressInput);
			if (address!=null) {
				family.setAddress(address);
			}
			else {
				family.setAddress(addressQueryService.buildAddressEntity(addressInput));
			}
		}

		family.setPhone(familyMutationInput.getPhone());
		family.setKoottayma(koottaymaQueryService.getOneKoottayma(familyMutationInput.getKoottaymaId()));

		family.setActive(familyMutationInput.getActive());

		return familyRepository.save(family);
	}

	public FamilyEntity activateOneFamily(Long familyId) {
		if (familyId!=null) {
			Optional<FamilyEntity> obtainedFamily = familyRepository.findById(familyId);
			FamilyEntity family = obtainedFamily.orElseThrow();
			family.setActive(true);
			return familyRepository.save(family);
		}
		GraphQLException exception = new GraphQLException("Some Error Occured") ;
		throw exception;
	}

	public FamilyEntity deactivateOneFamily(Long familyId) {
		if (familyId!=null) {
			Optional<FamilyEntity> obtainedFamily = familyRepository.findById(familyId);
			FamilyEntity family = obtainedFamily.orElseThrow();
			family.setActive(false);
			return familyRepository.save(family);
		}
		GraphQLException exception = new GraphQLException("Some Error Occured") ;
		throw exception;
	}

}
