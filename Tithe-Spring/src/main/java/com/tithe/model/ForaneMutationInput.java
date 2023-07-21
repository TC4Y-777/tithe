/**
 * 
 */
package com.tithe.model;

import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Sam T George
 *
 */
@NoArgsConstructor
@Data
public class ForaneMutationInput {

	@Nonnull
	String foraneName;
	
	AddressMutationInput addressMutationInput;
	String phone;
	Boolean active = true;
	
}
