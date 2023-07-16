/**
 * 
 */
package com.tithe.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class ParishEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long parishId;
	
	private String parishName;
	
	@ManyToOne
	@JoinColumn(name = "address_id")
	private AddressEntity address;
	
	private String phone;
	
	@ManyToOne
	@JoinColumn(name = "forane_id")
	private ForaneEntity forane;
	
	@OneToMany(mappedBy = "parish")
	private List<KoottaymaEntity> koottaymas;
	
	private Boolean active = true;

}
