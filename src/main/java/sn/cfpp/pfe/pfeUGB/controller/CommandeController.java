package sn.cfpp.pfe.pfeUGB.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sn.cfpp.pfe.pfeUGB.model.Commande;
import sn.cfpp.pfe.pfeUGB.repositories.CommandeRepository;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    

    private final CommandeRepository commandeRepository;

    public CommandeController(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    // Create (Ajouter une nouvelle commande)
    @PostMapping
    public Commande createCommande(@RequestBody Commande commande) {
        return commandeRepository.save(commande);
    }

    // Read (Lister toutes les commandes)
    @GetMapping
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


}
