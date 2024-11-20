package sn.cfpp.pfe.pfeUGB.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.cfpp.pfe.pfeUGB.security.entite.UserInfos;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client{

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long idCl;
        private String nomCl, telephoneCl, adresseCl , emailCl, imageCl;
        @JsonIgnore
        @OneToMany(mappedBy = "client")
        private List<Commande> commandes;

        @JsonIgnore
        @OneToMany(mappedBy="clients")
        private List<Notifications> notification;

        @OneToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private UserInfos userClient;


    // public Client() {}

  

    // public Client(Long idCl, String nomCl, String telephoneCl, String adresseCl, String emailCl, UserInfo userClient) {
    //     super();
    //     this.idCl = idCl;
    //     this.nomCl = nomCl;
    //     this.telephoneCl = telephoneCl;
    //     this.adresseCl = adresseCl;
    //     this.emailCl = emailCl;
    //     this.userClient = userClient;
       
    // }
    


    // public Long getIdCl() {
    //     return this.idCl;
    // }

    // public void setIdCl(Long idCl) {
    //     this.idCl = idCl;
    // }

    // public String getNomCl() {
    //     return this.nomCl;
    // }

    // public void setNomCl(String nomCl) {
    //     this.nomCl = nomCl;
    // }

    // public String getTelephoneCl() {
    //     return this.telephoneCl;
    // }

    // public void setTelephoneCl(String telephoneCl) {
    //     this.telephoneCl = telephoneCl;
    // }

    // public String getAdresseCl() {
    //     return this.adresseCl;
    // }

    // public void setAdresseCl(String adresseCl) {
    //     this.adresseCl = adresseCl;
    // }

    // public String getEmailCl() {
    //     return this.emailCl;
    // }

    // public void setEmailCl(String emailCl) {
    //     this.emailCl = emailCl;
    // }

    // public List<Commande> getCommandes() {
    //     return this.commandes;
    // }

    // public void setCommandes(List<Commande> commandes) {
    //     this.commandes = commandes;
    // }

    // public List<Notifications> getNotification() {
    //     return this.notification;
    // }

    // public void setNotification(List<Notifications> notification) {
    //     this.notification = notification;
    // }


    // public UserInfo getUserClient() {
    //     return this.userClient;
    // }

    // public void setUserClient(UserInfo userClient) {
    //     this.userClient = userClient;
    // }
    

 }