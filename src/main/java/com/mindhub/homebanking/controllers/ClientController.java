package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/api/client")
    public List<ClientDTO> getClients(){
        return clientService.getAllClients().stream().map(client -> new ClientDTO(client)).collect(toList());
    }
    @GetMapping("/api/client/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return new ClientDTO(clientService.getClientsById(id));
    }

    @PostMapping ("/api/client")
    public ResponseEntity<Object> register(@RequestParam String first, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {

        if (first.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientService.getClientsByEmail(email) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        clientService.saveClient(new Client(first, lastName, email, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/client/current")
    public ClientDTO getClientsCurrent(Authentication authentication) {
        return new ClientDTO(clientService.getClientsByEmail(authentication.getName()));
    }
}

