package com.zoommeeting.mindconnectzoommeeting.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zoommeeting.mindconnectzoommeeting.entitites.User;
import com.zoommeeting.mindconnectzoommeeting.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	
	public LoginController(UserService userService) {
		this.userService=userService;
	}
	
	@GetMapping("/register")
	public String showRegister() {
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(@RequestParam String username, @RequestParam String password, Model model) {
		try {
		userService.registerUser(username, password);
		return "redirect:/login";
		}catch(RuntimeException e) {
			model.addAttribute("error", "Username already exists!");
			return "register";
		}
	}
	
	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}
	
	@PostMapping("/login")
	public String loginUser(@RequestParam String username,
	                        @RequestParam String password,
	                        Model model,
	                        HttpSession session) {
	    Optional<User> user = userService.loginUser(username, password);

	    if (user.isPresent()) {
	        session.setAttribute("loggedInUser", user.get()); 
	        return "redirect:/index";
	    } else {
	        model.addAttribute("error", "Invalid username or password!");
	        return "login";
	    }
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
	    session.invalidate(); 
	    return "redirect:/index";
	}

}
