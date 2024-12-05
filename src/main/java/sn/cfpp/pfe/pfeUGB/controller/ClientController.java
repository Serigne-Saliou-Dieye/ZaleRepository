package sn.cfpp.pfe.pfeUGB.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import sn.cfpp.pfe.pfeUGB.model.Client;
import sn.cfpp.pfe.pfeUGB.model.Commande;
import sn.cfpp.pfe.pfeUGB.repositories.ClientRepository;
import sn.cfpp.pfe.pfeUGB.repositories.CommandeRepository;
import sn.cfpp.pfe.pfeUGB.visualisations.service.ClientService;
import sn.cfpp.pfe.pfeUGB.websockets.NotificationService;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:3000")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    ClientService clientService;
    @Autowired
    CommandeRepository commandeRepository;


    // Retourner le nombre total de clients
    @GetMapping("/count")
    public ResponseEntity<Long> getClientCount() {
        long count = clientRepository.count();
        return ResponseEntity.ok(count);
    }
    //créer un nouveau client
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client){
        Client savedClient = clientRepository.save(client);

        notificationService.sendNotification("Nouveau client ajouté");

        return ResponseEntity.ok(savedClient) ;
    }

    // @GetMapping("/send")
    // public ResponseEntity<String> sendTestNotification() {
    //     notificationService.sendNotification("Test de notification");
    //     return ResponseEntity.ok("Notification envoyée");
    // }

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

    @GetMapping("/top-active")
    public ResponseEntity<List<Map<String, Object>>> getTop5MostActiveClients() {
        List<Map<String, Object>> topClients = clientService.getTop5MostActiveClients();
        return ResponseEntity.ok(topClients);
    }


    // Récupérer les commandes d'un client
    @GetMapping("/{id}/commandes")
    public ResponseEntity<?> getClientCommandes(@PathVariable Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (!client.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }
        List<Commande> commandes = client.get().getCommandes();
        return ResponseEntity.ok(commandes);
    }


}
