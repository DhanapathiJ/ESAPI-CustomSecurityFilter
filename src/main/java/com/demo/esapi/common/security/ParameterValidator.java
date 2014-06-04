package com.demo.esapi.common.security;

import org.owasp.esapi.errors.ValidationException;



public interface ParameterValidator {

	String validateParameterName(String paramName) throws ValidationException;
	String validateParameterValue(String paramName) throws ValidationException;
}
