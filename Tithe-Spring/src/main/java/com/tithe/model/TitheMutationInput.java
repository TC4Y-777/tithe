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
@NoArgsConstructor
@Data
public class TitheMutationInput {
	
	Double titheAmount;
	String timeStamp;

}
