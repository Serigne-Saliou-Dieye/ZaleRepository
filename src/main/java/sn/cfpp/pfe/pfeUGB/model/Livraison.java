package sn.cfpp.pfe.pfeUGB.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.type.descriptor.java.LocalDateTimeJavaType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import sn.cfpp.pfe.pfeUGB.statut.StatutCommande;
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

    @JsonIgnore
    // @JsonManagedReference // Indique que c'est la partie "gérante" de la relation
    @OneToOne
    @JoinColumn(name = "commande_id" , referencedColumnName = "idCmd")
    private Commande commande;  

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "livreur_id")
    private Livreur livreur;

    // Ajoutez une liste de produits si nécessaire
    @Transient // Utilisez @Transient si vous ne voulez pas que cela soit persisté dans la base de données
    private List<Produit> produits;

    // Constructeurs
    public Livraison() {
        this.dateDepart = LocalDateTime.now();  // Initialiser la date de départ à la date actuelle
    }

    private boolean isUpdatingStatus = false;



    public Livraison(StatutLivraison statutLivraison, Livreur livreur) {
        this.statutLivraison = statutLivraison;
        this.livreur = livreur;
        this.dateDepart = LocalDateTime.now(); // Initialise la date de départ à l'instant actuel

        if (statutLivraison == StatutLivraison.LIVREE) {
            this.dateArrivee = LocalDateTime.now(); // Mettre la date d'arrivée si le statut est LIVREE
        }
    }

    @PrePersist
    protected void onCreate() {
        this.dateDepart = LocalDateTime.now(); // Date du jour
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

    // public void setStatutLivraison(StatutLivraison statutLivraison) {
    //     this.statutLivraison = statutLivraison;

    //      // Mettre à jour automatiquement la date d'arrivée si le statut est "LIVREE"
    //     if (statutLivraison == StatutLivraison.LIVREE) {
    //         this.dateArrivee = LocalDateTime.now();
    //     }
    // }

     public void setStatutLivraison(StatutLivraison statutLivraison) {
        if (this.isUpdatingStatus) {
            return; // Empêche les mises à jour infinies
        }
        this.statutLivraison = statutLivraison;

        // Mettre à jour automatiquement la date d'arrivée si le statut est "LIVREE"
        if (statutLivraison == StatutLivraison.LIVREE) {
            this.dateArrivee = LocalDateTime.now();
        }

        // Synchroniser le statut de la commande si elle existe
        if (this.commande != null) {
            switch (statutLivraison) {
                case EN_ATTENTE:
                    commande.setStatutCmd(StatutCommande.EN_ATTENTE);
                    break;
                case EN_COURS:
                    commande.setStatutCmd(StatutCommande.TRAITEE);
                    break;
                case LIVREE:
                    commande.setStatutCmd(StatutCommande.LIVREE);
                    break;
                case ANNULEE:
                    commande.setStatutCmd(StatutCommande.ANNULEE);
                    break;
                default:
                    throw new IllegalArgumentException("StatutLivraison inconnu : " + statutLivraison);
            }
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

    public void setCommande(Commande commande) {
        this.commande = commande;

        // Synchroniser le statut de la livraison avec celui de la commande
        if (commande != null) {
            switch (commande.getStatutCmd()) {
                case EN_ATTENTE:
                    this.statutLivraison = StatutLivraison.EN_ATTENTE;
                    break;
                case TRAITEE:
                    this.statutLivraison = StatutLivraison.EN_COURS;
                    break;
                case LIVREE:
                    this.statutLivraison = StatutLivraison.LIVREE;
                    break;
                case ANNULEE:
                    this.statutLivraison = StatutLivraison.ANNULEE;
                    break;
                default:
                    throw new IllegalArgumentException("StatutCommande inconnu : " + commande.getStatutCmd());
            }
        }
    }


    public Livreur getLivreur() {
        return this.livreur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }


    public List<Produit> getProduits() {
        return this.produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public boolean isIsUpdatingStatus() {
        return this.isUpdatingStatus;
    }

    public boolean getIsUpdatingStatus() {
        return this.isUpdatingStatus;
    }

    public void setIsUpdatingStatus(boolean isUpdatingStatus) {
        this.isUpdatingStatus = isUpdatingStatus;
    }
    
}
