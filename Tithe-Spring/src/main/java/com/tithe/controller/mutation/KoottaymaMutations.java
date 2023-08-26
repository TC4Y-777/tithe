/**
 *
 */
package com.tithe.controller.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.tithe.entity.KoottaymaEntity;
import com.tithe.entity.ParishEntity;
import com.tithe.model.KoottaymaMutationInput;
import com.tithe.service.mutation.KoottaymaMutationService;

/**
 * @author Ashish Sam T George
 *
 */
@Controller
public class KoottaymaMutations {

	@Autowired
	private KoottaymaMutationService koottaymaMutationService;

	@MutationMapping(name = "createOneKoottayma")
	public KoottaymaEntity createOneKoottayma(@Argument(name = "koottayma") KoottaymaMutationInput koottaymaMutationInput) {
		return koottaymaMutationService.createOneKoottayma(koottaymaMutationInput);
	}
	
	@MutationMapping(name = "activateOneKoottayma")
	public KoottaymaEntity activateOneKoottayma(@Argument(name = "koottaymaId") Long koottaymaId) {
		return koottaymaMutationService.activateOneKoottayma(koottaymaId);
	}
	
	@MutationMapping(name = "deactivateOneKoottayma")
	public KoottaymaEntity deactivateOneKoottayma(@Argument(name = "koottaymaId") Long koottaymaId) {
		return koottaymaMutationService.deactivateOneKoottayma(koottaymaId);
	}

}
