/**
 *
 */
package com.tithe.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Sam T George
 *
 */
@NoArgsConstructor
@Data
public class PersonMutationInput {

	@NotBlank(message = "Baptism Name should not be empty or null")
	String baptismName;

	@NotBlank(message = "Name of Person should not be empty or null")
	String personName;

//	@Positive(message = "Id of Family should be valid")
//	@NotNull(message = "Id of Family should not be empty or null")
	Long familyId;

	@Positive(message = "Id of Relation should be valid")
	@NotNull(message = "Id of Relation should not be empty or null")
	Long relationId;

	@NotNull(message = "Gender should not be empty or null")
	GenderEnum gender;

	@PastOrPresent(message = "Date of Birth should be valid")
	@NotNull(message = "Date of Birth should not be empty or null")
	LocalDate dob;

	String phone;
	List<TitheMutationInput> tithes;
	Boolean moved = false;
	List<Long> educationIds;
	
	OccupationSectorEnum occupationSector;
	
	List<Long> occupationIds;
	Boolean active = true;

}
