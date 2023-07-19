/**
 * 
 */
package com.tithe.controller.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.tithe.entity.PersonEntity;
import com.tithe.model.PersonMutationInput;
import com.tithe.service.mutation.PersonMutationService;

/**
 * @author Ashish Sam T George
 *
 */
@Controller
public class PersonMutations {
	
	@Autowired
	private PersonMutationService personMutationService;

	@MutationMapping(name = "createOnePerson")
	public PersonEntity createOnePerson(@Argument PersonMutationInput personMutationInput) {
		return personMutationService.createOnePerson(personMutationInput);
	}
	
	
	
}
