package com.organization.crm.modules.Event;

import com.organization.crm.dtos.events.EventCreateUpdateReq;
import com.organization.crm.modules.Users.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Event> createEvent(
            @Valid @RequestBody EventCreateUpdateReq event,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws BadRequestException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventService.createEvent(userDetails, event));
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(
            @RequestParam(
                    defaultValue = "7"
            ) String limit,
            @RequestParam(
                    defaultValue = "0"
            ) String page
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventService.getAllEvents(page, limit));
    }

    @GetMapping("{id}")
    public ResponseEntity<Event> getEventById(@PathVariable String id) throws BadRequestException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventService.getEventById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Event> updateEvent(@Valid @RequestBody EventCreateUpdateReq event, @PathVariable String id) throws BadRequestException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventService.updateEvent(id, event));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEvent(@PathVariable String id) throws BadRequestException {
        eventService.deleteEvent(id);
        Map<String, Boolean> resp = new HashMap<>();
        resp.put("success", true);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resp);
    }
}
