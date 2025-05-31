package com.organization.crm.modules.Client;

import com.organization.crm.dtos.client.ClientRegister;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/register")
public class ClientController {
    private final ClientService clientService;

    // Fetch a Registration by ID
    @GetMapping("{id}")
    public ResponseEntity<Client> getClientRegistrationById(
            @PathVariable
            String id
    ) throws BadRequestException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientService.getClientRegisterationById(id));
    }

    // Fetch Registrations with client email
    // Retrieves any Registration regardless of the event
    @GetMapping(params="email")
    public ResponseEntity<List<Client>> getClientRegistrationsByEmail(
            @RequestParam(
                    required = true
            ) @Email(
                    message = "Invalid email."
            ) String email
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientService.getClientRegistrationsByEmail(email));
    }

    // Register a client registration
    @PostMapping
    public ResponseEntity<Map<String, Boolean>> register(
            @Valid @RequestBody ClientRegister data
    ) throws BadRequestException {
        clientService.register(data);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "success", true
                ));
    }

    // Delete a registration by id
    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Boolean>> deleteRegistration(
            @PathVariable String id
    ) throws BadRequestException {
        clientService.deleteClientRegistrationById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of(
                        "success", true
                ));
    }

    // Update a registration by id
    @PutMapping("{id}")
    public ResponseEntity<Client> updateClientRegistration(
            @PathVariable String id,
            @Valid @RequestBody ClientRegister data
    ) throws BadRequestException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientService.updateClientRegistrations(id, data));
    }

    // Get all Registrations
        @GetMapping
        public ResponseEntity<List<Client>> getAllClients(
                @RequestParam(
                        defaultValue = "0"
                ) String page,
                @RequestParam(
                        defaultValue = "7"
                ) String limit){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientService.getAllClientRegistrations(page, limit));
    }
}
