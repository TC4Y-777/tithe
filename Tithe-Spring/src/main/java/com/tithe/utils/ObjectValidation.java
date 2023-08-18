/**
 *
 */
package com.tithe.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

/**
 * @author Ashish Sam T George
 *
 */
@Component
public class ObjectValidation {

	@Autowired
	private Validator validator;

	public void validateObject(Object obj) {
		Set<ConstraintViolation<Object>> violations = validator.validate(obj);
		if (!violations.isEmpty()) {
			List<String> errorMessages = new ArrayList<>();
			for (ConstraintViolation<Object> constraintViolation : violations) {
				errorMessages.add(constraintViolation.getMessage());
			}
			throw new ConstraintViolationException("Error Occured: " + errorMessages, violations);
		}
	}

	public void validateObjects(List<?> objs) {
		for (Object obj : objs) {
			validateObject(obj);
		}
	}

}
