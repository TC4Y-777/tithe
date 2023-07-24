/**
 * 
 */
package com.tithe.controller.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.tithe.entity.AddressEntity;
import com.tithe.entity.FamilyEntity;
import com.tithe.model.FamilyMutationInput;
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
	public FamilyEntity createOneFamily(@Argument(name = "family") FamilyMutationInput familyMutationInput) {
		if (familyMutationInput.getFamilyName().isBlank()) {
			familyMutationInput.setFamilyName(null);
		}
		return familyMutationService.createOneFamily(familyMutationInput);
	}
	
}
