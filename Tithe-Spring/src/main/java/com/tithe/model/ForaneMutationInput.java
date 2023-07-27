/**
 * 
 */
package com.tithe.model;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Sam T George
 *
 */
@NoArgsConstructor
@Data
public class ForaneMutationInput {

	@NotBlank(message = "Name of Forane cannot be empty or null")
	String foraneName;
	
	AddressMutationInput address;
	String phone;
	Boolean active = true;
	
}
