package com.sharedTrip.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.sharedTrip.details.CustomUserDetails;
import com.sharedTrip.entities.Trip;
import com.sharedTrip.entities.User;
import com.sharedTrip.repositories.TripRepository;
import com.sharedTrip.repositories.UserRepository;

@Controller
@RequestMapping("/trip")
public class TripController {

	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/create")
	public String createFormTrip(Model model) {
		model.addAttribute("trip", new Trip());

		return "trip/createEdit";
	}

	@GetMapping("/edit/{tripId}")
	public String editFormTrip(@PathVariable("tripId") Long id, Model model) {
		Trip trip = tripRepository.findByTripId(id);
		model.addAttribute("trip", trip);

		return "trip/createEdit";
	}

	@PostMapping("/createEdit")
	public RedirectView createEditTrip(@AuthenticationPrincipal CustomUserDetails loggedUser,
			@ModelAttribute("trip") Trip trip) {
		if (trip.getUser() == null) {
			User user = userRepository.findByUsername(loggedUser.getUsername());
			trip.setUser(user);
		}

		tripRepository.save(trip);

		return new RedirectView("/user/my-trip");
	}

	@RequestMapping(value = "delete/{tripId}", method = RequestMethod.GET)
	public RedirectView deleteTrip(@PathVariable("tripId") Long id) {

		tripRepository.deleteById(id);
		return new RedirectView("/user/my-trip");
	}
}
