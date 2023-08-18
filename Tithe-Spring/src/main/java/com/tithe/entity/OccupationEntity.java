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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "occupation_table")
public class OccupationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long occupationId;

	@NotBlank(message = "Name of Occupation is either empty or null")
	@Column(unique = true)
	private String occupationName;

	@ManyToMany(mappedBy = "occupations")
	private List<PersonEntity> persons;

}
