package sn.cfpp.pfe.pfeUGB.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.cfpp.pfe.pfeUGB.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByNomClStartingWith(String nom); // Méthode pour rechercher par nom

}
