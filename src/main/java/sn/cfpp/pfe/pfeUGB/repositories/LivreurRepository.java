package sn.cfpp.pfe.pfeUGB.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.cfpp.pfe.pfeUGB.model.Livreur;

public interface LivreurRepository extends JpaRepository<Livreur, Long>{
    List<Livreur> findByNomLivStartingWith(String nom);

}
