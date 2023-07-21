/**
 * 
 */
package com.tithe.entity;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "person_moved_table")
public class PersonMoved {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long familyMovedId;
	
	@OneToOne
	private PersonEntity person;
	
	@OneToOne
	private FamilyEntity oldFamily;
	
	@OneToOne
	private FamilyEntity newFamily;
	
	private Date timeStamp;

}
