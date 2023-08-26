/**
 *
 */
package com.tithe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tithe.entity.OccupationEntity;

/**
 * @author Ashish Sam T George
 *
 */
@Repository
public interface OccupationRepository extends JpaRepository<OccupationEntity, Long> {

}
