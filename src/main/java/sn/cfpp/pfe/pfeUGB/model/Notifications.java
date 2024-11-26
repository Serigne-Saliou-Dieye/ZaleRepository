package sn.cfpp.pfe.pfeUGB.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import sn.cfpp.pfe.pfeUGB.statut.StatutNotification;
import sn.cfpp.pfe.pfeUGB.statut.TypeNotification;

@Entity
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idNotification;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateNotification;

    @Enumerated(EnumType.STRING)
    private TypeNotification typeNotification = TypeNotification.NOUVELLE_COMMANDE;
    
    @Enumerated(EnumType.STRING)
    private StatutNotification statutNotification; // Vous pouvez définir un enum `Statut` pour limiter les valeurs possibles

    
    @ManyToOne
    @JoinColumn(name="commande_id")
    private Commande commande;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="client_id")
    private Client clients;


    public Notifications() {
    }

    public Notifications(TypeNotification typeNotification, StatutNotification statutNotification, 
    Commande commande, Client clients) {
        this.typeNotification = typeNotification;
        this.statutNotification = statutNotification;
        this.commande = commande;
        this.clients = clients;
    }
     
    @PrePersist
    protected void onCreate() {
        this.dateNotification = new Date(); // Date et heure actuelles
    }


    public Long getIdNotification() {
        return this.idNotification;
    }

    public void setIdNotification(Long idNotification) {
        this.idNotification = idNotification;
    }

    public Date getDateNotification() {
        return this.dateNotification;
    }

    public void setDateNotification(Date dateNotification) {
        this.dateNotification = dateNotification;
    }

    public TypeNotification getTypeNotification() {
        return this.typeNotification;
    }

    public void setTypeNotification(TypeNotification typeNotification) {
        this.typeNotification = typeNotification;
    }

    public StatutNotification getStatutNotification() {
        return this.statutNotification;
    }

    public void setStatutNotification(StatutNotification statutNotification) {
        this.statutNotification = statutNotification;
    }


    public Commande getCommande() {
        return this.commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }


    public Client getClient() {
        return this.clients;
    }

    public void setClient(Client client) {
        this.clients = client;
    }

}
