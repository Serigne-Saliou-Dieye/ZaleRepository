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
import sn.cfpp.pfe.pfeUGB.security.entite.Roles;
import sn.cfpp.pfe.pfeUGB.security.entite.UserInfos;
import sn.cfpp.pfe.pfeUGB.security.repository.UserInfoRepository;
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
		UserInfos userInfo1 = new UserInfos(null, "Moussa", "moussa@gmail.com", passwordEncoder.encode("1234"), Roles.ROLE_LIVREUR, false, null, null);
		UserInfos userInfo3 = new UserInfos(null, "Modou", "modou@gmail.com", passwordEncoder.encode("1234"), Roles.ROLE_ADMIN, true, null, null);
		UserInfos userInfo2 = new UserInfos(null, "Saliou", "saliou@gmail.com", passwordEncoder.encode("admin"), Roles.ROLE_CLIENT, true, null, null);
		UserInfos userInfo4 = new UserInfos(null, "Aly", "aly@gmail.com", passwordEncoder.encode("admin"), Roles.ROLE_LIVREUR, true, null, null);
		UserInfos userInfo5 = new UserInfos(null, "Abdou", "abdou@gmail.com", passwordEncoder.encode("admin"), Roles.ROLE_CLIENT, true, null, null);
		UserInfos userInfo6 = new UserInfos(null, "Abou", "abou@gmail.com", passwordEncoder.encode("admin"), Roles.ROLE_CLIENT, true, null, null);

		userInfoRepository.saveAll(Arrays.asList(userInfo1, userInfo2, userInfo3, userInfo4, userInfo5, userInfo6));

		
		// Enregistrer des clients
		Client cl1 = new Client(null, "Demba", "44334433", "demba12", "demba@gmail.com", null, null, null, userInfo1);
		Client cl2 = new Client(null, "Saliou", "44884433", "samba14", "saliou@gmail.com", null, null, null, userInfo2);
		Client cl3 = new Client(null, "Aw", "44884433", "aw12", "aw@gmail.com", null, null, null, userInfo5);
		Client cl4 = new Client(null, "Teuw", "44884433", "teuw14", "teuw@gmail.com", null, null, null, userInfo3);
		Client cl5 = new Client(null, "Ba", "44884433", "ba123", "ba@gmail.com", null, null, null, userInfo4);
		Client cl6 = new Client(null, "Ball", "44884433", "ball123", "ball@gmail.com", null, null, null, userInfo6);
		clientRepository.saveAll(Arrays.asList(cl1, cl2, cl3, cl4, cl5, cl6));
			
	
		// Enregistrer les livreurs
		Livreur liv1 = new Livreur(null, "Moussa", "moussa@gmail.com", "8877665", "toyota", null, null, userInfo1);
		Livreur liv2 = new Livreur(null, "aly", "aly@gmail.com", "33221165", "helux", null, null, userInfo2);
		Livreur liv3 = new Livreur(null, "seck", "seck@gmail.com", "33221165", "helux", null, null, userInfo3);
		Livreur liv4 = new Livreur(null, "sall", "sall@gmail.com", "33221165", "helux", null, null, userInfo4);
		livreurRepository.saveAll(Arrays.asList(liv1, liv2, liv3, liv4));
	
		// Enregistrer les commandes avec les produits associés
		Commande com1 = new Commande(StatutCommande.ANNULEE, cl1);
		Commande com2 = new Commande(StatutCommande.ANNULEE, cl5);
		Commande com3 = new Commande(StatutCommande.EN_ATTENTE, cl2);
		Commande com4 = new Commande(StatutCommande.EN_ATTENTE, cl3);
		Commande com5 = new Commande(StatutCommande.LIVREE, cl1);
		Commande com6 = new Commande(StatutCommande.EN_ATTENTE, cl3);
		Commande com7 = new Commande(StatutCommande.TRAITEE, cl5);
		Commande com8 = new Commande(StatutCommande.TRAITEE, cl6);
		Commande com9 = new Commande(StatutCommande.TRAITEE, cl4);
		com1.setProduits(produits);
		com2.setProduits(produits);
		com3.setProduits(produits);
		com4.setProduits(produits);
		com5.setProduits(produits);
		com6.setProduits(produits);
		com7.setProduits(produits);
		com8.setProduits(produits);
		com9.setProduits(produits);
		commandeRepository.saveAll(Arrays.asList(com1, com2, com3, com4, com5, com6, com7, com8, com9));
	
		
		// Enregistrer les livraisons et les associer aux commandes
		Livraison livraison1 = new Livraison(StatutLivraison.EN_COURS, liv1);
		Livraison livraison2 = new Livraison(StatutLivraison.LIVREE, liv2);
		Livraison livraison3 = new Livraison(StatutLivraison.LIVREE, liv3);
		Livraison livraison4 = new Livraison(StatutLivraison.LIVREE, liv4);
		livraison1.setCommande(com1);
		livraison2.setCommande(com2);
		livraison3.setCommande(com3);
		livraison4.setCommande(com4);
		livraisonRepository.saveAll(Arrays.asList(livraison1, livraison2, livraison3, livraison4));


		// Enregistrer les notifications
		Notifications not1 = new Notifications(TypeNotification.ANNULATION_COMMANDE, StatutNotification.ENVOYEE, com1, cl1);
		Notifications not2 = new Notifications(TypeNotification.LIVRAISON_EFFECTUÉE, StatutNotification.IGNOREE, com2, cl2);
		notificationRepository.saveAll(Arrays.asList(not1, not2));
	
	}

}
