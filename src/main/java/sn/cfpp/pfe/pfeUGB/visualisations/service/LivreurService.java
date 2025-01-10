package sn.cfpp.pfe.pfeUGB.visualisations.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sn.cfpp.pfe.pfeUGB.model.Client;
import sn.cfpp.pfe.pfeUGB.model.Commande;
import sn.cfpp.pfe.pfeUGB.model.Livraison;
import sn.cfpp.pfe.pfeUGB.model.Livreur;
import sn.cfpp.pfe.pfeUGB.repositories.ClientRepository;
import sn.cfpp.pfe.pfeUGB.repositories.CommandeRepository;
import sn.cfpp.pfe.pfeUGB.repositories.LivraisonRepository;
import sn.cfpp.pfe.pfeUGB.repositories.LivreurRepository;
import sn.cfpp.pfe.pfeUGB.security.entite.UserInfos;
import sn.cfpp.pfe.pfeUGB.security.repository.UserInfoRepository;
import sn.cfpp.pfe.pfeUGB.statut.StatutLivraison;

@Service
public class LivreurService {

    @Autowired
    private LivreurRepository livreurRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private LivraisonRepository livraisonRepository;
    @Autowired
    private ClientRepository clientRepository; 

    @Autowired
    private CommandeRepository commandeRepository;

    public Livreur createLivreur(Livreur livreur, Long userId) {
        // Récupérer l'utilisateur connecté
        UserInfos user = userInfoRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Associer l'utilisateur au livreur
        livreur.setUserLivreur(user);

        // Enregistrer le client
        return livreurRepository.save(livreur);
    }

    public Livraison assignLivreurToLivraison(Long livraisonId, Long livreurId) {
        // Récupérer le livreur par son ID
        Livreur livreur = livreurRepository.findById(livreurId)
            .orElseThrow(() -> new RuntimeException("Livreur non trouvé"));
    
        // Récupérer la livraison
        Livraison livraison = livraisonRepository.findById(livraisonId)
            .orElseThrow(() -> new RuntimeException("Livraison non trouvée"));
    
        // Vérifier le statut de la livraison
        if (livraison.getStatutLivraison() == StatutLivraison.EN_COURS || 
            livraison.getStatutLivraison() == StatutLivraison.LIVREE) {
            // Associer le livreur à la livraison
            livraison.setLivreur(livreur);
            // Enregistrer la livraison mise à jour
            return livraisonRepository.save(livraison);
        } else {
            throw new RuntimeException("Le statut de la livraison ne permet pas d'associer un livreur.");
        }
    }

    public List<Map<String, Object>> getTop5MostActiveLivreurs() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Object[]> results = livreurRepository.findTop5MostActiveLivreurs(pageable);
        List<Map<String, Object>> topLivreurs = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> livreurData = new HashMap<>();
            livreurData.put("LivreurName", ( (Livreur) result[0]).getNomLiv());
            livreurData.put("orderCount", result[1]);
            topLivreurs.add(livreurData);
        }
        return topLivreurs;
    }
   
    public List<Livreur> getLivreursByConnectedUserLivreur(Long userId) {
        // Récupérer le client associé à l'utilisateur connecté
        Optional<Client> clientOptional = clientRepository.findByUserClient_Id(userId);
    
        // Vérifiez si le client est présent
        if (!clientOptional.isPresent()) {
            return List.of(); // Retourner une liste vide si aucun client n'est trouvé
        }
    
        // Récupérer le client
        Client client = clientOptional.get();
    
        // Récupérer les commandes associées à ce client
        List<Commande> commandes = commandeRepository.findByClient_IdCl(client.getIdCl());
    
        // Récupérer les livreurs associés aux livraisons des commandes avec le statut "EN_COURS"
        return commandes.stream()
            .flatMap(commande -> {
                // Vérifier si la livraison existe
                Livraison livraison = commande.getLivraison(); // Assurez-vous que getLivraison() est une méthode de Commande
                if (livraison != null && livraison.getStatutLivraison() == StatutLivraison.EN_COURS) {
                    return Stream.of(livraison.getLivreur()); // Récupérer le livreur associé à la livraison
                }
                return Stream.empty(); // Retourner un stream vide si la livraison n'est pas en cours
            })
            .filter(livreur -> livreur != null) // Filtrer les livreurs null
            .distinct() // Éliminer les doublons
            .collect(Collectors.toList()); // Retourner la liste des livreurs
    }

}
