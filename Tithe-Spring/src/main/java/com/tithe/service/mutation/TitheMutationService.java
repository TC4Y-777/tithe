/**
 *
 */
package com.tithe.service.mutation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.PersonEntity;
import com.tithe.entity.TitheEntity;
import com.tithe.model.TitheMutationInput;
import com.tithe.repository.TitheRepository;
import com.tithe.service.query.PersonQueryService;
import com.tithe.service.query.TitheQueryService;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class TitheMutationService {

	@Autowired
	private PersonQueryService personQueryService;

	@Autowired
	private TitheRepository titheRepository;

	@Autowired
	private TitheQueryService titheQueryService;

	public List<TitheEntity> createManyTithes(Long personId,
			List<TitheMutationInput> titheMutationInputs) {

//		TODO Try adding @NotNull in the method parameter above - No Use
		PersonEntity person = personQueryService.getOnePerson(personId);
		List<TitheEntity> tithes = titheQueryService.buildTitheEntities(person, titheMutationInputs);
		return titheRepository.saveAll(tithes);
	}

}
