/**
 * 
 */
package com.tithe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Sam T George
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StreetEntity {
	
	@Id
	private Long streetId;
	
	@Column(unique = true)
	private String streetName;

}
