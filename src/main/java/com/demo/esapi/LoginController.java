package com.demo.esapi;

import javax.validation.Valid;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class LoginController {
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(ModelMap model,@ModelAttribute("login") @Valid Login login, BindingResult result) {
		Validator validator = ESAPI.validator();
		
		boolean isValidEmail = validator.isValidInput("email", login.getEmail(), "Email", 25, false);
		boolean isValidPassword = validator.isValidInput("password", login.getPassword(), "Password", 25, false);
		if(!isValidEmail ){
			result.rejectValue("email", "Invalid email","Invalid email");
		    return "home";
		}
		if(!isValidPassword ){
			result.rejectValue("password", "Invalid Password","Invalid Password");
		    return "home";
		}
		return "welcome";
	}

}
