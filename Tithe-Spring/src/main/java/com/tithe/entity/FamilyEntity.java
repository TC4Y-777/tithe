/**
 * 
 */
package com.tithe.entity;

import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "family_table", uniqueConstraints = @UniqueConstraint(columnNames = {"familyName", "phone"}))
public class FamilyEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long familyId;
	
	@Nonnull
	private String familyName;
	
//TODO	I am a bit confused. UNDERSTAND
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "address_id")
	private AddressEntity address;
	
	private String phone;
	
	@ManyToOne
	@JoinColumn(name = "koottayma_id")
	private KoottaymaEntity koottayma;
	
	@OneToMany(mappedBy = "family")
	private List<PersonEntity> persons;
	
	@OneToMany(mappedBy = "family")
	private List<TitheEntity> tithes;
	
	private Boolean active = true;

}
