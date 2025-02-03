package sn.cfpp.pfe.pfeUGB.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import sn.cfpp.pfe.pfeUGB.model.Client;
import sn.cfpp.pfe.pfeUGB.model.Commande;
import sn.cfpp.pfe.pfeUGB.model.Livraison;
import sn.cfpp.pfe.pfeUGB.model.Livreur;
import sn.cfpp.pfe.pfeUGB.repositories.LivreurRepository;
import sn.cfpp.pfe.pfeUGB.visualisations.service.LivreurService;
import sn.cfpp.pfe.pfeUGB.websockets.NotificationService;

@RestController
@RequestMapping("/api/livreurs")
@CrossOrigin(origins = "http://localhost:3000")
public class LivreurController {

    @Autowired
    private LivreurRepository livreurRepository;
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private LivreurService livreurService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @GetMapping("/count")
    public ResponseEntity<Long> getClientCount() {
        long count = livreurRepository.count();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getLivreurByUserId(@PathVariable Long userId) {
        if (userId == null || userId <= 0) {
            return ResponseEntity.badRequest().body("L'ID utilisateur fourni est invalide.");
        }

        Optional<Livreur> livreurOpt = livreurRepository.findByUserLivreur_Id(userId);
        if (livreurOpt.isPresent()) {
            return ResponseEntity.ok(livreurOpt.get());
        } else {
            return ResponseEntity.ok("Aucun livreur trouvé pour cet utilisateur.");
        }
    }


    @GetMapping("/en-cours/{userId}")
    public ResponseEntity<List<Livreur>> getLivreursEnCours(@PathVariable Long userId) {
        List<Livreur> livreurs = livreurService.getLivreursByConnectedUserLivreur(userId);

        if (livreurs.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retourne un code 204 si aucun livreur n'est trouvé
        }

        return ResponseEntity.ok(livreurs); // Retourne un code 200 avec la liste des livreurs
    }
    @GetMapping("/statutLivraison/en-cours")
    public ResponseEntity<List<Livreur>> getLivreursStatutLivraisonEnCours() {
        List<Livreur> livreurs = livreurService.getLivreursAvecStatusLivraisonEncours();

        if (livreurs.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retourne un code 204 si aucun livreur n'est trouvé
        }

        return ResponseEntity.ok(livreurs); // Retourne un code 200 avec la liste des livreurs
    }


    @PutMapping("/update-location/{livreurId}")
    public ResponseEntity<Void> updateLocation(@PathVariable Long livreurId, @RequestBody Map<String, Double> coords) {
        Optional<Livreur> livreurOpt = livreurRepository.findById(livreurId);
        if (livreurOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Livreur livreur = livreurOpt.get();
        livreur.setLatitude(coords.get("latitude"));
        livreur.setLongitude(coords.get("longitude"));
        livreurRepository.save(livreur);

        // Diffuser la position uniquement au client lié à ce livreur
        messagingTemplate.convertAndSend("/topic/locations/" + livreurId, livreur);

        return ResponseEntity.ok().build();
    }




    //créer un nouveau client
    @PostMapping
    public ResponseEntity<Livreur> createLivreur(@RequestBody Livreur livreur, @RequestParam Long userId){
        Livreur savedLivreur = livreurService.createLivreur(livreur, userId);
        notificationService.sendNotification("Nouveau livreur ajouté");
        return ResponseEntity.ok(savedLivreur);
    }

    // @PostMapping
    // public Livreur createLivreur(@RequestBody Livreur livreur){
    //     return livreurRepository.save(livreur);
    // }

    //obtenir la liste des clients 
    @GetMapping
    public Iterable<Livreur> getAllLivreurs(){
        return livreurRepository.findAll(); 
    }

    //obtenir un client par son id
    @GetMapping("/{id}")
    public Optional<Livreur> getLivreurById(@PathVariable Long id){
        return livreurRepository.findById(id);
    }
   

    //modifier un client 
    @PutMapping("/{id}")
    public Livreur updateLivreur(@PathVariable Long id, @RequestBody Livreur updateLivreur){
        return livreurRepository.findById(id)
               .map(livreur -> {
                livreur = updateLivreur;
                return livreurRepository.save(livreur);
               })
               .orElseThrow(() -> new RuntimeException("Livreur non trouvé"));
    }

    //supprimer un client
    @DeleteMapping("/{id}")
    public void deleteLivreur(@PathVariable Long id){
        livreurRepository.deleteById(id);
    }

    //rechercher un client par son nom
    @GetMapping("/recherche")
        public ResponseEntity<List<Livreur>> recherche(@RequestParam("nom") String nom) {
        List<Livreur> livreurs = livreurRepository.findByNomLivStartingWith(nom);
        return new ResponseEntity<>(livreurs, HttpStatus.OK);
    }

    @GetMapping("/top-active")
    public ResponseEntity<List<Map<String, Object>>> getTop5MostActiveLivreur() {
        List<Map<String, Object>> topLivreurs = livreurService.getTop5MostActiveLivreurs();
        return ResponseEntity.ok(topLivreurs);
    }

    // @GetMapping("/{id}/livraisons")
    // public ResponseEntity<?> getLivreurLivraison(@PathVariable Long id) {
    //     Optional<Livreur> livreur = livreurRepository.findById(id);
    //     if (!livreur.isPresent()) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livreur not found");
    //     }
    //     List<Livraison> livraisons = livreur.get().getLivraison();
    //     return ResponseEntity.ok(livraisons);
    // }


}
