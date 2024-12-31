package sn.cfpp.pfe.pfeUGB.visualisations.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sn.cfpp.pfe.pfeUGB.model.Client;
import sn.cfpp.pfe.pfeUGB.model.Livraison;
import sn.cfpp.pfe.pfeUGB.model.Livreur;
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
   

}
