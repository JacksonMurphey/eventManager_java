package com.jmurphey.eventmanager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmurphey.eventmanager.models.Event;
import com.jmurphey.eventmanager.models.Message;
import com.jmurphey.eventmanager.models.User;
import com.jmurphey.eventmanager.repositories.EventRepository;
import com.jmurphey.eventmanager.repositories.MessageRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository eRepo;
	
	@Autowired
	private MessageRepository mRepo;
	
	
	
// Find by State, and Not State
	public List<Event> allEventsWithState(String state){
		return eRepo.findByState(state);
	}
	
	public List<Event> allEventsNotState(String state){
		return eRepo.findByStateIsNot(state);
	}
	
// Find by ID
	public Event findById(Long id) {
		return eRepo.findById(id).orElse(null);
	}
	
// Create, Update, Delete  Event
	public Event create(Event event) {
		return eRepo.save(event);
	}
	
	public Event update(Event event) {
		return eRepo.save(event);
	}
	
	public void delete(Long id) {
		eRepo.deleteById(id);
	}
	
	
// Method to manage Attendees of Event
	public void manageAttendees(Event event, User user, boolean isJoining) {
		if(isJoining) {
			event.getAttendees().add(user);
		} else {
			event.getAttendees().remove(user);
		}
		eRepo.save(event);
	}
	
	
// Creating-Saving Message -- Linking message to other tables
	public void comment(User user, Event event, String comment) {
		this.mRepo.save(new Message(user, event, comment));
	}
}
