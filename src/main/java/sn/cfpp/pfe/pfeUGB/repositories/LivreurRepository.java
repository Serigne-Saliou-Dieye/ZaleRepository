package sn.cfpp.pfe.pfeUGB.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.cfpp.pfe.pfeUGB.model.Livraison;
import sn.cfpp.pfe.pfeUGB.model.Livreur;
import sn.cfpp.pfe.pfeUGB.statut.StatutLivraison;

public interface LivreurRepository extends JpaRepository<Livreur, Long>{
    List<Livreur> findByNomLivStartingWith(String nom);


    @Query("SELECT l, COUNT(o) AS orderCount FROM Livreur l " +
           "JOIN l.livraison o " +
           "GROUP BY l " +
           "ORDER BY orderCount DESC")
    List<Object[]> findTop5MostActiveLivreurs(Pageable pageable);

    // Spring Data JPA comprend automatiquement que vous souhaitez accéder à l'ID de l'objet lié via userLivreur.
    // List<Livraison> findByLivreur_IdLivreurAndStatutLivraison(Long livreurId, StatutLivraison statutLivraison);


    Optional<Livreur> findByUserLivreur_Id(Long userId);

    // Livreur findByUserLivreurId(Long userId); // Méthode pour récupérer le livreur par ID d'utilisateur





}
