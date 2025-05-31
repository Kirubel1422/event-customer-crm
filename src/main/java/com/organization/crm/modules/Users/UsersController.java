package com.organization.crm.modules.Users;

import com.organization.crm.dtos.users.UpdateReq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Users>> getUsers(
            @RequestParam(
                    name = "limit",
                    required = false,
                    defaultValue = "7"
            ) String limit,
            @RequestParam(
                    name = "page",
                    defaultValue = "0",
                    required = false
            ) String page
    ){
        return ResponseEntity.status(HttpStatus.OK).body(
                userService.getAllUsers(
                    limit,
                    page
                )
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable String id) throws BadRequestException {
        Map<String, Boolean> response = userService.deleteUserById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Users> updateUser(@PathVariable String id, @Valid @RequestBody UpdateReq user) throws BadRequestException {
        return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.updateUserById(id, user));
    }
}
