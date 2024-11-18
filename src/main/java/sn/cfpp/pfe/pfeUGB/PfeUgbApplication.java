package sn.cfpp.pfe.pfeUGB;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import sn.cfpp.pfe.pfeUGB.model.*;
import sn.cfpp.pfe.pfeUGB.repositories.*;
import sn.cfpp.pfe.pfeUGB.sec.entite.Roles;
import sn.cfpp.pfe.pfeUGB.sec.entite.UserInfo;
import sn.cfpp.pfe.pfeUGB.sec.repository.UserInfoRepository;
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
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;


	public static void main(String[] args) {
		SpringApplication.run(PfeUgbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{

		
	
		// Enregistrer des produits
		List<Produit> produits = produitRepository.saveAll(Arrays.asList(
			new Produit(null, "lait", "lait concentré", 123.0),
			new Produit(null, "viande", "viande haché", 1232.0)
		));

		// Enregistrer les UserInfo
		// Enregistrer les UserInfo avec mot de passe crypté
		UserInfo userInfo1 = new UserInfo(null, "Moussa", "moussa@gmail.com", passwordEncoder.encode("1234"), Roles.ROLE_LIVREUR, null, null);
		UserInfo userInfo3 = new UserInfo(null, "Moussa", "moussa@gmail.com", passwordEncoder.encode("1234"), Roles.ROLE_CLIENT, null, null);
		UserInfo userInfo2 = new UserInfo(null, "Saliou", "moussa@gmail.com", passwordEncoder.encode("admin"), Roles.ROLE_ADMIN, null, null);

userInfoRepository.saveAll(Arrays.asList(userInfo1, userInfo2, userInfo3));


		// Enregistrer des clients
		Client cl1 = new Client(null, "Demba", "44334433", "demba12", "demba@gmail.com", null, null, null, userInfo1);
		Client cl2 = new Client(null, "Samba", "44884433", "samba14", "samba@gmail.com", null, null, null, userInfo3);
		clientRepository.saveAll(Arrays.asList(cl1, cl2));
		
	
		// Enregistrer les livreurs
		Livreur liv1 = new Livreur(null, "Moussa", "moussa@gmail.com", "8877665", "toyota", null, null, userInfo1);
		Livreur liv2 = new Livreur(null, "aly", "aly@gmail.com", "33221165", "helux", null, null, userInfo2);
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
