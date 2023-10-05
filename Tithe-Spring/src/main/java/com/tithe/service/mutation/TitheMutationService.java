/**
 *
 */
package com.tithe.service.mutation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.PersonEntity;
import com.tithe.entity.TitheBuilder;
import com.tithe.entity.TitheEntity;
import com.tithe.model.TitheMutationInput;
import com.tithe.repository.TitheRepository;
import com.tithe.service.query.PersonQueryService;
import com.tithe.service.query.TitheQueryService;
import com.tithe.utils.ObjectValidation;

import graphql.GraphQLException;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class TitheMutationService {
	
	@Autowired
	private ObjectValidation objectValidation;

	@Autowired
	private PersonQueryService personQueryService;

	@Autowired
	private TitheRepository titheRepository;

	@Autowired
	private TitheQueryService titheQueryService;

	public List<TitheEntity> createManyTithes(Long personId,
			List<TitheMutationInput> titheMutationInputs) {
		
		objectValidation.validateObjects(titheMutationInputs);

		if(personId==null) {
			throw new GraphQLException("Person Id is not valid");
		}
		
		PersonEntity person = personQueryService.getOnePerson(personId);
		if(person==null) {
			throw new GraphQLException("Person does not exist");
		}
		
		TitheBuilder titheBuilder = new TitheBuilder();
		List<TitheEntity> tithes = titheBuilder.buildTithe(person, titheMutationInputs);
		return titheRepository.saveAll(tithes);
	}

}
