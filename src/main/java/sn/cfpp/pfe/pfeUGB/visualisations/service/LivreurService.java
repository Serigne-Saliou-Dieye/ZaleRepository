package sn.cfpp.pfe.pfeUGB.visualisations.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sn.cfpp.pfe.pfeUGB.model.Livreur;
import sn.cfpp.pfe.pfeUGB.repositories.LivreurRepository;

@Service
public class LivreurService {

    @Autowired
    private LivreurRepository livreurRepository;

    public List<Map<String, Object>> getTop5MostActiveLivreurs() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Object[]> results = livreurRepository.findTop5MostActiveLivreurs(pageable);
        List<Map<String, Object>> topLivreurs = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> livreurData = new HashMap<>();
            livreurData.put("LivreurName", ( (Livreur) result[0]).getNomLiv());
            livreurData.put("orderCount", result[1]);
            topLivreurs.add(livreurData);
        }
        return topLivreurs;
    }

}
