package sn.cfpp.pfe.pfeUGB.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.cfpp.pfe.pfeUGB.model.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long>{
    List<Commande> findByDateCmd(Date dateCmd);

}
