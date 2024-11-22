package sn.cfpp.pfe.pfeUGB.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.cfpp.pfe.pfeUGB.security.entite.UserInfos;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Livreur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idLivreur;
    @NotBlank(message = "Nom is mandatory")
    private String nomLiv;
    @Email(message = "Email should be valid")
    private String emailLiv;
    private String telephoneLiv;
    private String vehiculeLiv, imageLiv;
     
    @JsonIgnore
    @OneToMany(mappedBy ="livreur", cascade = CascadeType.ALL)
    private List<Livraison> livraison;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfos userLivreur;


    // public Livreur() {
    // }

    // public Livreur(Long idLivreur, String nomLiv, String emailLiv, String telephoneLiv, String vehiculeLiv, UserInfo userLivreur) {
    //     super();
    //     this.idLivreur = idLivreur;
    //     this.nomLiv = nomLiv;
    //     this.emailLiv = emailLiv;
    //     this.telephoneLiv = telephoneLiv;
    //     this.vehiculeLiv = vehiculeLiv;
    //     this.userLivreur = userLivreur;
    // }


    // public Long getIdLivreur() {
    //     return this.idLivreur;
    // }

    // public void setIdLivreur(Long idLivreur) {
    //     this.idLivreur = idLivreur;
    // }

    // public String getNomLiv() {
    //     return this.nomLiv;
    // }

    // public void setNomLiv(String nomLiv) {
    //     this.nomLiv = nomLiv;
    // }

    // public String getEmailLiv() {
    //     return this.emailLiv;
    // }

    // public void setEmailLiv(String emailLiv) {
    //     this.emailLiv = emailLiv;
    // }

    // public String getTelephoneLiv() {
    //     return this.telephoneLiv;
    // }

    // public void setTelephoneLiv(String telephoneLiv) {
    //     this.telephoneLiv = telephoneLiv;
    // }

    // public String getVehiculeLiv() {
    //     return this.vehiculeLiv;
    // }

    // public void setVehiculeLiv(String vehiculeLiv) {
    //     this.vehiculeLiv = vehiculeLiv;
    // }
    


    // public List<Livraison> getLivraison() {
    //     return this.livraison;
    // }

    // public void setLivraison(List<Livraison> livraison) {
    //     this.livraison = livraison;
    // }

    // public UserInfo getUserLivreur() {
    //     return this.userLivreur;
    // }

    // public void setUserLivreur(UserInfo userLivreur) {
    //     this.userLivreur = userLivreur;
    // }

    
    

}

   