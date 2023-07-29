/**
 * 
 */
package com.tithe.model;

import jakarta.annotation.Nonnull;
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
public class ParishMutationInput {

	@NotBlank(message = "Name of Parish cannot be empty or null")
	String parishName;
	
	AddressMutationInput address;
	String phone;
	
	@Positive(message = "Id of Forane should be valid")
	@NotNull(message = "Id of Forane cannot be empty or null")
	Long foraneId;
	
	Boolean active = true;
	
}
