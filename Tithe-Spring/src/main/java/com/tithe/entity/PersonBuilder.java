/**
 * 
 */
package com.tithe.entity;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tithe.model.GenderEnum;
import com.tithe.utils.ObjectValidation;


/**
 * @author Ashish Sam T George
 *
 */
public class PersonBuilder {

	private Long personId;
	private String baptismName;
	private String personName;
	private FamilyEntity family;
	private RelationEntity relation;
	private GenderEnum gender;
	private LocalDate dob;
	private String phone;
	private List<TitheEntity> tithes;
	private Boolean moved = false;
	private List<EducationEntity> educations;
	private List<OccupationEntity> occupations;
	private Boolean active = true;

	public PersonBuilder personId(Long personId) {
		this.personId = personId;
		return this;
	}

	public PersonBuilder baptismName(String baptismName) {
		this.baptismName = baptismName;
		return this;
	}

	public PersonBuilder personName(String personName) {
		this.personName = personName;
		return this;
	}

	public PersonBuilder family(FamilyEntity family) {
		this.family = family;
		return this;
	}

	public PersonBuilder relation(RelationEntity relation) {
		this.relation = relation;
		return this;
	}

	public PersonBuilder gender(GenderEnum gender) {
		this.gender = gender;
		return this;
	}

	public PersonBuilder dob(LocalDate dob) {
		this.dob = dob;
		return this;
	}

	public PersonBuilder phone(String phone) {
		this.phone = phone;
		return this;
	}

//	public PersonBuilder tithes(List<TitheEntity> tithes) {
//		this.tithes = tithes;
//		return this;
//	}

	public PersonBuilder moved(Boolean moved) {
		this.moved = moved;
		return this;
	}

	public PersonBuilder educations(List<EducationEntity> educations) {
		this.educations = educations;
		return this;
	}

	public PersonBuilder occupations(List<OccupationEntity> occupations) {
		this.occupations = occupations;
		return this;
	}

	public PersonBuilder active(Boolean active) {
		this.active = active;
		return this;
	}

	public PersonEntity build() {
		PersonEntity person = new PersonEntity(personId, baptismName, personName, family, relation, gender, dob, phone,
				tithes, moved, educations, occupations, active);
		return person;
	}

}
