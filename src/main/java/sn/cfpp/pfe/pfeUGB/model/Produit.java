package sn.cfpp.pfe.pfeUGB.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idProd;
    private String nomProd, descriptionProd;
    private Double prixProd;

    @JsonIgnore
    @ManyToMany(mappedBy = "produits")
    private List<Commande> commandes;

    public Produit() {
    }


    public Produit(Long idProd, String nomProd, String descriptionProd, Double prixProd) {
        super();
        this.idProd = idProd;
        this.nomProd = nomProd;
        this.descriptionProd = descriptionProd;
        this.prixProd = prixProd;
    }


    public Long getIdProd() {
        return this.idProd;
    }

    public void setIdProd(Long idProd) {
        this.idProd = idProd;
    }

    public String getNomProd() {
        return this.nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public String getDescriptionProd() {
        return this.descriptionProd;
    }

    public void setDescriptionProd(String descriptionProd) {
        this.descriptionProd = descriptionProd;
    }

    public Double getprixProd() {
        return this.prixProd;
    }

    public void setprixProd(Double prixProd) {
        this.prixProd = prixProd;
    }


    public Double getPrixProd() {
        return this.prixProd;
    }

    public void setPrixProd(Double prixProd) {
        this.prixProd = prixProd;
    }

    public List<Commande> getCommandes() {
        return this.commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }
    

}
