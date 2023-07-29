/**
 * 
 */
package com.tithe.controller.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.tithe.entity.ForaneEntity;
import com.tithe.model.ForaneMutationInput;
import com.tithe.service.mutation.ForaneMutationService;

/**
 * @author Ashish Sam T George
 *
 */
@Controller
public class ForaneMutations {

	@Autowired
	private ForaneMutationService foraneMutationService;
	
	@MutationMapping(name = "createOneForane")
	public ForaneEntity createOneForane(@Argument(name = "forane") ForaneMutationInput foraneMutationInput) {
		return foraneMutationService.createOneForane(foraneMutationInput);
	}
	
}
