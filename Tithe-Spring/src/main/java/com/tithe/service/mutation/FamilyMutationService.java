/**
 *
 */
package com.tithe.service.mutation;


import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.AddressEntity;
import com.tithe.entity.FamilyEntity;
import com.tithe.entity.PersonEntity;
import com.tithe.model.AddressMutationInput;
import com.tithe.model.FamilyMutationInput;
import com.tithe.model.PersonMutationInput;
import com.tithe.model.PersonRelationInputModel;
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
	private PersonMutationService personMutationService;

	@Autowired
	private FamilyRepository familyRepository;

	public FamilyEntity createOneFamily(FamilyMutationInput familyMutationInput) {

		objectValidation.validateObject(familyMutationInput);

		FamilyEntity family = new FamilyEntity();
		family.setFamilyName(familyMutationInput.getFamilyName());

//		If the same address already exists, then set that address to family entity or else build a new address 
//		entity and set it to family entity
		AddressMutationInput addressInput = familyMutationInput.getAddress();
		if (addressInput != null) {
			AddressEntity address = addressQueryService.getOneAddress(addressInput);
			if (address != null) {
				family.setAddress(address);
			} else {
				family.setAddress(addressQueryService.buildAddressEntity(addressInput));
			}
		}

		family.setPhone(familyMutationInput.getPhone());
		family.setKoottayma(
				koottaymaQueryService.getOneKoottayma(familyMutationInput.getKoottaymaId()));

//		Build Head of Family person entity
		PersonMutationInput headOfFamilyInput = familyMutationInput.getHeadOfFamily();
		PersonEntity headOfFamily = personMutationService.buildPersonWithTithe(family,
				headOfFamilyInput);
		family.setHeadOfFamily(headOfFamily);

//		Build Family members
		List<PersonMutationInput> familyMemberInputs = familyMutationInput.getPersons();
		if (familyMemberInputs != null) {

			objectValidation.validateObjects(familyMemberInputs);
			List<PersonEntity> familyMembers = personMutationService.buildPersonsWithTithe(family,
					familyMemberInputs);
			family.setPersons(familyMembers);
		}

		family.setActive(familyMutationInput.getActive());

		return familyRepository.save(family);
	}

	public FamilyEntity changeHeadOfFamily(Long familyId,
			PersonRelationInputModel headOfFamilyModel,
			List<PersonRelationInputModel> personModels) {

		objectValidation.validateObject(headOfFamilyModel);
		objectValidation.validateObject(personModels);

		if (familyId == null) {
			throw new GraphQLException("Family Id is invalid");
		}

		Optional<FamilyEntity> obtainedFamily = familyRepository.findById(familyId);
		FamilyEntity family = obtainedFamily.orElseThrow();

		PersonEntity headOfFamily = personMutationService
				.changeRelation(headOfFamilyModel.getPersonId(), headOfFamilyModel.getRelationId());
		family.setHeadOfFamily(headOfFamily);

		if (personModels != null && personModels.size() != 0) {
			for (PersonRelationInputModel personModel : personModels) {
				personMutationService.changeRelation(personModel.getPersonId(),
						personModel.getRelationId());
			}
		}

		return family;

	}

	public FamilyEntity activateOneFamily(Long familyId) {
		if (familyId != null) {
			Optional<FamilyEntity> obtainedFamily = familyRepository.findById(familyId);
			FamilyEntity family = obtainedFamily.orElseThrow();
			family.setActive(true);
			return familyRepository.save(family);
		}
		GraphQLException exception = new GraphQLException("Some Error Occured");
		throw exception;
	}

	public FamilyEntity deactivateOneFamily(Long familyId) {
		if (familyId != null) {
			Optional<FamilyEntity> obtainedFamily = familyRepository.findById(familyId);
			FamilyEntity family = obtainedFamily.orElseThrow();
			family.setActive(false);
			return familyRepository.save(family);
		}
		GraphQLException exception = new GraphQLException("Some Error Occured");
		throw exception;
	}

}
