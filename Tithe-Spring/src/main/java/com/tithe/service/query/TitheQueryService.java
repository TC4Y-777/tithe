/**
 *
 */
package com.tithe.service.query;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.PersonEntity;
import com.tithe.entity.TitheEntity;
import com.tithe.model.TitheMutationInput;
import com.tithe.utils.ObjectValidation;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class TitheQueryService {

	@Autowired
	private ObjectValidation objectValidation;

	public List<TitheEntity> buildTitheEntities(PersonEntity person,
			List<TitheMutationInput> titheMutationInputs) {

		objectValidation.validateObjects(titheMutationInputs);

		List<TitheEntity> tithes = new ArrayList<>();
		for (TitheMutationInput titheInput : titheMutationInputs) {
			TitheEntity tithe = new TitheEntity();
			tithe.setTitheAmount(titheInput.getTitheAmount());
			tithe.setTimeStamp(titheInput.getTimeStamp());
			tithe.setPerson(person);
			tithe.setFamily(person.getFamily());
			tithe.setKoottayma(tithe.getFamily().getKoottayma());
			tithe.setParish(tithe.getKoottayma().getParish());
			tithe.setForane(tithe.getParish().getForane());

			tithes.add(tithe);
		}

		return tithes;

	}

}
