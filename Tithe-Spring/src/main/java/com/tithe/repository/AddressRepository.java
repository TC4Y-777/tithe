/**
 *
 */
package com.tithe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tithe.entity.AddressEntity;

/**
 * @author Ashish Sam T George
 *
 */
@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

}
