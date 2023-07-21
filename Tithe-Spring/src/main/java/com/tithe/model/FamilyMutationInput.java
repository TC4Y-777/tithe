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
public class FamilyMutationInput {
	
	@Nonnull
	String familyName;
	
	AddressMutationInput addressMutationInput;
	String phone;
	Long koottaymaId;
	Boolean active = true;

}
