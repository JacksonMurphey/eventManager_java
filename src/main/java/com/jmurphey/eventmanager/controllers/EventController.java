package com.jmurphey.eventmanager.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jmurphey.eventmanager.models.Event;
import com.jmurphey.eventmanager.models.State;
import com.jmurphey.eventmanager.models.User;
import com.jmurphey.eventmanager.services.EventService;
import com.jmurphey.eventmanager.services.UserService;

@Controller
@RequestMapping("/events")
public class EventController {

	@Autowired
	private UserService uService;
	@Autowired
	private EventService eService;
	
	
// Method for getting-checking User in Session
	public Long userInSession(HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return null;
		} else {
			return (Long)session.getAttribute("userId");
		}
	}
	
// Method to reformat our input date
	private String dateNow() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(new Date());
	}
	
	
	
// Get and Post mapping to create event
	@GetMapping("")
	public String index(@ModelAttribute("event")Event event, Model model, HttpSession session) {
		Long userId = userInSession(session);
		if(userId == null) {
			return "redirect:/";
		} else {
			User user = uService.findById(userId);
			model.addAttribute("usersStates", eService.allEventsWithState(user.getState()));
			model.addAttribute("otherStates", eService.allEventsNotState(user.getState()));
			model.addAttribute("user", user);
			model.addAttribute("states", State.States);
			model.addAttribute("now", dateNow());
			return "dashboard.jsp";	
		}
	}
	
	@PostMapping("")
	public String create(@Valid @ModelAttribute("event")Event event, BindingResult result, Model model, 
			HttpSession session, @RequestParam Map<String,String> body) {
		if(result.hasErrors()) {
			Long userId = userInSession(session);
			User user = uService.findById(userId);
			model.addAttribute("usersStates", eService.allEventsWithState(user.getState()));
			model.addAttribute("otherStates", eService.allEventsNotState(user.getState()));
			model.addAttribute("user", user);
			model.addAttribute("states", State.States);
			model.addAttribute("now", dateNow());
			return "dashboard.jsp";
		} else {
			eService.create(event);
			return "redirect:/events";
		}
	}
	
	
// Get Mapping to show 1 event details and Post to leave a comment on said event
	@GetMapping("/{id}")
	public String show(@PathVariable("id")Long id, Model model, HttpSession session) {
		Long userId = userInSession(session);
		Event event = eService.findById(id);
		
		if(userId == null) {
			return "redirect:/";
		}
		
		if(event == null) {
			return "redirect:/events";
		}
		
		model.addAttribute("event", event);
		model.addAttribute("userId", userId);
		return "show.jsp";
	}
	
	@PostMapping("/{id}/comment")
	public String createComment(@PathVariable("id")Long id, @RequestParam("comment")String comment, 
			RedirectAttributes redirect, HttpSession session) {
		Long userId = userInSession(session);
		
		if(userId == null) {
			return "redirect:/";
		}
		
		if(comment.equals("")) {
			redirect.addFlashAttribute("error", "You must provide a Comment");
			return "redirect:/events/" + id;
		}
		
		Event event = eService.findById(id);
		User user = uService.findById(userId);
		eService.comment(user, event, comment);
		return "redirect:/events/" + id;
	}
	

// Get and Put mapping to edit-update an event's details
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable("id")Long id, HttpSession session, Model model) {
		Long userId = userInSession(session);
		Event event = eService.findById(id);
		
		if(userId == null) {
			return "redirect:/";
		}
		
		if(event == null || !event.getPlanner().getId().equals(userId)) {
			return "redirect:/events"; 	
		}
		
		model.addAttribute("event", event);
		model.addAttribute("states", State.States);
		model.addAttribute("userId", userId);
		return "edit.jsp";
	}
	
	@PutMapping("/{id}")
	public String update(@Valid @ModelAttribute("event")Event event, BindingResult result, @PathVariable("id")Long id,
			HttpSession session, Model model) {
		if(result.hasErrors()) {
			Long userId = userInSession(session);
			model.addAttribute("event", event);
			model.addAttribute("states", State.States);
			model.addAttribute("userId", userId);
			return "edit.jsp";
		}
		
		eService.update(event);
		return "redirect:/events";
	}
	
	
// Delete Mapping to Delete event
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id")Long id) {
		eService.delete(id);
		return "redirect:/events";
	}
	
	
// Get Mapping to update relationship of users attending an event or not
	@GetMapping("/{id}/a/{choice}")
	public String manageAttendees(@PathVariable("id")Long id, @PathVariable("choice")String choice, HttpSession session) {
		Long userId = userInSession(session);
		Event event = eService.findById(id);
		boolean isJoining = (choice.equals("join"));
		if(userId == null) {
			return "redirect:/";
		}
		
		User user = uService.findById(userId);
		eService.manageAttendees(event, user, isJoining);
		
		return "redirect:/events";
	}
}
