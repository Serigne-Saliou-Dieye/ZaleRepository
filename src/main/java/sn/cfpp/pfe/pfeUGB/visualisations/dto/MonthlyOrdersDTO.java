package sn.cfpp.pfe.pfeUGB.visualisations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Data
// @AllArgsConstructor
// @NoArgsConstructor
public class MonthlyOrdersDTO {
    private String month; // Exemple : "Janvier", "Février"
    private Long totalOrders;


    public MonthlyOrdersDTO() {
    }

    public MonthlyOrdersDTO(String month, Long totalOrders) {
        this.month = month;
        this.totalOrders = totalOrders;
    }


    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getTotalOrders() {
        return this.totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }

}
