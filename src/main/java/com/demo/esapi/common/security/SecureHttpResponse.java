package com.demo.esapi.common.security;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.StringUtilities;

public class SecureHttpResponse extends HttpServletResponseWrapper implements HttpServletResponse {

	public SecureHttpResponse(HttpServletResponse response) {
		super(response);
	}

	private HttpServletResponse getHttpServletResponse(){
		return (HttpServletResponse) super.getResponse();
	}
	
	public void addHeader(String name, String value){
		String strippedName = StringUtilities.stripControls(name);
		String strippedValue = StringUtilities.stripControls(value);
		super.addHeader(strippedName, strippedValue);
	}
	
	public void addIntHeader(String name, int value){
		String strippedName = StringUtilities.stripControls(name);
		super.addIntHeader(strippedName, value);
	}
	
	public void addDateHeader(String name, long date){
		String strippedName = StringUtilities.stripControls(name);
		super.addDateHeader(strippedName, date);
	}
	
	public void addCookie(Cookie cookie){
		String strippedName = StringUtilities.stripControls(cookie.getName());
		String strippedValue = StringUtilities.stripControls(cookie.getValue());
		int maxAge = cookie.getMaxAge();
		String domain = cookie.getDomain();
		String path = cookie.getPath();
		boolean secure = cookie.getSecure();
		String header = createCookieHeader(strippedName, strippedValue, maxAge, domain, path, secure);
	}
	
	private String createCookieHeader(String name, String value,int maxAge, String domain, String path, boolean secure ){
		
		String header = name + "=" + value;
		header+="; Max-Age=" + maxAge;
		if(domain !=null){
			header +=";Domain="+domain;
		}
		if(path !=null){
			path +="; Path=" + path;
		}
		if(secure || ESAPI.securityConfiguration().getForceSecureCookies()){
			header +="; Secure";
		}
		if( ESAPI.securityConfiguration().getForceHttpOnlyCookies()){
			header +="; HttpOnly";
		}
		return header;
		
	}
	
	public void sendRedirect(String location) throws IOException{
		if(!ESAPI.validator().isValidRedirectLocation("Redirect", location, false)){
			throw new IOException("Redirect failed");
		}
	}
	
	public void setHeader(String name, String value){
		String strippedName = StringUtilities.stripControls(name);
		String strippedValue = StringUtilities.stripControls(value);
		getHttpServletResponse().setHeader(strippedName, strippedValue);
	}
	
	public void setIntHeader(String name, int value){
		String strippedName = StringUtilities.stripControls(name);
		getHttpServletResponse().setIntHeader(strippedName, value);
	}
	
	public void setDateHeader(String name, long date){
		String strippedName = StringUtilities.stripControls(name);
		getHttpServletResponse().setDateHeader(strippedName, date);
	}
	
	
}
