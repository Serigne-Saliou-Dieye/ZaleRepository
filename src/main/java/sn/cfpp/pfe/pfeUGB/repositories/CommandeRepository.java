package sn.cfpp.pfe.pfeUGB.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.cfpp.pfe.pfeUGB.model.Commande;
import sn.cfpp.pfe.pfeUGB.model.Produit;
import sn.cfpp.pfe.pfeUGB.statut.StatutLivraison;
import sn.cfpp.pfe.pfeUGB.visualisations.dto.MonthlyOrdersDTO;
import sn.cfpp.pfe.pfeUGB.visualisations.dto.OrderStatusStatsDTO;

public interface CommandeRepository extends JpaRepository<Commande, Long>{
    List<Commande> findByDateCmd(Date dateCmd);

    @Query(value = "SELECT MONTHNAME(c.dateCmd) AS month, COUNT(c.idCmd) AS totalOrders " +
               "FROM Commande c " +
               "GROUP BY month " +
               "ORDER BY FIELD(month, 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December')",
       nativeQuery = true)
    List<Object[]> getMonthlyOrdersStats();




    @Query("SELECT new sn.cfpp.pfe.pfeUGB.visualisations.dto.OrderStatusStatsDTO(" +
       "c.statutCmd, COUNT(c)) " +
       "FROM Commande c " +
       "GROUP BY c.statutCmd")
    List<OrderStatusStatsDTO> getOrderStatusStats();


    @Query("SELECT c FROM Commande c WHERE c.livraison IS NULL")
    List<Commande> findAllWithoutLivraison();

   List<Commande> findByClientIdCl(Long idCl);
   

   List<Commande> findAllById(Iterable<Long> ids); // Méthode pour récupérer les commandes par leurs IDs


}
