package sn.cfpp.pfe.pfeUGB.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idProd;
    private String nomProd, descriptionProd;
    private int prixProd;

    public Produit() {
    }


    public Produit(Long idProd, String nomProd, String descriptionProd, int prixProd) {
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

    public int getprixProd() {
        return this.prixProd;
    }

    public void setprixProd(int prixProd) {
        this.prixProd = prixProd;
    }

    

}
