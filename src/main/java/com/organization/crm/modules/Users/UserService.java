package com.organization.crm.modules.Users;

import com.organization.crm.dtos.users.UpdateReq;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public List<Users> getAllUsers(
            String limitStr,
            String pageStr
    ){
        int limit = Integer.parseInt(limitStr);
        int page = Integer.parseInt(pageStr);
        PageRequest pageable = PageRequest.of(page, limit);

        return userRepo.findAll(pageable).getContent();
    }

    public Map<String, Boolean> deleteUserById(String id) throws BadRequestException {
        Optional<Users> user = userRepo.findById(id);

        if(!user.isPresent()){
            throw new BadRequestException("User doesn't exist");
        }

        userRepo.deleteById(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("success", true);

        return response;
    }

    public Users updateUserById(String id, UpdateReq body) throws BadRequestException {
        Optional<Users> optional = userRepo.findById(id);

        if(!optional.isPresent()) {
            throw new BadRequestException("User not found");
        }

        String newEmail = body.email();
        Optional<Users> previousUser = userRepo.findByEmail(newEmail);

        if(previousUser.isPresent()) {
            throw new BadRequestException("This email address is already in use");
        }

        Users user = optional.get();
        user.setEmail(newEmail);

        return userRepo.save(user);
    }
}
