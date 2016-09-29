package com.in28minutes.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.in28minutes.login.LoginService;

@Controller
@SessionAttributes({"password","name"})
public class LoginController {
	
	@Autowired
	LoginService service;

	/*@RequestMapping(value ="/login", method = RequestMethod.GET) 
	public String showLoginPage(){
		return "login"; 
	}*/
	
	/*@RequestMapping(value ="/login", method = RequestMethod.POST) 
	public String handleLoginRequest(@RequestParam String name, @RequestParam String password, ModelMap model){
		
		if(!service.validateUser(name, password)){
			model.put("errorMessage", "Login Fail!");
			return "login";
		}
		
		model.addAttribute("name", name);
		model.addAttribute("password", password);
//		System.out.println(name);
//		System.out.println(password);
		return "welcome"; 
	}*/
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String showWelcomePage(ModelMap model) {
        model.put("name", "in28Minutes");
        return "welcome";
    }
}