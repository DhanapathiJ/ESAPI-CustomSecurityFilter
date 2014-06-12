package com.demo.esapi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
	public String gettingStarted(ModelMap model) {
		model.addAttribute("login", new Login());
		return "home";
	}
	
	
	
}
