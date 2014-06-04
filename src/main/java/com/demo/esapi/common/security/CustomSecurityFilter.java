package com.demo.esapi.common.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
@Component
public class CustomSecurityFilter extends GenericFilterBean implements Filter {
	
    @Autowired
	protected ParameterValidator parameterValidator;
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if(!(request instanceof HttpServletRequest)){
			chain.doFilter(request, response);
			return;
		}
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse= (HttpServletResponse) response;
			
			SecureHttpRequest secureRequest = buiildSecureRequest( httpRequest);
			ESAPI.httpUtilities().setCurrentHTTP(httpRequest, httpResponse);
			chain.doFilter(secureRequest, httpResponse);
		} catch (Exception e) {
			
			request.setAttribute("esapi.message", e.getMessage());
		}finally{
			ESAPI.httpUtilities().clearCurrent();
		}
	}

	protected SecureHttpRequest buiildSecureRequest(HttpServletRequest httpRequest ){
		return new SecureHttpRequest(httpRequest,parameterValidator);
	}
	
	protected SecureHttpResponse buiildSecureResponse(HttpServletResponse httpResponse ){
		return new SecureHttpResponse(httpResponse);
	}
}
