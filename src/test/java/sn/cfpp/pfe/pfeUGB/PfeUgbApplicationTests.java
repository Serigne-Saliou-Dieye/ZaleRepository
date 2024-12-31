package sn.cfpp.pfe.pfeUGB;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import sn.cfpp.pfe.pfeUGB.model.Commande;
import sn.cfpp.pfe.pfeUGB.model.Livraison;
import sn.cfpp.pfe.pfeUGB.statut.StatutCommande;
import sn.cfpp.pfe.pfeUGB.statut.StatutLivraison;

@SpringBootTest
class PfeUgbApplicationTests {

	@Test
	void contextLoads() {
	}

	 @Test
    void testSynchronisationStatutCommandeEtLivraison() {
        // Créer une commande et une livraison
        Commande commande = new Commande();
        Livraison livraison = new Livraison();

        // Associer la livraison à la commande
        commande.setLivraison(livraison);

        // Modifier le statut de la commande
        commande.setStatutCmd(StatutCommande.TRAITEE);

        // Vérifier que le statut de la livraison a été mis à jour
        assertEquals(StatutLivraison.EN_COURS, livraison.getStatutLivraison(),
                "Le statut de la livraison doit être mis à jour automatiquement à EN_COURS");

        // Modifier le statut de la livraison
        // livraison.setStatutLivraison(StatutLivraison.LIVREE);

        // // Vérifier que le statut de la commande a été mis à jour
        // assertEquals(StatutCommande.LIVREE, commande.getStatutCmd(),
        //         "Le statut de la commande doit être mis à jour automatiquement à LIVREE");
    }
}
