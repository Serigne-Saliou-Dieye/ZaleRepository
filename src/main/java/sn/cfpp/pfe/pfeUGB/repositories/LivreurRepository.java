package sn.cfpp.pfe.pfeUGB.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.cfpp.pfe.pfeUGB.model.Livreur;

public interface LivreurRepository extends JpaRepository<Livreur, Long>{
    List<Livreur> findByNomLivStartingWith(String nom);


    @Query("SELECT l, COUNT(o) AS orderCount FROM Livreur l " +
           "JOIN l.livraison o " +
           "GROUP BY l " +
           "ORDER BY orderCount DESC")
    List<Object[]> findTop5MostActiveLivreurs(Pageable pageable);

    // Spring Data JPA comprend automatiquement que vous souhaitez accéder à l'ID de l'objet lié via userLivreur.
    Optional<Livreur> findByUserLivreur_Id(Long userId);

    // Cette requête spécifie explicitement que vous voulez comparer l.userLivreur.id à userId.
    // @Query("SELECT l FROM Livreur l WHERE l.userLivreur.id = :userId")
    // Optional<Livreur> findByUserLivreurId(@Param("userId") Long userId);





}
