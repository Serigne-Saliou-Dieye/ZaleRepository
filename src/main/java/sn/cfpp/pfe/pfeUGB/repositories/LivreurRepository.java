package sn.cfpp.pfe.pfeUGB.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sn.cfpp.pfe.pfeUGB.model.Livreur;
import sn.cfpp.pfe.pfeUGB.security.entite.UserInfos;

public interface LivreurRepository extends JpaRepository<Livreur, Long>{
    List<Livreur> findByNomLivStartingWith(String nom);


    @Query("SELECT l, COUNT(o) AS orderCount FROM Livreur l " +
           "JOIN l.livraison o " +
           "GROUP BY l " +
           "ORDER BY orderCount DESC")
    List<Object[]> findTop5MostActiveLivreurs(Pageable pageable);


    Optional<UserInfos> findByUserLivreur(UserInfos user);

}
