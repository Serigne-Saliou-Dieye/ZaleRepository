package sn.cfpp.pfe.pfeUGB.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import sn.cfpp.pfe.pfeUGB.model.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long>{
    List<Produit> findByNomProdStartingWith(String nomProd);

    
}
