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
import jakarta.persistence.Table;
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
@Table(name = "forane_table")
public class ForaneEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long foraneId;
	
	private String foraneName;
	
	@ManyToOne
	@JoinColumn(name = "address_id")
	private AddressEntity address;
	
	private String phone;
	
	@OneToMany(mappedBy = "forane")
	private List<ParishEntity> parishes;
	
	@OneToMany(mappedBy = "forane")
	private List<TitheEntity> tithes;
	
	private Boolean active = true;

}
