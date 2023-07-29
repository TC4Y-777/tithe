/**
 * 
 */
package com.tithe.service.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.tithe.entity.KoottaymaEntity;
import com.tithe.model.KoottaymaMutationInput;
import com.tithe.repository.KoottaymaRepository;
import com.tithe.service.query.ParishQueryService;
import com.tithe.utils.ObjectValidation;

import jakarta.validation.Valid;
import jakarta.validation.Validator;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class KoottaymaMutationService {
	
	@Autowired
	private ObjectValidation objectValidation;
	
	@Autowired
	private KoottaymaRepository koottaymaRepository;
	
	@Autowired
	private ParishQueryService parishQueryService;

	public KoottaymaEntity createOneKoottayma(KoottaymaMutationInput koottaymaMutationInput) {
		
		objectValidation.validateObject(koottaymaMutationInput);
		
		KoottaymaEntity koottayma = new KoottaymaEntity();
		koottayma.setKoottaymaName(koottaymaMutationInput.getKoottaymaName());
		koottayma.setParish(parishQueryService.getOneParish(koottaymaMutationInput.getParishId()));
		koottayma.setActive(koottaymaMutationInput.getActive());
		
		return koottaymaRepository.save(koottayma);
	}

}
