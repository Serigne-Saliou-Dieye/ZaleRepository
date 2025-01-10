package sn.cfpp.pfe.pfeUGB.model;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sn.cfpp.pfe.pfeUGB.security.entite.UserInfos;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
// @EqualsAndHashCode(exclude = "userClient") // Exclure userClient pour éviter la récursion
public class Client{

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long idCl;
        @NotBlank(message = "Nom is mandatory")
        private String nomCl, telephoneCl, adresseCl ;
        @Email(message = "Email should be valid")
        private String emailCl;
        private String imageCl;
        private Double latitude;
        private Double longitude;
        @JsonIgnore
        @OneToMany(mappedBy = "client")
        private List<Commande> commandes;

        @JsonIgnore
        @OneToMany(mappedBy="clients")
        private List<Notifications> notification;

        @OneToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")  
        private UserInfos userClient;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Client)) return false;
            Client client = (Client) o;
            return Objects.equals(idCl, client.idCl);
        }

        @Override
        public int hashCode() {
            return Objects.hash(idCl);
        }


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