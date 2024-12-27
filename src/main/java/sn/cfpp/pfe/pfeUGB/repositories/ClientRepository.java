package sn.cfpp.pfe.pfeUGB.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sn.cfpp.pfe.pfeUGB.model.Client;
import sn.cfpp.pfe.pfeUGB.security.entite.UserInfos;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByNomClStartingWith(String nom); // Méthode pour rechercher par nom

    @Query("SELECT c, COUNT(o) AS orderCount FROM Client c " +
           "JOIN c.commandes o " +
           "GROUP BY c " +
           "ORDER BY orderCount DESC")
    List<Object[]> findTop5MostActiveClients(Pageable pageable);

    // Optional<Client> findByUserInfo(UserInfos userClient);

    Optional<Client> findByUserClient(UserInfos userClient);

    Client findTopByOrderByIdClDesc();

    Optional<Client> findByUserClientId(Long userId); // Méthode pour trouver un client par l'ID de l'utilisateur


}
