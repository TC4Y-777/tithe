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
@Table(name = "relation_table")
public class RelationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long relationId;

	@NotBlank(message = "Name of Relation is either empty or null")
	@Column(unique = true)
	private String relationName;

}
