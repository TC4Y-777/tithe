/**
 *
 */
package com.tithe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "pincode_table")
public class PincodeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pincodeId;

	@NotBlank(message = "Pincode is empty or null")
	@Column(unique = true)
	private String pincode;

}
