/**
 * 
 */
package com.tithe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tithe.entity.KoottaymaEntity;

/**
 * @author Ashish Sam T George
 *
 */
@Repository
public interface KoottaymaRepository extends JpaRepository<KoottaymaEntity, Long> {

}
