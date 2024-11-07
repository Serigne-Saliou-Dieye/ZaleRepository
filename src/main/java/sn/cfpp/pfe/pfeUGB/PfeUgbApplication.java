package sn.cfpp.pfe.pfeUGB;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sn.cfpp.pfe.pfeUGB.model.*;
import sn.cfpp.pfe.pfeUGB.repositories.*;
import sn.cfpp.pfe.pfeUGB.statut.StatutCommande;
import sn.cfpp.pfe.pfeUGB.statut.StatutLivraison;
import sn.cfpp.pfe.pfeUGB.statut.StatutNotification;
import sn.cfpp.pfe.pfeUGB.statut.TypeNotification;


@SpringBootApplication
public class PfeUgbApplication implements CommandLineRunner{

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private ProduitRepository produitRepository;
	@Autowired
	private LivreurRepository livreurRepository;
	@Autowired
	private NotificationRepository notificationRepository ;
	@Autowired
	private CommandeRepository commandeRepository;
	@Autowired
	private LivraisonRepository livraisonRepository;

	public static void main(String[] args) {
		SpringApplication.run(PfeUgbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{

		// Enregistrer des clients
		Client cl1 = new Client(null, "Demba", "44334433", "demba12", "demba@gmail.com");
		Client cl2 = new Client(null, "Samba", "44884433", "samba14", "samba@gmail.com");
		clientRepository.saveAll(Arrays.asList(cl1, cl2));
	
		// Enregistrer des produits
		List<Produit> produits = produitRepository.saveAll(Arrays.asList(
			new Produit(null, "lait", "lait concentré", 123.0),
			new Produit(null, "viande", "viande haché", 1232.0)
		));
	
		// Enregistrer les livreurs
		Livreur liv1 = new Livreur(null, "Moussa", "moussa@gmail.com", "8877665", "toyota");
		Livreur liv2 = new Livreur(null, "aly", "aly@gmail.com", "33221165", "helux");
		livreurRepository.saveAll(Arrays.asList(liv1, liv2));
	
		// Enregistrer les commandes avec les produits associés
		Commande com1 = new Commande(StatutCommande.ANNULEE, cl1);
		Commande com2 = new Commande(StatutCommande.LIVREE, cl2);
		com1.setProduits(produits);
		com2.setProduits(produits);
		commandeRepository.saveAll(Arrays.asList(com1, com2));
	
		
		// Enregistrer les livraisons et les associer aux commandes
		Livraison livraison1 = new Livraison(StatutLivraison.EN_COURS, liv1);
		livraison1.setCommande(com1);
		Livraison livraison2 = new Livraison(StatutLivraison.LIVREE, liv2);
		livraison2.setCommande(com2);
		livraisonRepository.saveAll(Arrays.asList(livraison1, livraison2));


		// Enregistrer les notifications
		Notifications not1 = new Notifications(TypeNotification.ANNULATION_COMMANDE, StatutNotification.ENVOYEE, com1, cl1);
		Notifications not2 = new Notifications(TypeNotification.LIVRAISON_EFFECTUÉE, StatutNotification.IGNOREE, com2, cl2);
		notificationRepository.saveAll(Arrays.asList(not1, not2));
	
	}

}
