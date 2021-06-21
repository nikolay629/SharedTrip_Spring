package com.sharedTrip.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sharedTrip.entities.User;
import com.sharedTrip.repositories.UserRepository;

@Controller
public class RegistrationController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/registration")
	public String showSignUpForm(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			model.addAttribute("user", new User());

			return "registration/index";
		}

		return "redirect:/list-trip";

	}

	@PostMapping("/process_registration")
	public String processRegistration(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodePassword = encoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		userRepository.save(user);

		return "redirect:/login";
	}

}
