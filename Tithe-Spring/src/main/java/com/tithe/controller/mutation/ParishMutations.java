	/**
 *
 */
package com.tithe.controller.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.tithe.entity.ForaneEntity;
import com.tithe.entity.ParishEntity;
import com.tithe.model.ParishMutationInput;
import com.tithe.service.mutation.ParishMutationService;

/**
 * @author Ashish Sam T George
 *
 */
@Controller
public class ParishMutations {

	@Autowired
	private ParishMutationService parishMutationService;

	@MutationMapping(name = "createOneParish")
	public ParishEntity createOneParish(@Argument(name = "parish") ParishMutationInput parishMutationInput) {
		return parishMutationService.createOneParish(parishMutationInput);
	}
	
	@MutationMapping(name = "activateOneParish")
	public ParishEntity activateOneParish(@Argument(name = "parishId") Long parishId) {
		return parishMutationService.activateOneParish(parishId);
	}
	
	@MutationMapping(name = "deactivateOneParish")
	public ParishEntity deactivateOneParish(@Argument(name = "parishId") Long parishId) {
		return parishMutationService.deactivateOneParish(parishId);
	}

}
