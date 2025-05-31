package com.organization.crm.modules.Client;

import com.organization.crm.dtos.client.ClientRegister;
import com.organization.crm.modules.Event.Event;
import com.organization.crm.modules.Event.EventService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepo clientRepo;
    private final EventService eventService;

    // POST
    public void register(ClientRegister data) throws BadRequestException {
        Event event = eventService.getEventById(data.eventId());

        Client client = Client.builder()
                .event(event)
                .lastName(data.lastName())
                .firstName(data.firstName())
                .plan(data.plan())
                .phoneNumber(data.phoneNumber())
                .email(data.email())
                .build();

        clientRepo.save(client);
    }

    // GET-ID
    public Client getClientRegisterationById(String id) throws BadRequestException {
        return clientRepo.findById(id)
                .orElseThrow(() -> new BadRequestException("Record not found"));
    }

    // GET-ALL
    public List<Client> getAllClientRegistrations(String page, String limit){
        PageRequest paging = PageRequest.of(
                Integer.parseInt(page),
                Integer.parseInt(limit)
        );

        return clientRepo.findAll(paging).getContent();
    }

    // UPDATE
    public Client updateClientRegistrations(String id, ClientRegister data) throws BadRequestException {
        Client prevClient = getClientRegisterationById(id);
        Event event = eventService.getEventById(data.eventId());

        prevClient.setEvent(event);
        prevClient.setLastName(data.lastName());
        prevClient.setFirstName(data.firstName());
        prevClient.setPhoneNumber(data.phoneNumber());
        prevClient.setEmail(data.email());

        return clientRepo.save(prevClient);
    }

    // GET
    public List<Client> getClientRegistrationsByEmail(String clientEmail){
        return clientRepo.findByEmail(clientEmail);
    }

    // DELETE
    public void deleteClientRegistrationById(String id) throws BadRequestException{
        Client client = getClientRegisterationById(id);

        if(client == null) throw new BadRequestException("Record not found");

        clientRepo.deleteById(id);
    }
}
