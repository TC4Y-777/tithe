/**
 * 
 */
package com.tithe.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.tithe.entity.EducationEntity;
import com.tithe.entity.FamilyEntity;
import com.tithe.entity.OccupationEntity;
import com.tithe.entity.PersonEntity;
import com.tithe.entity.RelationEntity;
import com.tithe.entity.TitheEntity;
import com.tithe.model.GenderEnum;
import com.tithe.repository.PersonRepository;
import com.tithe.repository.RelationRepository;
import com.tithe.repository.TitheRepository;

/**
 * @author Ashish Sam T George
 *
 */
@Controller
public class PersonController {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private RelationRepository relationRepository;
	
	@Autowired
	private TitheRepository titheRepository;
	
	@QueryMapping
	public PersonEntity getPerson() {
		PersonEntity person = new PersonEntity();
		person.setBaptismName("Kuttaappaayi");
		person.setName("Komalan");
		person.setDob(LocalDate.of(2002, 7, 23));
		person.setPhone("9446823512");
		person.setGender(GenderEnum.F);
		
		RelationEntity relation = new RelationEntity();
		relation.setRelationName("Father");
//		RelationEntity savedRelation = relationRepository.save(relation);
		
		person.setRelation(relation);
		
		TitheEntity tithe = new TitheEntity();
		tithe.setTitheAmount(1000.39);
		tithe.setTimeStamp(LocalDate.now());
		tithe.setPerson(person);
//		TitheEntity savedTithe = titheRepository.save(tithe);
		
		person.setTithe(tithe);
		
		FamilyEntity family = new FamilyEntity();
		family.setFamilyName("Rich Villa");
		family.setPhone("97347834323");
		
		person.setFamily(family);
		
		EducationEntity education = new EducationEntity();
		education.setEducationName("MCA");
		
		person.setEducations(List.of(education));
		
		OccupationEntity occupation = new OccupationEntity();
		occupation.setOccupationName("Engineer");;
		
		person.setOccupations(List.of(occupation));
		
		PersonEntity savedPerson = personRepository.save(person);
		return savedPerson;
	}

}
