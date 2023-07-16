/**
 * 
 */
package com.tithe.service.mutation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.PersonEntity;
import com.tithe.entity.TitheEntity;
import com.tithe.model.PersonMutationInput;
import com.tithe.model.TitheMutationInput;
import com.tithe.repository.PersonRepository;
import com.tithe.repository.TitheRepository;
import com.tithe.service.query.FamilyQueryService;
import com.tithe.service.query.RelationQueryService;

/**
 * @author Ashish Sam T George
 *
 */
@Service
public class PersonMutationService {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private TitheRepository titheRepository;

	@Autowired
	private FamilyQueryService familyQueryService;
	
	@Autowired
	private RelationQueryService relationQueryService;
	
	public PersonEntity createOnePerson(PersonMutationInput personMutationInput) {
		PersonEntity person = new PersonEntity();
		person.setBaptismName(personMutationInput.getBaptismName());
		person.setPersonName(personMutationInput.getPersonName());
		
		if (personMutationInput.getFamilyId()!=null) {
			person.setFamily(familyQueryService.getOneFamily(personMutationInput.getFamilyId()));
		}
		if (personMutationInput.getRelationId()!=null) {
			person.setRelation(relationQueryService.getOneRelation(personMutationInput.getRelationId()));
		}
		
		person.setGender(personMutationInput.getGender());
		person.setDob(LocalDate.parse(personMutationInput.getDob()));
		person.setPhone(personMutationInput.getPhone());
		
		List<TitheMutationInput> titheInputs = personMutationInput.getTithes();
		List<TitheEntity> tithes = new ArrayList<>();
		if (tithes.size()!=0) {
			for (TitheMutationInput titheInput : titheInputs) {
				TitheEntity tithe = new TitheEntity();
				tithe.setTitheAmount(titheInput.getTitheAmount());
				tithe.setTimeStamp(LocalDate.parse(titheInput.getTimeStamp()));
				tithe.setPerson(person);
				tithe.setFamily(person.getFamily());
				tithe.setKoottayma(tithe.getFamily().getKoottayma());
				tithe.setParish(tithe.getKoottayma().getParish());
				tithe.setForane(tithe.getParish().getForane());
				
				tithes.add(tithe);
			}
			List<TitheEntity> savedTithes = titheRepository.saveAll(tithes);
//			Find latest tithe
		}
		
		person.setMoved(personMutationInput.getMoved());
		
		
		return null;
	}

}
