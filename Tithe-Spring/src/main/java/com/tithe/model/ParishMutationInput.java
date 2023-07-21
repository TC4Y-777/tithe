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
public class ParishMutationInput {

	@Nonnull
	String parishName;
	
	AddressMutationInput addressMutationInput;
	String phone;
	
	@Nonnull
	Long foraneId;
	
	Boolean active = true;
	
}
