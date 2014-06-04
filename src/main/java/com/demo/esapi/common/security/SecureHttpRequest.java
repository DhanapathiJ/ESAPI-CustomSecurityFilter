package com.demo.esapi.common.security;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.ValidationException;

public class SecureHttpRequest extends HttpServletRequestWrapper implements
		HttpServletRequest {
	private final static Logger LOGGER = Logger.getLogger(SecureHttpRequest.class.getName());
	private static final int MAX_CONTEXT_PATH = 300;
	private static final int MAX_HTTP_URI = 2000;
	private String allowableContentRoot="/WEB-INF";
	private ParameterValidator parameterValidator;

	public SecureHttpRequest(HttpServletRequest request,
			ParameterValidator parameterValidator) {
		super(request);
		this.parameterValidator = parameterValidator;
	}

	private HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) super.getRequest();
	}

	public String getContextPath() {
		String path = getHttpServletRequest().getContextPath();
		if (path == null || "".equals(path.trim()))
			return "";
		String clean = "";
		try {
			clean = ESAPI.validator().getValidInput(
					"HTTP context path: " + path, path, "HTTPContextPath",
					MAX_CONTEXT_PATH, false);
			LOGGER.debug("ESAPI cleaned ContextPath:"+clean);
		} catch (ValidationException e) {
			LOGGER.error("ValidationException in HTTP context path ",e);
		}

		return clean;
	}

	public String getParameter(String name) {

		String orig = getHttpServletRequest().getParameter(name);
		String clean = "";

		try {
			clean = parameterValidator.validateParameterValue(orig);
		} catch (ValidationException e) {

		}

		return clean;
	}

	public Map getParameterMap() {
		@SuppressWarnings("unchecked")
		Map<String, String[]> map = getHttpServletRequest().getParameterMap();
		Map<String, String[]> cleanMap = new HashMap<String, String[]>();
		for (Map.Entry<String, String[]> entry : map.entrySet()) {

			try {
				String name = entry.getKey();
				String cleanName = parameterValidator
						.validateParameterName(name);
				String[] value = entry.getValue();
				String[] cleanValues = new String[value.length];
				for (int j = 0; j < value.length; j++) {
					cleanValues[j] = parameterValidator
							.validateParameterValue(value[j]);
				}
				cleanMap.put(cleanName, cleanValues);
			} catch (ValidationException e) {

			}
		}
		return cleanMap;
	}

	public Enumeration getParameterNames() {
		Vector<String> v = new Vector<String>();
		Enumeration en = getHttpServletRequest().getParameterNames();
		while (en.hasMoreElements()) {
			try {
				String name = (String) en.nextElement();
				String clean = parameterValidator.validateParameterName(name);
				v.add(clean);
			} catch (ValidationException e) {

			}
		}
		return v.elements();
	}
	
	public String[] getParameterValues(String name){
		String[] values = getHttpServletRequest().getParameterValues(name);
		List<String> newValues;
		
		if(values ==null)
			return null;
		newValues = new ArrayList<String>();
		for (String value : values) {
			try{
				String cleanValues = parameterValidator.validateParameterValue(value);
				newValues.add(cleanValues);
			}catch (ValidationException e) {
				
			}
		}
		return newValues.toArray(new String[newValues.size()]);
	}
	
	public String getPathInfo(){
		String path = getHttpServletRequest().getPathInfo();
		if(path == null) return null;
		String clean ="";
		try {
			clean =ESAPI.validator().getValidInput("HTTP path" + path, path, "HTTPPath", 150, true);
		}catch (ValidationException e) {
			
		}
		return clean;
	}
	
	public String getQueryString(){
		String query = getHttpServletRequest().getQueryString();
		if(query == null) return null;
		String clean ="";
		try {
			clean =ESAPI.validator().getValidInput("HTTP query string" + query, query, "HTTPQueryString", 2000, true);
		}catch (ValidationException e) {
			
		}
		return clean;
	}
	
	public RequestDispatcher getRequestDispatcher(String path){
		if(path.startsWith(allowableContentRoot)){
			return getHttpServletRequest().getRequestDispatcher(path);
		}
		return null;
	}
	
	public String getRequestURI(){
		String uri = getHttpServletRequest().getRequestURI();
		String clean ="";
		try {
			clean = ESAPI.validator().getValidInput("HTTP URI: " + uri, uri, "HTTPURI", MAX_HTTP_URI, false);
		} catch (ValidationException e) {
			
		} 
		return clean;
	}
	
	public StringBuffer getRequestURL(){
		String url = getHttpServletRequest().getRequestURL().toString();
		String clean ="";
		try {
			clean = ESAPI.validator().getValidInput("HTTP URL: " + url, url, "HTTPURL", MAX_HTTP_URI, false);
		} catch (ValidationException e) {
			
		} 
		return new StringBuffer(clean);
	}

	public String getScheme(){
		String scheme = getHttpServletRequest().getScheme();
		String clean ="";
		try {
			clean = ESAPI.validator().getValidInput("HTTP scheme: " + scheme, scheme, "HTTPScheme", 10, false);
		} catch (ValidationException e) {
			
		} 
		return clean;
	}
	
	public String getServerName(){
		String name = getHttpServletRequest().getServerName();
		String clean ="";
		try {
			clean = ESAPI.validator().getValidInput("HTTP server name: " + name, name, "HTTPServerName", 200, false);
		} catch (ValidationException e) {
			
		} 
		return clean;
	}
	
	public int getServerPort(){
		int port = getHttpServletRequest().getServerPort();
		if(port < 0 || port > 0xFFFF){
			port = 0;
		}
		return port;
	}
	
	public String getServletPath(){
		String path = getHttpServletRequest().getServletPath();
		String clean ="";
		try {
			clean = ESAPI.validator().getValidInput("HTTP servlet path: " + path, path, "HTTPServletPath", 300, false);
		} catch (ValidationException e) {
			
		} 
		return clean;
	}
	
	public String getAllowableContentRoot() {
		return allowableContentRoot;
	}

	public void setAllowableContentRoot(String allowableContentRoot) {
		this.allowableContentRoot = allowableContentRoot.startsWith("/") ? allowableContentRoot: "/" + allowableContentRoot;
	}
	
	
}
