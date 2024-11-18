package sn.cfpp.pfe.pfeUGB.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import sn.cfpp.pfe.pfeUGB.model.Client;
import sn.cfpp.pfe.pfeUGB.repositories.ClientRepository;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:3000")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;


    //créer un nouveau client
    @PostMapping
    public Client createClient(@RequestBody Client client){
        return clientRepository.save(client);
    }

    //obtenir la liste des clients 
    @GetMapping
    public Iterable<Client> getAllClients(){
        return clientRepository.findAll(); 
    }

    //obtenir un client par son id
    @GetMapping("/{id}")
    public Optional<Client> getClientById(@PathVariable Long id){
        return clientRepository.findById(id);
    }

    //modifier un client 
    @PutMapping("/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client updateClient){
        return clientRepository.findById(id)
               .map(client -> {
                client = updateClient;
                return clientRepository.save(client);
               })
               .orElseThrow(() -> new RuntimeException("Client non trouvé"));
    }

    //supprimer un client
    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id){
        clientRepository.deleteById(id);
    }

    //rechercher un client par son nom
    @GetMapping("/recherche")
    public ResponseEntity<List<Client>> recherche(@RequestParam("nom") String nom) {
        List<Client> clients = clientRepository.findByNomClStartingWith(nom);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }


}
