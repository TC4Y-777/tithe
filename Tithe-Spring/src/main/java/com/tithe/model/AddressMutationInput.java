/**
 * 
 */
package com.tithe.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Sam T George
 *
 */
@Data
public class AddressMutationInput extends AddressQueryFilter {
	
	public AddressMutationInput() {
		super();
	}
	
	private String buildingName;
	
}
