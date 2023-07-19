/**
 * 
 */
package com.tithe.controller.mutation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.tithe.entity.EducationEntity;
import com.tithe.entity.PersonEntity;
import com.tithe.model.PersonMutationInput;
import com.tithe.service.mutation.EducationMutationService;
import com.tithe.service.mutation.PersonMutationService;

/**
 * @author Ashish Sam T George
 *
 */
@Controller
public class PersonMutations {
	
	@Autowired
	private PersonMutationService personMutationService;
	
	@Autowired
	private EducationMutationService educationMutationService;

	@MutationMapping(name = "createOnePerson")
	public PersonEntity createOnePerson(@Argument PersonMutationInput personMutationInput) {
		return personMutationService.createOnePerson(personMutationInput);
	}
	
	@MutationMapping(name = "createManyEducations")
	public List<EducationEntity> createManyEducations(List<String> educationNames) {
		return educationMutationService.createManyEducations(educationNames);
	}
	
}
