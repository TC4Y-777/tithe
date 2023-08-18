/**
 *
 */
package com.tithe.model;

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
public class KoottaymaMutationInput {

	@NotBlank(message = "Name of Koottayma cannot be empty or null")
	String koottaymaName;

	@Positive(message = "Id of Parish should be valid")
	@NotNull(message = "Id of Parish cannot be empty or null")
	Long parishId;

	Boolean active = true;

}
