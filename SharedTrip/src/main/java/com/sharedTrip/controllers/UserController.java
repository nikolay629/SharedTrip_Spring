package com.sharedTrip.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.sharedTrip.details.CustomUserDetails;
import com.sharedTrip.entities.AppliedTrip;
import com.sharedTrip.entities.Trip;
import com.sharedTrip.entities.User;
import com.sharedTrip.repositories.AppliedTripRepository;
import com.sharedTrip.repositories.TripRepository;
import com.sharedTrip.repositories.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private AppliedTripRepository appliedRepository;

	@GetMapping("/my-trip")
	public String viewUserProfile(@AuthenticationPrincipal CustomUserDetails loggedUser, Model model) {
		User user = userRepository.findByUsername(loggedUser.getUsername());
		model.addAttribute("user", user);

		List<Trip> myTrips = tripRepository.findByUserId(user.getId());
		model.addAttribute("myTrips", myTrips);

		return "user/my_trip/index";
	}

	@GetMapping("/my-trip/applied/{tripId}")
	public String showMyTripApplied(@AuthenticationPrincipal CustomUserDetails loggedUser,
			@PathVariable("tripId") Long id, Model model) {
		Trip trip = tripRepository.findByTripId(id);
		List<AppliedTrip> listApplied = appliedRepository.findAppliedByTripId(id);
		List<AppliedTrip> listConfimred = appliedRepository.findConfirmedByTripId(id);
		boolean isBusy;

		if (listConfimred.stream().count() == trip.getSeats()) {
			isBusy = true;
		} else
			isBusy = false;

		model.addAttribute("isBusy", isBusy);
		model.addAttribute("trip", trip);
		model.addAttribute("listApplied", listApplied);
		model.addAttribute("listConfirmed", listConfimred);

		return "user/my_trip/applied/index";
	}

	@GetMapping("/my-trip/applied/confirm/{appliedTripId}")
	public RedirectView confirmTrip(@PathVariable("appliedTripId") Long id) {
		AppliedTrip appliedTrip = appliedRepository.findWithId(id);
		appliedTrip.setApplied(false);
		appliedTrip.setConfirmed(true);

		appliedRepository.save(appliedTrip);

		return new RedirectView("/user/my-trip/applied/" + appliedTrip.getTrip().getId());
	}

	@GetMapping("/my-trip/refuse/{appliedTripId}")
	public RedirectView refuseTrip(@PathVariable("appliedTripId") Long id) {
		AppliedTrip appliedTrip = appliedRepository.findWithId(id);
		appliedRepository.deleteById(id);

		return new RedirectView("/user/my-trip/applied/" + appliedTrip.getTrip().getId());

	}

}
