/**
 * 
 */
package com.tithe.model;

import java.time.LocalDate;

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
public class TitheMutationInput {
	
	@Positive(message = "Tithe Amount should be valid")
	@NotNull(message = "Tithe Amount should not be emtpy or null")
	Double titheAmount;
	
	@NotNull(message = "Timestamp should not be empty or null")
	LocalDate timeStamp;

}
