/**
 *
 */
package com.tithe.service.mutation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.KoottaymaEntity;
import com.tithe.entity.ParishEntity;
import com.tithe.model.KoottaymaMutationInput;
import com.tithe.repository.KoottaymaRepository;
import com.tithe.service.query.ParishQueryService;
import com.tithe.utils.ObjectValidation;

import graphql.GraphQLException;

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

	public KoottaymaEntity activateOneKoottayma(Long koottaymaId) {
		if (koottaymaId!=null) {
			Optional<KoottaymaEntity> obtainedKoottayma = koottaymaRepository.findById(koottaymaId);
			KoottaymaEntity koottayma = obtainedKoottayma.orElseThrow();
			koottayma.setActive(true);
			return koottaymaRepository.save(koottayma);
		}
		GraphQLException exception = new GraphQLException("Some Error Occured") ;
		throw exception;
	}

	public KoottaymaEntity deactivateOneKoottayma(Long koottaymaId) {
		if (koottaymaId!=null) {
			Optional<KoottaymaEntity> obtainedKoottayma = koottaymaRepository.findById(koottaymaId);
			KoottaymaEntity koottayma = obtainedKoottayma.orElseThrow();
			if (koottayma.getFamilies().size()==0) {
				koottayma.setActive(false);
				return koottaymaRepository.save(koottayma);
			}
		}
		GraphQLException exception = new GraphQLException("Some Error Occured") ;
		throw exception;
	}

}
