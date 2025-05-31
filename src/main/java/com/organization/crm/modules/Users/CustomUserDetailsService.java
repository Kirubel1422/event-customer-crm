package com.organization.crm.modules.Users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
      Optional<Users> user = userRepo.findById(id);

      if(user.isPresent()) {
          return new CustomUserDetails(user.get());
      }

      throw new UsernameNotFoundException(id);
    }
}
