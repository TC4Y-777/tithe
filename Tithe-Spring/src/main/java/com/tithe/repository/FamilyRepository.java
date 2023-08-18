/**
 *
 */
package com.tithe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tithe.entity.FamilyEntity;

/**
 * @author Ashish Sam T George
 *
 */
@Repository
public interface FamilyRepository extends JpaRepository<FamilyEntity, Long> {

	long countByActive(Boolean active);

}
