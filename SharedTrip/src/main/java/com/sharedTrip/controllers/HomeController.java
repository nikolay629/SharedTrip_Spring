package com.sharedTrip.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.sharedTrip.details.CustomUserDetails;
import com.sharedTrip.entities.AppliedTrip;
import com.sharedTrip.entities.Search;
import com.sharedTrip.entities.Trip;
import com.sharedTrip.entities.User;
import com.sharedTrip.repositories.AppliedTripRepository;
import com.sharedTrip.repositories.TripRepository;
import com.sharedTrip.repositories.UserRepository;

@Controller
public class HomeController {

	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AppliedTripRepository appliedRepository;

	@GetMapping("/")
	public String home() {
		return "redirect:/search-trip";
	}

	@GetMapping("/search-trip")
	public String showSearchForm(Model model) {
		TreeSet<String> usernames = userRepository.findAllUsername();
		TreeSet<String> cityDepartures = tripRepository.findAllCityDeparture();
		TreeSet<String> cityArrivals = tripRepository.findAllCityArrival();

		model.addAttribute("search", new Search());
		model.addAttribute("usernames", usernames);
		model.addAttribute("cityDepartures", cityDepartures);
		model.addAttribute("cityArrivals", cityArrivals);

		return "home/search/index";
	}

	@GetMapping("/list-trip")
	public String showListWithTrips(@AuthenticationPrincipal CustomUserDetails loggedUser, Search search, Model model) {

		User user = userRepository.findByUsername(loggedUser.getUsername());
		List<Trip> listTrip = getByParam(user.getId(), search);
//		List<AppliedTrip> listApplied = appliedRepository.findAllByUserId(user.getId());
//		List<Trip> deleteTrip = new ArrayList<Trip>();
//		listTrip = 

		model.addAttribute("listTrip", listTrip);

		return "home/list_trip/index";
	}

	@RequestMapping(value = "/list-trip/applied/{tripId}", method = RequestMethod.GET)
	public RedirectView applyTrip(@AuthenticationPrincipal CustomUserDetails loggedUser,
			@PathVariable("tripId") Long id) {
		User user = userRepository.findByUsername(loggedUser.getUsername());
		Trip trip = tripRepository.findByTripId(id);
		AppliedTrip appliedTrip = new AppliedTrip();
		appliedTrip.setUser(user);
		appliedTrip.setTrip(trip);
		appliedTrip.setApplied(true);
		appliedTrip.setConfirmed(false);

		appliedRepository.save(appliedTrip);

		return new RedirectView("/list-trip-applied");
	}

	@GetMapping("/list-trip-applied")
	public String showAppliedTrips(@AuthenticationPrincipal CustomUserDetails loggedUser, Model model) {
		User user = userRepository.findByUsername(loggedUser.getUsername());
		List<AppliedTrip> listAppliedTrips = appliedRepository.findAppliedByUserId(user.getId());

		model.addAttribute("listTrips", listAppliedTrips);
		model.addAttribute("title", "Applied Trips");

		return "user/applied_confirmed_trip/index";
	}

	@GetMapping("/list-trip-confirmed")
	public String showConfirmedTrips(@AuthenticationPrincipal CustomUserDetails loggedUser, Model model) {
		User user = userRepository.findByUsername(loggedUser.getUsername());
		List<AppliedTrip> listConfirmedTrips = appliedRepository.findConfirmedByUserId(user.getId());

		model.addAttribute("listTrips", listConfirmedTrips);
		model.addAttribute("title", "Confirmed Trips");

		return "user/applied_confirmed_trip/index";
	}

	@GetMapping("/list-trip-applied-confirmed/refuse/{tripId}")
	public RedirectView refuseAppliedTrip(@PathVariable("tripId") Long id) {
		appliedRepository.deleteById(id);
		return new RedirectView("/search-trip");
	}

	private List<Trip> getByParam(Long userId, Search search) {
		List<Trip> listTrip = tripRepository.findAllWithOutThisUser(userId);
		List<Trip> deleteTrip = new ArrayList<Trip>();
		Set<Long> tripIds = appliedRepository.findByTrip();

		tripIds.forEach(id -> listTrip.remove(tripRepository.findById(id)));

		if (!search.getUsername().equalsIgnoreCase("all")) {
			deleteTrip.addAll(tripRepository.findByUsename(search.getUsername()));
		}
		if (!search.getCityDeparture().equalsIgnoreCase("all")) {
			deleteTrip.addAll(tripRepository.findByCityDeparture(search.getCityDeparture()));
		}
		if (!search.getCityArrival().equalsIgnoreCase("all")) {
			deleteTrip.addAll(tripRepository.findByCityArrival(search.getCityArrival()));
		}
		if (search.getHourDeparture() != null) {
			deleteTrip.addAll(tripRepository.findByHourDeparture(search.getHourDeparture()));
		}
		if (search.getHourArrival() != null) {
			deleteTrip.addAll(tripRepository.findByHourArrival(search.getHourArrival()));
		}

		listTrip.removeAll(deleteTrip);
		return listTrip;
	}
}
