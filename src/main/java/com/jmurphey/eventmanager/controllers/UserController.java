package com.jmurphey.eventmanager.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jmurphey.eventmanager.models.User;
import com.jmurphey.eventmanager.models.State;
import com.jmurphey.eventmanager.services.UserService;
import com.jmurphey.eventmanager.validators.UserValidator;



@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	private UserService uService;
	
	@Autowired
	private UserValidator validator;
	
	
// Login and Registration
	@GetMapping("")
	public String index(@ModelAttribute("user") User user, Model model) {
		model.addAttribute("states", State.States);
		return "loginRegistration.jsp";
	}
	
	@PostMapping("")
	public String register(@Valid @ModelAttribute("user")User user, BindingResult result, HttpSession session) {
		validator.validate(user, result);
		if(result.hasErrors()) {
			return "loginRegistration.jsp";
		} else {
			User newUser = uService.register(user);
			session.setAttribute("userId", newUser.getId());
			return "redirect:/events";
		}
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("email")String email, @RequestParam("password")String password, HttpSession session, RedirectAttributes redirect) {
		if(uService.authenticate(email, password)) {
			User user = uService.getUserByEmail(email);
			session.setAttribute("userId", user.getId());
			return "redirect:/events";
		} else {
			redirect.addFlashAttribute("error", "Invalid Email or Password");
			return "redirect:/";
		}
	}
	
	
// --- Logout User ---
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
