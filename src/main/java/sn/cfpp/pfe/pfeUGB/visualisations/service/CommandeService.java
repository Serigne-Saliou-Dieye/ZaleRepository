package sn.cfpp.pfe.pfeUGB.visualisations.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import sn.cfpp.pfe.pfeUGB.model.Commande;
import sn.cfpp.pfe.pfeUGB.repositories.CommandeRepository;
import sn.cfpp.pfe.pfeUGB.repositories.LivraisonRepository;
import sn.cfpp.pfe.pfeUGB.statut.StatutCommande;
import sn.cfpp.pfe.pfeUGB.visualisations.dto.MonthlyOrdersDTO;
import sn.cfpp.pfe.pfeUGB.visualisations.dto.OrderStatusStatsDTO;

@Service
public class CommandeService {
    @Autowired
    private CommandeRepository commandeRepository;
     @Autowired
    private LivraisonRepository livraisonRepository;

    public List<MonthlyOrdersDTO> getMonthlyOrdersStats() {
        List<Object[]> result = commandeRepository.getMonthlyOrdersStats();

        List<MonthlyOrdersDTO> monthlyOrders = new ArrayList<>();
        for (Object[] row : result) {
            String month = (String) row[0]; // Nom du mois
            Long totalOrders = (Long) row[1]; // Nombre total de commandes pour ce mois
            monthlyOrders.add(new MonthlyOrdersDTO(month, totalOrders));
        }
        return monthlyOrders;
    }


    public List<OrderStatusStatsDTO> getOrderStatusStats() {
        return commandeRepository.getOrderStatusStats();
    }

    public Commande mettreAJourStatutCommande(Long idCommande, StatutCommande nouveauStatut) {
        Commande commande = commandeRepository.findById(idCommande)
            .orElseThrow(() -> new EntityNotFoundException("Commande non trouvée avec l'ID : " + idCommande));
        
        // Mise à jour du statut de la commande
        commande.setStatutCmd(nouveauStatut);
        
        // Synchronisation du statut de la livraison
        commande.synchroniserStatutLivraison();
        
        // Sauvegarder la commande et la livraison (si modifiée)
        if (commande.getLivraison() != null) {
            livraisonRepository.save(commande.getLivraison());  // Sauvegarde de la livraison si elle a été modifiée
        }
        
        // Sauvegarder la commande
        return commandeRepository.save(commande);
    }

}
