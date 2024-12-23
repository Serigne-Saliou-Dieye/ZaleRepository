package sn.cfpp.pfe.pfeUGB.visualisations.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
