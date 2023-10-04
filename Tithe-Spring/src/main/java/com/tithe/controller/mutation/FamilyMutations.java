/**
 *
 */
package com.tithe.controller.mutation;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.tithe.entity.FamilyEntity;
import com.tithe.entity.KoottaymaEntity;
import com.tithe.model.FamilyMutationInput;
import com.tithe.model.PersonRelationInputModel;
import com.tithe.service.mutation.FamilyMutationService;


/**
 * @author Ashish Sam T George
 *
 */
@Controller
public class FamilyMutations {

	@Autowired
	private FamilyMutationService familyMutationService;

	@MutationMapping(name = "createOneFamily")
	public FamilyEntity createOneFamily(
			@Argument(name = "family") FamilyMutationInput familyMutationInput) {
		return familyMutationService.createOneFamily(familyMutationInput);
	}

	@MutationMapping(name = "changeHeadOfFamily")
	public FamilyEntity changeHeadOfFamily(@Argument(name = "familyId") Long familyId,
			@Argument(name = "newHeadOfFamily") PersonRelationInputModel headOfFamily,
			@Argument(name = "persons") List<PersonRelationInputModel> persons) {
		return familyMutationService.changeHeadOfFamily(familyId, headOfFamily, persons);
	}

	@MutationMapping(name = "activateOneFamily")
	public FamilyEntity activateOneFamily(@Argument(name = "familyId") Long familyId) {
		return familyMutationService.activateOneFamily(familyId);
	}

	@MutationMapping(name = "deactivateOneFamily")
	public FamilyEntity deactivateOneFamily(@Argument(name = "familyId") Long familyId) {
		return familyMutationService.deactivateOneFamily(familyId);
	}

}
