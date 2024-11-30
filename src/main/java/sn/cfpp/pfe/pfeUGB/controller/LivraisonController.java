package sn.cfpp.pfe.pfeUGB.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import sn.cfpp.pfe.pfeUGB.model.Livraison;

import sn.cfpp.pfe.pfeUGB.repositories.LivraisonRepository;
import sn.cfpp.pfe.pfeUGB.statut.StatutLivraison;
import sn.cfpp.pfe.pfeUGB.websockets.NotificationService;

@RestController
@RequestMapping("/api/livraisons")
@CrossOrigin(origins = "http://localhost:3000")
public class LivraisonController {

    private final LivraisonRepository livraisonRepository;
    @Autowired
    private NotificationService notificationService;


    @GetMapping("/count")
    public ResponseEntity<Long> getClientCount() {
        long count = livraisonRepository.count();
        return ResponseEntity.ok(count);
    }


    public LivraisonController(LivraisonRepository livraisonRepository) {
        this.livraisonRepository = livraisonRepository;
    }

    // Create (Ajouter une nouvelle commande)
    @PostMapping
    public ResponseEntity<Livraison> createLivraison(@RequestBody Livraison livraison) {
        // Définir automatiquement la date selon le statut
        if (livraison.getStatutLivraison() == StatutLivraison.EN_COURS) {
            livraison.setDateDepart(LocalDateTime.now());
        }

        // Sauvegarder la livraison
        Livraison savedLivraison = livraisonRepository.save(livraison);

        // Envoyer une notification
        notificationService.sendNotification("Nouvelle livraison ajoutée");

        // Retourner la réponse
        return ResponseEntity.ok(savedLivraison);
    }

    // @PostMapping
    // public Livraison createLivraison(@RequestBody Livraison livraison) {
    //     // Définir automatiquement la date selon le statut
    //     if (livraison.getStatutLivraison() == StatutLivraison.EN_COURS) {
    //         livraison.setDateDepart(LocalDateTime.now());
    //     }

    //     return livraisonRepository.save(livraison);
    // }

    // Read (Lister toutes les commandes)
    @GetMapping
    public Iterable<Livraison> getAllLivraison(){
        return livraisonRepository.findAll(); 
    }

    // Read (Rechercher une commande par ID)
    @GetMapping("/{id}")
    public Optional<Livraison> getLivraisonById(@PathVariable Long id) {
        return livraisonRepository.findById(id);
    }

    
    @PutMapping("/{id}")
    public Livraison updateLivraison(@PathVariable Long id, @RequestBody Livraison updateLivraison) {
    return livraisonRepository.findById(id)
        .map(livraison -> {
            livraison.setDateArrivee(updateLivraison.getDateArrivee());
            livraison.setDateDepart(updateLivraison.getDateDepart());
            livraison.setStatutLivraison(updateLivraison.getStatutLivraison());
            // Ajoutez d'autres champs si nécessaire
            return livraisonRepository.save(livraison);
        })
        .orElseThrow(() -> new RuntimeException("Livraison non trouvé"));
}

    
    // Delete (Supprimer une commande)
    @DeleteMapping("/{id}")
    public void deleteLivraison(@PathVariable Long id) {
        livraisonRepository.deleteById(id);
    }

   // Rechercher une Livraison par Date d'arrivée
    @GetMapping("/recherche/dateArrivee")
    public ResponseEntity<List<Livraison>> rechercherParDateArrivee(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateArrivee) {
    List<Livraison> livraisons = livraisonRepository.findByDateArrivee(dateArrivee);
    return new ResponseEntity<>(livraisons, HttpStatus.OK);
}

    // Rechercher une Livraison par Date de départ
    @GetMapping("/recherche/dateDepart")
    public ResponseEntity<List<Livraison>> rechercherParDateDepart(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDepart) {
    List<Livraison> livraisons = livraisonRepository.findByDateDepart(dateDepart); 
    return new ResponseEntity<>(livraisons, HttpStatus.OK);
}

    @GetMapping("/statutLivraison/{satatutLivraison}")
    public List<Livraison> getLivraisonByStatut(@PathVariable StatutLivraison statutLivraison) {
        return livraisonRepository.findByStatutLivraison(statutLivraison);
    }  
}
