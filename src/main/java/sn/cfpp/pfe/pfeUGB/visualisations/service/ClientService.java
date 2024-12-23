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
import sn.cfpp.pfe.pfeUGB.security.entite.UserInfos;
import sn.cfpp.pfe.pfeUGB.security.repository.UserInfoRepository;

@Service
public class ClientService {
     @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserInfoRepository userInfosRepository;


    public Client createClient(Client client, Long userId) {
        // Récupérer l'utilisateur connecté
        UserInfos user = userInfosRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Associer l'utilisateur au client
        client.setUserClient(user);

        // Enregistrer le client
        return clientRepository.save(client);
    }

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
