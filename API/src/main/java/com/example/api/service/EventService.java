package com.example.api.service;

import com.example.api.dto.EventDTO;
import com.example.api.dto.PostDTO;
import com.example.api.dto.UserDTO;
import com.example.api.model.Event;
import com.example.api.model.Post;
import com.example.api.model.User;
import com.example.api.repository.CommentRepository;
import com.example.api.repository.EventRepository;
import com.example.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    @Autowired
    public EventService(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event, UserDTO userDTO){
        Integer uid = userDTO.getUid();
        User user = userRepository.findById(uid).orElse(null);
        if(!user.getUserType().equals("Admin")){
            return null;
        }
        eventRepository.save(event);
        return event;
    }

    public boolean deleteEvent(Integer eid, Integer uid) {
        Optional<Event> event = eventRepository.findById(eid);
        if (event.isPresent() && event.get().getUid().equals(uid)) {
            eventRepository.deleteById(eid);
            return true;
        }
        return false;
    }

    public boolean existsById(Integer eid) {
        return eventRepository.existsById(eid);
    }


    public List<Event> getAllEvents() {
        List<Event> Events = eventRepository.findAll();
        return Events;
    }
}
