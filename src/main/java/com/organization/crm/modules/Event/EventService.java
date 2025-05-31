package com.organization.crm.modules.Event;

import com.organization.crm.dtos.events.EventCreateUpdateReq;
import com.organization.crm.modules.Users.CustomUserDetails;
import com.organization.crm.modules.Users.UserRepo;
import com.organization.crm.modules.Users.Users;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepo eventRepo;
    private final UserRepo userRepo;

    public Event createEvent(CustomUserDetails userDetails, EventCreateUpdateReq event) throws BadRequestException {
        // Check if the event is already saved
        String eventName = event.name();

        Optional<Event> prevEvent = eventRepo.findEventByName(eventName);

        if(prevEvent.isPresent()){
            throw new BadRequestException("Event with name " + eventName + " already exists");
        }

        Optional<Users> createdBy = userRepo.findById(userDetails.getUsername());

        if(createdBy.isEmpty()){
            throw new BadRequestException("User " + userDetails.getUsername() + " does not exist");
        }

        Event newEvent = new Event();
        newEvent.setName(eventName);
        newEvent.setCreatedBy(createdBy.get());
        newEvent.setLocation(event.location());

        // Save Event
        return eventRepo.save(newEvent);
    }

    public Event getEventById(String id) throws BadRequestException{
        return eventRepo.findById(id)
                .orElseThrow(() -> new BadRequestException("Event with id " + id + " does not exist"));
    }

    public List<Event> getAllEvents(String page, String limit){
        PageRequest paging = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit));

        return eventRepo.findAll(paging).getContent();
    }

    public Event updateEvent(String id, EventCreateUpdateReq event) throws BadRequestException {
        Optional<Event> prevEvent = eventRepo.findById(id);

        if(prevEvent.isEmpty()){
            throw new BadRequestException("Event with id " + id + " does not exist");
        }

        Event previousEvent = prevEvent.get();

        previousEvent.setName(event.name());
        previousEvent.setLocation(event.location());

        return eventRepo.save(previousEvent);
    }

    public void deleteEvent(String id) throws BadRequestException {
        Optional<Event> prevEvent = eventRepo.findById(id);

        if(prevEvent.isEmpty()){
            throw new BadRequestException("Event with id " + id + " does not exist");
        }

        eventRepo.deleteById(id);
    }
}
