package sn.cfpp.pfe.pfeUGB.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sn.cfpp.pfe.pfeUGB.model.Client;
import sn.cfpp.pfe.pfeUGB.model.Commande;
import sn.cfpp.pfe.pfeUGB.repositories.ClientRepository;
import sn.cfpp.pfe.pfeUGB.repositories.CommandeRepository;
import sn.cfpp.pfe.pfeUGB.security.entite.UserInfos;
import sn.cfpp.pfe.pfeUGB.security.repository.UserInfoRepository;
import sn.cfpp.pfe.pfeUGB.statut.StatutCommande;
import sn.cfpp.pfe.pfeUGB.visualisations.dto.MonthlyOrdersDTO;
import sn.cfpp.pfe.pfeUGB.visualisations.dto.OrderStatusStatsDTO;
import sn.cfpp.pfe.pfeUGB.visualisations.service.CommandeService;
import sn.cfpp.pfe.pfeUGB.websockets.NotificationService;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    

    private final CommandeRepository commandeRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private CommandeService commandeService;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    ClientRepository clientRepository;


    @GetMapping("/count")
    public ResponseEntity<Long> getClientCount() {
        long count = commandeRepository.count();
        return ResponseEntity.ok(count);
    }

    public CommandeController(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    // Create (Ajouter une nouvelle commande)
    // @PostMapping
    // public ResponseEntity<?> createCommande(@RequestBody Commande commande) {
    //     try {
    //         String email = SecurityContextHolder.getContext().getAuthentication().getName();
    //         System.out.println("Email extrait : " + email);

    //         if (email == null || email.equals("anonymousUser")) {
    //             throw new IllegalArgumentException("Utilisateur non authentifié " + email);
    //         }

    //         // Trouver l'utilisateur par email
    //         UserInfos userInfo = userInfoRepository.findByEmail(email)
    //                 .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec l'email : " + email));

    //         // Trouver le client associé à cet utilisateur
    //         Client client = clientRepository.findByUserClient(userInfo)
    //                 .orElseThrow(() -> new IllegalArgumentException("Aucun client associé à cet utilisateur"));

    //         // Associer la commande au client
    //         commande.setClient(client);
    //         Commande savedCommande = commandeRepository.save(commande);

    //         // Envoyer une notification
    //         notificationService.sendNotification("Nouvelle commande ajoutée par le client : " + client.getNomCl());

    //         return ResponseEntity.ok(savedCommande);

    //     } catch (IllegalArgumentException e) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur : " + e.getMessage());
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne : " + e.getMessage());
    //     }
    // }

    // @PostMapping
    // public ResponseEntity<Commande> createCommande(@RequestBody Commande commande, 
    //                                             @AuthenticationPrincipal UserDetails userDetails) {
    //     if (userDetails == null) {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Vérifie que l'utilisateur est connecté
    //     }
        
    //     // Récupérer l'utilisateur depuis le repository (ou injecter directement)
    //     UserInfos user = userInfoRepository.findByUsername(userDetails.getUsername())
    //                     .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));
        
    //     // Associer l'utilisateur connecté à la commande
    //     commande.setClient(user.getClient());
        
    //     // Sauvegarder la commande
    //     Commande savedCommande = commandeRepository.save(commande);
        
    //     // Envoyer une notification
    //     notificationService.sendNotification("Nouvelle commande ajoutée par " + userDetails.getUsername());
        
    //     return ResponseEntity.ok(savedCommande);
    // }

    // @PostMapping
    // public Commande createCommande(@RequestBody Commande commande) {
    //     return commandeRepository.save(commande);
    // }
    @PostMapping
    public ResponseEntity<Commande> createCommande(@RequestBody Commande commande){            
        Commande savedCommande = commandeRepository.save(commande);
        notificationService.sendNotification("Nouvelle commande ajoutée");
        return ResponseEntity.ok(savedCommande);
    }


    // Read (Lister toutes les commandes)
    @GetMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public Iterable<Commande> getAllCammandes(){
        return commandeRepository.findAll(); 
    }

    // Read (Rechercher une commande par ID)
    @GetMapping("/{id}")
    public Optional<Commande> getCommandeParId(@PathVariable Long id) {
        return commandeRepository.findById(id);
    }

    
    // Update (Modifier une commande existante)

    @PutMapping("/{id}")
    public Commande updateCommande(@PathVariable Long id, @RequestBody Commande updateCommande){
        return commandeRepository.findById(id)
        .map(commande -> {
            commande = updateCommande;
            return commandeRepository.save(commande);
        })
        .orElseThrow(() -> new RuntimeException("Commande non trouvé"));
    }
    
    // Delete (Supprimer une commande)
    @DeleteMapping("/{id}")
    public void deleteCommande(@PathVariable Long id) {
        commandeRepository.deleteById(id);
    }

    // Read (Rechercher une commande par Date)
    @GetMapping("/recherche")
    public ResponseEntity<List<Commande>> rechercherParDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateCmd) {
        List<Commande> commandes = commandeRepository.findByDateCmd(dateCmd);  // Utiliser "findByDateCmd" ici
        return new ResponseEntity<>(commandes, HttpStatus.OK);
}

    @GetMapping("/monthly-orders")
    public ResponseEntity<List<MonthlyOrdersDTO>> getMonthlyOrders() {
        List<MonthlyOrdersDTO> monthlyOrders = commandeService.getMonthlyOrdersStats();
        return ResponseEntity.ok(monthlyOrders);
    }

    @GetMapping("/status-stats")
    public ResponseEntity<List<OrderStatusStatsDTO>> getOrderStatusStats() {
        List<OrderStatusStatsDTO> statusStats = commandeService.getOrderStatusStats();
        return ResponseEntity.ok(statusStats);
    }


}
