/**
 * 
 */
package com.tithe.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class EducationEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long educationId;
	
	@Column(unique = true)
	private String educationName;
	
	@ManyToMany(mappedBy = "educations")
	private List<PersonEntity> persons;

}
