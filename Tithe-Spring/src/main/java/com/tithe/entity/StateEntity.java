/**
 *
 */
package com.tithe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "state_table")
public class StateEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long stateId;

	@NotBlank(message = "Name of State in empty or null")
	@Column(unique = true)
	private String stateName;

}
