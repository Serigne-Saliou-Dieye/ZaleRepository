package sn.cfpp.pfe.pfeUGB.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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


    @GetMapping("/count")
    public ResponseEntity<Long> getClientCount() {
        long count = livreurRepository.count();
        return ResponseEntity.ok(count);
    }

    //créer un nouveau client
    @PostMapping
    public ResponseEntity<Livreur> createLivreur(@RequestBody Livreur livreur){
        Livreur savedLivreur = livreurRepository.save(livreur);
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

}
