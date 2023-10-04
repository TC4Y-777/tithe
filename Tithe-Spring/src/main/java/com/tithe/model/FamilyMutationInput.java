/**
 *
 */
package com.tithe.model;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
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
public class FamilyMutationInput {

	@NotBlank(message = "Name of Family should not be empty or null")
	String familyName;

	AddressMutationInput address;
	String phone;

	@Positive(message = "Id of Koottayma should be valid")
	@NotNull(message = "Id of Koottayma should not be empty or null")
	Long koottaymaId;
	
	@NotNull(message = "Head of Family should not be empty or null")
	PersonMutationInput headOfFamily;
	
	List<PersonMutationInput> persons;

	Boolean active = true;

}
