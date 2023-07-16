/**
 * 
 */
package com.tithe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tithe.entity.PersonEntity;

/**
 * @author Ashish Sam T George
 *
 */
@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

}
