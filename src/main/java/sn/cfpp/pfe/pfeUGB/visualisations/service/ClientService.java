package sn.cfpp.pfe.pfeUGB.visualisations.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import sn.cfpp.pfe.pfeUGB.model.Client;
import sn.cfpp.pfe.pfeUGB.repositories.ClientRepository;

@Service
public class ClientService {
     @Autowired
    private ClientRepository clientRepository;

    public List<Map<String, Object>> getTop5MostActiveClients() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Object[]> results = clientRepository.findTop5MostActiveClients(pageable);
        List<Map<String, Object>> topClients = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> clientData = new HashMap<>();
            clientData.put("clientName", ((Client) result[0]).getNomCl());
            clientData.put("orderCount", result[1]);
            topClients.add(clientData);
        }
        return topClients;
    }

}
