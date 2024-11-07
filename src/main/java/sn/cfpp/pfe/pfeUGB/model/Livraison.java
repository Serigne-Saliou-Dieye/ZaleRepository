package sn.cfpp.pfe.pfeUGB.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import sn.cfpp.pfe.pfeUGB.statut.StatutLivraison;

@Entity
public class Livraison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLivraison;
    @Enumerated(EnumType.STRING)
    private StatutLivraison statutLivraison = StatutLivraison.EN_COURS;

    private LocalDateTime dateDepart;
    private LocalDateTime dateArrivee;

    @OneToOne
    @JoinColumn(name = "commande_id" , referencedColumnName = "idCmd")
    private Commande commande;  

    @ManyToOne
    @JoinColumn(name = "livreur_id")
    private Livreur livreur;

    // Constructeurs
    public Livraison() {
        this.dateDepart = LocalDateTime.now();  // Initialiser la date de départ à la date actuelle
    }


    public Livraison(StatutLivraison statutLivraison, Livreur livreur) {
        this.statutLivraison = statutLivraison;
        this.livreur = livreur;
        this.dateDepart = LocalDateTime.now(); // Initialise la date de départ à l'instant actuel

        if (statutLivraison == StatutLivraison.LIVREE) {
            this.dateArrivee = LocalDateTime.now(); // Mettre la date d'arrivée si le statut est LIVREE
        }
    }


    public Long getIdLivraison() {
        return this.idLivraison;
    }

    public void setIdLivraison(Long idLivraison) {
        this.idLivraison = idLivraison;
    }

    public StatutLivraison getStatutLivraison() {
        return this.statutLivraison;
    }

    public void setStatutLivraison(StatutLivraison statutLivraison) {
        this.statutLivraison = statutLivraison;

         // Mettre à jour automatiquement la date d'arrivée si le statut est "LIVREE"
        if (statutLivraison == StatutLivraison.LIVREE) {
            this.dateArrivee = LocalDateTime.now();
        }
    }

    public LocalDateTime getDateDepart() {
        return this.dateDepart;
    }

    public void setDateDepart(LocalDateTime dateDepart) {
        this.dateDepart = dateDepart;
    }

    public LocalDateTime getDateArrivee() {
        return this.dateArrivee;
    }

    public void setDateArrivee(LocalDateTime dateArrivee) {
        this.dateArrivee = dateArrivee;
    }


    public Commande getCommande() {
        return this.commande;
    }

    public void setCommande(Commande commandes) {
        this.commande = commandes;
    }


    public Livreur getLivreur() {
        return this.livreur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }
    


}
