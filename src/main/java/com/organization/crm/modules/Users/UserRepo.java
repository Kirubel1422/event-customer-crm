package com.organization.crm.modules.Users;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<Users, String> {

    Optional<Users> findByEmail(String email) throws UsernameNotFoundException;
}
