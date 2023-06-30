/**
 * 
 */
package com.tithe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tithe.entity.EducationEntity;

/**
 * @author Ashish Sam T George
 *
 */
@Repository
public interface EducationRepository extends JpaRepository<EducationEntity, Long> {

}
