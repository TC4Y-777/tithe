/**
 * 
 */
package com.tithe.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Sam T George
 *
 */
@NoArgsConstructor
@Data
public class PersonRelationInputModel {
	
	@Positive(message = "Id of Person should be valid")
	@NotNull(message = "Id of Person should not be empty or null")
	private Long personId;
	
	@Positive(message = "Id of Relation should be valid")
	@NotNull(message = "Id of Relation should not be empty or null")
	private Long relationId;

}
