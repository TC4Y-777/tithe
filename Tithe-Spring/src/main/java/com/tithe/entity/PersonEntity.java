/**
 * 
 */
package com.tithe.entity;

import java.time.LocalDate;
import java.util.List;

import com.tithe.model.GenderEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Sam T George
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = 
@UniqueConstraint(columnNames = {"baptismName", 
		"personName", 
		"family_id", 
		"gender", 
		"dob"}))
public class PersonEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long personId;
	
	private String baptismName;
	private String personName;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "family_id")
	private FamilyEntity family;
	
//TODO I am bit confused
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "relation_id")
	private RelationEntity relation;
	
	private GenderEnum gender;
	private LocalDate dob;
//	I think it is better to store phone as string
	private String phone;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "tithe_id")
	private TitheEntity tithe;
	
	private Boolean moved = false;

	@ManyToMany(cascade = CascadeType.PERSIST)
//	I can change the column names of the join table by using attributes "joinColumns" and "inverseJoinColumns"
	@JoinTable(uniqueConstraints = 
	@UniqueConstraint(columnNames = {"persons_person_id", "educations_education_id"}))
	private List<EducationEntity> educations;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(joinColumns = @JoinColumn(name = "person_id"),
	inverseJoinColumns = @JoinColumn(name = "occupation_id"),
	uniqueConstraints = 
	@UniqueConstraint(columnNames = {"person_id", "occupation_id"}))
	private List<OccupationEntity> occupations;
	
	private Boolean active = true;
	
}
