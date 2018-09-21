package org.o7planning.sbsecurity.controller;

import java.security.Principal;

import org.o7planning.sbsecurity.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JacksonInject.Value;

@Controller
public class MainController {
	
	private static final Logger log = LoggerFactory.getLogger(MainController.class);


	@RequestMapping(value = {"/", "welcome"}, method = RequestMethod.GET)
	public String WelcomePage(Model model)
	{
		model.addAttribute("title", "Welcome");
		model.addAttribute("message", "This is Welcome Page");
		log.info("welcome page loaded");
		return "welcomePage";
	}

	@RequestMapping(value={"/login"}, method = RequestMethod.GET)
	public String loginPage(Model model){
		return "loginPage";
	}

	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public String userInfo(Model model, Principal principal){

		String username = principal.getName();
		System.out.println("Username : "+username);

		User loginUser = (User)((Authentication)principal).getPrincipal();

		String userinfo = WebUtils.toString(loginUser);
		model.addAttribute("userInfo", userinfo);
		return "userInfoPage";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal){

		if(principal != null){
			User loginedUser = (User)((Authentication)principal).getPrincipal();
			String userinfo = WebUtils.toString(loginedUser);

			model.addAttribute("userInfo", userinfo);

			String message = "Hi " + principal.getName()//
			+ "<br>You do not have permissions to access this page";
			model.addAttribute("message",message);

		}
		return "403Page";
	}

	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public String adminPage(Model model, Principal principal){

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);

		return "adminPage";
	}

	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
	public String logoutSuccessfulPage(Model model) {
		model.addAttribute("title", "Logout");
		return "logoutSuccessfulPage";
	}
	
	@RequestMapping(value = "/temp", method = RequestMethod.GET)
	public String temporary(Model model){
		return "form";
	}
	
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String secondTemp(Model model){
		return null;
	}




}
