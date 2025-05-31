package com.organization.crm.modules.Client;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepo extends MongoRepository<Client, String> {
    List<Client> findByEmail(String email);
}
