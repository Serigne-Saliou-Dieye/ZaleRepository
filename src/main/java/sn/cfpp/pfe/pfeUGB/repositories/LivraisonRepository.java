package sn.cfpp.pfe.pfeUGB.repositories;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.cfpp.pfe.pfeUGB.model.Livraison;
import sn.cfpp.pfe.pfeUGB.statut.StatutLivraison;

public interface LivraisonRepository extends JpaRepository<Livraison, Long> {
    
    List<Livraison> findByDateArrivee(LocalDateTime dateArrivee); // Nom corrigé
    List<Livraison> findByDateDepart(LocalDateTime dateDepart);   // Nom corrigé
    List<Livraison> findByStatutLivraison(StatutLivraison statutLivraison);

    List<Livraison> findByLivreur_IdLivreur(Long livreurId); // Méthode pour récupérer les livraisons par ID de livreur

    List<Livraison> findByLivreur_IdLivreurAndStatutLivraison(Long livreurId, StatutLivraison statutLivraison);



}
