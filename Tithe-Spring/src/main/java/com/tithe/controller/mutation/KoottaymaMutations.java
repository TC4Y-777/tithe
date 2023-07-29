/**
 * 
 */
package com.tithe.controller.mutation;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tithe.entity.KoottaymaEntity;
import com.tithe.model.KoottaymaMutationInput;
import com.tithe.service.mutation.KoottaymaMutationService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;

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
	
}
