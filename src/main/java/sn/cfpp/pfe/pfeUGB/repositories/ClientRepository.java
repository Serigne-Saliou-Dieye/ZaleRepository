package sn.cfpp.pfe.pfeUGB.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sn.cfpp.pfe.pfeUGB.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByNomClStartingWith(String nom); // Méthode pour rechercher par nom

    @Query("SELECT c, COUNT(o) AS orderCount FROM Client c " +
           "JOIN c.commandes o " +
           "GROUP BY c " +
           "ORDER BY orderCount DESC")
    List<Object[]> findTop5MostActiveClients(Pageable pageable);
}
