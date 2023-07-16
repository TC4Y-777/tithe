/**
 * 
 */
package com.tithe.model;

import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import com.tithe.entity.TitheEntity;

import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Sam T George
 *
 */
@NoArgsConstructor
@Data
public class PersonMutationInput {
	
	@Nonnull
	String baptismName;
	
	@Nonnull
	String personName;
	
	@Nonnull
	Long familyId;
	
	@Nonnull
	Long relationId;
	
	@Nonnull
	GenderEnum gender;
	
	String dob;
	String phone;
	List<TitheMutationInput> tithes;
	Boolean moved = false;
	Long[] educationIds;
	Long[] occupationIds;
	Boolean active = true;

}
