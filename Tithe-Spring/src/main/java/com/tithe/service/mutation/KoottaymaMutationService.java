/**
 * 
 */
package com.tithe.service.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.KoottaymaEntity;
import com.tithe.model.KoottaymaMutationInput;
import com.tithe.repository.KoottaymaRepository;
import com.tithe.service.query.ParishQueryService;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class KoottaymaMutationService {
	
	@Autowired
	private KoottaymaRepository koottaymaRepository;
	
	@Autowired
	private ParishQueryService parishQueryService;

	public KoottaymaEntity createOneKoottayma(KoottaymaMutationInput koottaymaMutationInput) {
		KoottaymaEntity koottayma = new KoottaymaEntity();
		koottayma.setKoottaymaName(koottaymaMutationInput.getKoottaymaName());
		
		if (koottaymaMutationInput.getParishId()!=null) {
			koottayma.setParish(parishQueryService.getOneParish(koottaymaMutationInput.getParishId()));
		}
		
		koottayma.setActive(koottaymaMutationInput.getActive());
		
		return koottaymaRepository.save(koottayma);
	}

}
