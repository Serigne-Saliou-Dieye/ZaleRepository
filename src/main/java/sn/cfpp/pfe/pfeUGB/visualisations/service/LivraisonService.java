package sn.cfpp.pfe.pfeUGB.visualisations.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.cfpp.pfe.pfeUGB.model.Client;
import sn.cfpp.pfe.pfeUGB.model.Commande;
import sn.cfpp.pfe.pfeUGB.model.Livraison;
import sn.cfpp.pfe.pfeUGB.model.Livreur;
import sn.cfpp.pfe.pfeUGB.model.Produit;
import sn.cfpp.pfe.pfeUGB.repositories.ClientRepository;
import sn.cfpp.pfe.pfeUGB.repositories.CommandeRepository;
import sn.cfpp.pfe.pfeUGB.repositories.LivraisonRepository;
import sn.cfpp.pfe.pfeUGB.repositories.LivreurRepository;
import sn.cfpp.pfe.pfeUGB.security.repository.UserInfoRepository;
import sn.cfpp.pfe.pfeUGB.statut.StatutLivraison;

@Service
public class LivraisonService {
    @Autowired
    private LivreurRepository livreurRepository; // Assurez-vous d'avoir ce repository

    @Autowired
    private LivraisonRepository livraisonRepository; // Assurez-vous d'avoir ce repository

    @Autowired
    private UserInfoRepository userInfosRepository; // Assurez-vous d'avoir ce repository pour récupérer l'utilisateur
    

    // Méthode pour récupérer les livraisons associées à l'utilisateur connecté avec le statut "EN_COURS"
    public List<Produit> getProduitsByConnectedUser (Long userId) {
        // Récupérer le livreur associé à l'utilisateur
        Optional<Livreur> livreurOptional = livreurRepository.findByUserLivreur_Id(userId);

        // Vérifiez si le livreur est présent
        if (!livreurOptional.isPresent()) {
            return List.of(); // Retourner une liste vide si aucun livreur n'est trouvé
        }

        // Récupérer le livreur
        Livreur livreur = livreurOptional.get();

        // Récupérer les livraisons associées à ce livreur avec le statut "EN_COURS"
        List<Livraison> livraisons = livraisonRepository.findByLivreur_IdLivreurAndStatutLivraison(livreur.getIdLivreur(), StatutLivraison.EN_COURS);

        // Récupérer les produits associés aux commandes de ces livraisons
        List<Produit> produits = livraisons.stream()
            .map(Livraison::getCommande) // Récupérer la commande associée
            .filter(commande -> commande != null) // Filtrer les commandes null
            .flatMap(commande -> commande.getProduits().stream()) // Récupérer les produits associés à chaque commande
            .collect(Collectors.toList());

        return produits; // Retourner la liste des produits
    }
    

}
