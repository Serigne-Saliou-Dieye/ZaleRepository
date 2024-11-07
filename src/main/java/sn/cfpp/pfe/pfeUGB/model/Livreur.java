package sn.cfpp.pfe.pfeUGB.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Livreur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idLivreur;
    private String nomLiv, emailLiv, telephoneLiv, vehiculeLiv;
     
    @JsonIgnore
    @OneToMany(mappedBy ="livreur", cascade = CascadeType.ALL)
    private List<Livraison> livraison;


    public Livreur() {
    }

    public Livreur(Long idLivreur, String nomLiv, String emailLiv, String telephoneLiv, String vehiculeLiv) {
        super();
        this.idLivreur = idLivreur;
        this.nomLiv = nomLiv;
        this.emailLiv = emailLiv;
        this.telephoneLiv = telephoneLiv;
        this.vehiculeLiv = vehiculeLiv;
    }


    public Long getIdLivreur() {
        return this.idLivreur;
    }

    public void setIdLivreur(Long idLivreur) {
        this.idLivreur = idLivreur;
    }

    public String getNomLiv() {
        return this.nomLiv;
    }

    public void setNomLiv(String nomLiv) {
        this.nomLiv = nomLiv;
    }

    public String getEmailLiv() {
        return this.emailLiv;
    }

    public void setEmailLiv(String emailLiv) {
        this.emailLiv = emailLiv;
    }

    public String getTelephoneLiv() {
        return this.telephoneLiv;
    }

    public void setTelephoneLiv(String telephoneLiv) {
        this.telephoneLiv = telephoneLiv;
    }

    public String getVehiculeLiv() {
        return this.vehiculeLiv;
    }

    public void setVehiculeLiv(String vehiculeLiv) {
        this.vehiculeLiv = vehiculeLiv;
    }
    


    public List<Livraison> getLivraison() {
        return this.livraison;
    }

    public void setLivraison(List<Livraison> livraison) {
        this.livraison = livraison;
    }
   
    

}
