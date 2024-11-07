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

}
