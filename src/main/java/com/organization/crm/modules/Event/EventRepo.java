package com.organization.crm.modules.Event;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EventRepo extends MongoRepository<Event, String> {
    Optional<Event> findEventByName(String name);
}
