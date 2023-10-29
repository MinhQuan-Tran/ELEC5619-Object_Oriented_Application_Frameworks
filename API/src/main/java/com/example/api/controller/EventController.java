package com.example.api.controller;

import com.example.api.dto.UserDTO;
import com.example.api.model.Event;
import com.example.api.model.Result;
import com.example.api.service.EventService;
import com.example.api.utils.JWTManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/api") // This means URL's start with /demo (after Application path)
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {

        this.eventService = eventService;
    }

    private boolean checkToken(String token){
        if (token == null || token.isEmpty() || !token.contains("Bearer ")) {
            return false;
        }
        return true;
    }

    @PostMapping(value = "/events", consumes = { "multipart/form-data" })
    public ResponseEntity<Result> createEvent(Event event, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Result result = new Result();
        token = token.replace("Bearer ", "");
        UserDTO userDTO = JWTManager.getDataFromToken(token, "user", UserDTO.class);

        if (userDTO == null) {
            result.setResultFailed(4);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        Event createdEvent = eventService.createEvent(event, userDTO);
        if (createdEvent == null) {
            result.setResultFailed(4);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        result.setResultFailed(0);
        return new ResponseEntity<>(result, HttpStatus.CREATED);

    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }


    @DeleteMapping("/events/{eid}")
    public ResponseEntity<Result> deletePost(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                             @PathVariable Integer eid) {


        token = token.replace("Bearer ", "");
        UserDTO userDTO = JWTManager.getDataFromToken(token, "user", UserDTO.class);
        Result result = new Result();
        if (userDTO == null || !userDTO.getUserType().equals("Admin")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (!eventService.existsById(eid)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean isDeleted = eventService.deleteEvent(eid, userDTO.getUid());
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        result.setResultSuccess(0);
        return new ResponseEntity<>(result,HttpStatus.NO_CONTENT);
    }

}
