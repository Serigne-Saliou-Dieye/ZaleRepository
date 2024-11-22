package sn.cfpp.pfe.pfeUGB.model;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import sn.cfpp.pfe.pfeUGB.statut.StatutCommande;

    @Entity
    public class Commande {

            @Id   
            @GeneratedValue(strategy = GenerationType.AUTO)
            private Long idCmd;
            @Temporal(TemporalType.DATE)
            private Date dateCmd;
            @Enumerated(EnumType.STRING)
            private StatutCommande statutCmd = StatutCommande.EN_ATTENTE;

            @ManyToOne
            @JoinColumn(name="client_id")
            private Client client; 

            @OneToOne(mappedBy = "commande", cascade = CascadeType.ALL)
            private Livraison livraison;

            @JsonIgnore
            @OneToMany(mappedBy="commande")
            private List<Notifications> notifications;


            @ManyToMany
            @JoinTable(
                name = "commande_produit",
                joinColumns = @JoinColumn(name = "commande_id"),
                inverseJoinColumns = @JoinColumn(name = "produit_id")
            )
            private List<Produit> produits;

    public Commande() {
    }

    // Constructeur sans la date, car elle est définie par défaut
    public Commande(StatutCommande statut, Client client) {
        this.statutCmd = statut;
        this.client = client;
    }
   
    // Méthode pour définir la date avant d'insérer en base de données
    @PrePersist
    protected void onCreate() {
        this.dateCmd = new Date(); // Date du jour
    }


    public Long getIdCmd() {
        return this.idCmd;
    }

    public void setIdCmd(Long idCmd) {
        this.idCmd = idCmd;
    }

    public Date getDateCmd() {
        return this.dateCmd;
    }

    public void setDateCmd(Date dateCmd) {
        this.dateCmd = dateCmd;
    }

    public StatutCommande getStatutCmd() {
        return this.statutCmd;
    }

    public void setStatutCmd(StatutCommande statutCmd) {
        this.statutCmd = statutCmd;
    }


    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Produit> getProduits() {
        return this.produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public Livraison getLivraison() {
        return this.livraison;
    }

    public void setLivraison(Livraison livraison) {
        this.livraison = livraison;
        // livraison.setCommande(this);
    }


    public List<Notifications> getNotifications() {
        return this.notifications;
    }

    public void setNotifications(List<Notifications> notifications) {
        this.notifications = notifications;
    }
            

}
