package sn.cfpp.pfe.pfeUGB.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import sn.cfpp.pfe.pfeUGB.model.Produit;
import sn.cfpp.pfe.pfeUGB.repositories.ProduitRepository;
import sn.cfpp.pfe.pfeUGB.websockets.NotificationService;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private NotificationService notificationService;


    @GetMapping("/count")
    public ResponseEntity<Long> getClientCount() {
        long count = produitRepository.count();
        return ResponseEntity.ok(count);
    }

     //créer un nouveau client
     @PostMapping
    public ResponseEntity<Produit> createProduit(@RequestBody Produit produit){
        Produit savedProduit = produitRepository.save(produit);
        notificationService.sendNotification("Nouveau produit ajouté");
        return ResponseEntity.ok(savedProduit);
    }

    // @PostMapping
    // public Produit createProduit(@RequestBody Produit produit){
    //     return produitRepository.save(produit);
    // }

    //obtenir la liste des clients 
    @GetMapping
    public Iterable<Produit> getAllProduits(){
        return produitRepository.findAll(); 
    }

    //obtenir un client par son id
    @GetMapping("/{id}")
    public Optional<Produit> getProduitById(@PathVariable Long id){
        return produitRepository.findById(id);
    }

    //modifier un client 
    @PutMapping("/{id}")
    public Produit updateProduit(@PathVariable Long id, @RequestBody Produit updateProduit){
        return produitRepository.findById(id)
               .map(produit -> {
                produit = updateProduit;
                return produitRepository.save(produit);
               })
               .orElseThrow(() -> new RuntimeException("produit non trouvé"));
    }

    //supprimer un client
    @DeleteMapping("/{id}")
    public void deleteProduit(@PathVariable Long id){
        produitRepository.deleteById(id);
    }

    //rechercher un client par son nom
    @GetMapping("/recherche")
    public ResponseEntity<List<Produit>> recherche(@RequestParam("nom") String nomProd) {
        List<Produit> produits = produitRepository.findByNomProdStartingWith(nomProd);
        return new ResponseEntity<>(produits, HttpStatus.OK);
    }

}
