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

	public static void main(String[] args) {
		SpringApplication.run(PfeUgbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{

		Client cl1 = new Client(null, "Demba", "44334433", "demba12"," demba@gmail.com" );
		Client cl2 = new Client(null, "Samba", "44884433", "samba14"," samba@gmail.com" );
		clientRepository.saveAll(Arrays.asList(cl1, cl2));

		// Produit prod1 = new Produit(null, "lait", "lait concentré", (double) 123);
		// Produit prod2 = new Produit(null, "viande", "viande haché", (double) 1232);
		// produitRepository.saveAll(Arrays.asList( prod1, prod2));
		// Enregistrer des produits
		List<Produit> produits = produitRepository.saveAll(Arrays.asList(
			new Produit(null, "lait", "lait concentré", (double) 123),
			new Produit(null, "viande", "viande haché", (double) 1232)
		));

		Livreur liv1 = new Livreur(null, "Moussa", "moussa@gmail.com", "8877665", "toyota");
		Livreur liv2 = new Livreur(null, "aly", "aly@gmail.com", "33221165", "helux");
		livreurRepository.saveAll(Arrays.asList(liv1, liv2));

		// Commande com1 = new Commande( Statut.ANNULEE, cl1);
		// Commande com2 = new Commande( Statut.LIVREE, cl2);
		// commandeRepository.saveAll(Arrays.asList(com1, com2));
		// Créer et enregistrer la commande avec les produits
		List<Commande> commandes = Arrays.asList(
		new Commande(StatutCommande.ANNULEE, cl1),
		new Commande(StatutCommande.LIVREE, cl2)
		);
		// Associer les produits à chaque commande individuellement
		for (Commande commande : commandes) {
			commande.setProduits(produits);
		}
		// Enregistrer les commandes avec les produits associés
		commandeRepository.saveAll(commandes);

		Notifications not1 = new Notifications( TypeNotification.ANNULATION_COMMANDE,StatutNotification.ENVOYEE);
		Notifications not2 = new Notifications( TypeNotification.LIVRAISON_EFFECTUÉE,StatutNotification.IGNOREE);
		notificationRepository.saveAll(Arrays.asList(not1, not2));


		
	}

}
