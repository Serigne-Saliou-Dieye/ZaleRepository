package sn.cfpp.pfe.pfeUGB.visualisations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.cfpp.pfe.pfeUGB.statut.StatutCommande;

// @Data
// @AllArgsConstructor
// @NoArgsConstructor
public class OrderStatusStatsDTO {
    private String status; // Exemple : "EN_ATTENTE", "LIVRÉE"
    private Long count;


    public OrderStatusStatsDTO() {
    }


    public OrderStatusStatsDTO(StatutCommande status, Long count) {
        this.status = status.toString();
        this.count = count;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCount() {
        return this.count;
    }

    public void setCount(Long count) {
        this.count = count;
    }


}
