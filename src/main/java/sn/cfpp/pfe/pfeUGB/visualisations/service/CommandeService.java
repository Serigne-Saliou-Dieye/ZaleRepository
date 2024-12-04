package sn.cfpp.pfe.pfeUGB.visualisations.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.cfpp.pfe.pfeUGB.repositories.CommandeRepository;
import sn.cfpp.pfe.pfeUGB.visualisations.dto.MonthlyOrdersDTO;
import sn.cfpp.pfe.pfeUGB.visualisations.dto.OrderStatusStatsDTO;

@Service
public class CommandeService {
    @Autowired
    private CommandeRepository commandeRepository;

    public List<MonthlyOrdersDTO> getMonthlyOrdersStats() {
        List<Object[]> result = commandeRepository.getMonthlyOrdersStats();

        List<MonthlyOrdersDTO> monthlyOrders = new ArrayList<>();
        for (Object[] row : result) {
            String month = (String) row[0]; // Nom du mois
            Long totalOrders = (Long) row[1]; // Nombre total de commandes pour ce mois
            monthlyOrders.add(new MonthlyOrdersDTO(month, totalOrders));
        }
        return monthlyOrders;
    }


    public List<OrderStatusStatsDTO> getOrderStatusStats() {
        return commandeRepository.getOrderStatusStats();
    }

}
