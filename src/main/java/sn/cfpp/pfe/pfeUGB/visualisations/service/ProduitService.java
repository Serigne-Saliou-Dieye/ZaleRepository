package sn.cfpp.pfe.pfeUGB.visualisations.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import sn.cfpp.pfe.pfeUGB.model.Client;
import sn.cfpp.pfe.pfeUGB.model.Commande;
import sn.cfpp.pfe.pfeUGB.model.Livraison;
import sn.cfpp.pfe.pfeUGB.model.Produit;
import sn.cfpp.pfe.pfeUGB.repositories.ClientRepository;
import sn.cfpp.pfe.pfeUGB.repositories.CommandeRepository;
import sn.cfpp.pfe.pfeUGB.repositories.LivraisonRepository;
import sn.cfpp.pfe.pfeUGB.repositories.ProduitRepository;
import sn.cfpp.pfe.pfeUGB.statut.StatutCommande;
import sn.cfpp.pfe.pfeUGB.statut.StatutLivraison;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository; // Assurez-vous d'avoir un repository pour Produit

    @Autowired
    private CommandeRepository commandeRepository; // Assurez-vous d'avoir un repository pour Commande

    @Autowired
    private ClientRepository clientRepository; // Assurez-vous d'avoir un repository pour Client

    @Autowired
    LivraisonRepository livraisonRepository; 

    public Produit ajouterProduitAvecCommande(Produit produit, Long clientId) {
        // 1. Ajouter le produit
        Produit savedProduit = produitRepository.save(produit);

        // 2. Récupérer le client
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        // 3. Créer une nouvelle commande
        Commande commande = new Commande();
        // commande.setDateCmd(new Date()); // Définir la date de la commande
        commande.setStatutCmd(StatutCommande.EN_ATTENTE); // Statut par défaut
        commande.setClient(client); // Associer la commande au client

        // 4. Associer le produit à la commande
        commande.getProduits().add(savedProduit); // Ajouter le produit à la commande

        // 5. Enregistrer la commande
        Commande savedCommande = commandeRepository.save(commande);

        // 6. Créer une nouvelle livraison associée à la commande
        Livraison livraison = new Livraison();
        livraison.setCommande(savedCommande); // Associer la livraison à la commande
        livraison.setStatutLivraison(StatutLivraison.EN_ATTENTE); // Définir le statut de la livraison
        livraison.setDateDepart(null); // Pas de date de départ pour une livraison en attente

        // 7. Sauvegarder la livraison
        livraisonRepository.save(livraison);

        // Retourner le produit ajouté
    return savedProduit; // Retourner le produit sauvegardé
    }

    public List<Produit> getProduitsSansLivraison() {
        // Récupérer toutes les commandes sans livraison
        List<Commande> commandesSansLivraison = commandeRepository.findAllWithoutLivraison();

        // Récupérer tous les IDs des produits associés à ces commandes
        Set<Long> produitIds = new HashSet<>();
        for (Commande commande : commandesSansLivraison) {
            produitIds.addAll(commande.getProduits().stream()
                .map(Produit::getIdProd) // Assurez-vous que getIdProd() est la méthode pour obtenir l'ID du produit
                .collect(Collectors.toList()));
        }

        // Récupérer tous les produits
        List<Produit> tousLesProduits = produitRepository.findAll();

        // Filtrer les produits pour ne garder que ceux qui ne sont pas dans produitIds
        return tousLesProduits.stream()
            .filter(produit -> !produitIds.contains(produit.getIdProd()))
            .collect(Collectors.toList());
    }

    // Méthode pour récupérer les produits sans livraison d'un utilisateur spécifique
    public List<Produit> getProduitsSansLivraisonByUser(Long userId) {
        // Récupérer le client associé à l'utilisateur
        Optional<Client> clientOpt = clientRepository.findByUserClientId(userId);
        if (clientOpt.isEmpty()) {
            throw new EntityNotFoundException("Aucun client associé à cet utilisateur.");
        }
        Client client = clientOpt.get();
    
        // Récupérer les commandes associées à ce client
        List<Commande> commandesClient = commandeRepository.findByClientIdCl(client.getIdCl());
    
        // Récupérer les produits sans livraison
        List<Produit> produitsSansLivraison = getProduitsSansLivraison();
    
        // Récupérer les produits associés aux commandes du client
        Set<Long> idsProduitsCommandesClient = commandesClient.stream()
            .flatMap(commande -> commande.getProduits().stream())
            .map(Produit::getIdProd)
            .collect(Collectors.toSet());
    
        // Filtrer les produits sans livraison pour ne garder que ceux appartenant aux commandes du client
        return produitsSansLivraison.stream()
            .filter(produit -> idsProduitsCommandesClient.contains(produit.getIdProd()))
            .collect(Collectors.toList());
    }
    

    public List<Produit> getProduitsAvecLivraisonByUser(Long userId) {
        // Récupérer le client associé à l'utilisateur
        Optional<Client> clientOpt = clientRepository.findByUserClientId(userId);
        if (clientOpt.isEmpty()) {
            throw new EntityNotFoundException("Aucun client associé à cet utilisateur.");
        }
        Client client = clientOpt.get();
    
        // Récupérer les commandes associées au client
        List<Commande> commandesClient = commandeRepository.findByClientIdCl(client.getIdCl());
    
        // Filtrer les commandes avec une livraison en cours ou en attente
        List<Commande> commandesAvecLivraisonValide = commandesClient.stream()
            .filter(commande -> commande.getLivraison() != null) // Vérifie que la commande est associée à une livraison
            .filter(commande -> commande.getLivraison().getStatutLivraison().equals(StatutLivraison.EN_COURS) 
                            //  || commande.getLivraison().getStatutLivraison().equals(StatutLivraison.EN_ATTENTE)
                             ) // Vérifie le statut
            .collect(Collectors.toList());
    
        // Extraire les produits des commandes valides
        Set<Produit> produitsAvecLivraisonValide = commandesAvecLivraisonValide.stream()
            .flatMap(commande -> commande.getProduits().stream()) // Récupère les produits de chaque commande
            .collect(Collectors.toSet());
    
        // Retourner les produits sous forme de liste
        return new ArrayList<>(produitsAvecLivraisonValide);
    }
    


}
