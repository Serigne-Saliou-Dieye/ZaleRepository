package sn.cfpp.pfe.pfeUGB.model;
import jakarta.persistence.*;

@Entity
public class Client{

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long idCl;
        private String nomCl, telephoneCl, adresseCl , emailCl;


    public Client() {}

    public Client(Long idCl, String nomCl, String telephoneCl, String adresseCl, String emailCl) {
        super();
        this.idCl = idCl;
        this.nomCl = nomCl;
        this.telephoneCl = telephoneCl;
        this.adresseCl = adresseCl;
        this.emailCl = emailCl;
    }


    public Long getIdCl() {
        return this.idCl;
    }

    public void setIdCl(Long idCl) {
        this.idCl = idCl;
    }

    public String getNomCl() {
        return this.nomCl;
    }

    public void setNomCl(String nomCl) {
        this.nomCl = nomCl;
    }

    public String getTelephoneCl() {
        return this.telephoneCl;
    }

    public void setTelephoneCl(String telephoneCl) {
        this.telephoneCl = telephoneCl;
    }

    public String getAdresseCl() {
        return this.adresseCl;
    }

    public void setAdresseCl(String adresseCl) {
        this.adresseCl = adresseCl;
    }

    public String getEmailCl() {
        return this.emailCl;
    }

    public void setEmailCl(String emailCl) {
        this.emailCl = emailCl;
    }

    

 }