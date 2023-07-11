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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"koottaymaName", "parish_id"}))
public class KoottaymaEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long koottaymaId;
	
	private String koottaymaName;
	
	@ManyToOne
	@JoinColumn(name = "parish_id")
	private ParishEntity parish;
	
	@OneToMany(mappedBy = "koottayma")
	private List<FamilyEntity> families;

	private Boolean active = true;
	
}
