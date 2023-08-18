/**
 *
 */
package com.tithe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tithe.entity.RelationEntity;

/**
 * @author Ashish Sam T George
 *
 */
// This annotation is not really necessary.
@Repository
public interface RelationRepository extends JpaRepository<RelationEntity, Long> {

}
