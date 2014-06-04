package com.demo.esapi.common.security;

import org.apache.log4j.Logger;
import org.owasp.esapi.Validator;
import org.owasp.esapi.errors.ValidationException;

public class EsapiParameterValidator implements ParameterValidator {
	private static final int MAX_PARAM_NAME = 200;
	private static final int MAX_PARAM_VALUE = 1<<15;
	private Validator esapiValidator;
	private final static Logger LOGGER = Logger.getLogger(EsapiParameterValidator.class.getName());
	
	public EsapiParameterValidator(Validator esapiValidator){
		this.esapiValidator =esapiValidator;
	}
	
	
	public String validateParameterName(String paramName) throws ValidationException {
		String clean = esapiValidator.getValidInput("HTTPParameterName: "+ paramName, paramName, "HTTPParameterName", MAX_PARAM_NAME, true);
		LOGGER.debug("ESPI Cleaned HTTPParameterName  "+clean);
		return clean;
	}

	
	public String validateParameterValue(String paramValue)throws ValidationException {
		String clean = esapiValidator.getValidInput("HTTPParameterValue: "+ paramValue, paramValue, "HTTPParameterValue", MAX_PARAM_VALUE, true);
		LOGGER.debug("ESPI Cleaned HTTPParameterValue  "+clean);
		return clean;
	}

}
