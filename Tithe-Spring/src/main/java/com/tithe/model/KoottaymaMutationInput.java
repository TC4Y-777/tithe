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
public class KoottaymaMutationInput {
	
	@Nonnull
	String koottaymaName;

	@Nonnull
	Long parishId;
	
	Boolean active = true;
	
}
