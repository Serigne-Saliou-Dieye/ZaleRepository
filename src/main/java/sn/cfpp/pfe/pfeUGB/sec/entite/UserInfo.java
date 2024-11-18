package sn.cfpp.pfe.pfeUGB.sec.entite;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.cfpp.pfe.pfeUGB.model.Client;
import sn.cfpp.pfe.pfeUGB.model.Livreur;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @NotEmpty(message = "Le mot de passe ne peut pas être vide")
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private Roles roles = Roles.ROLE_CLIENT;

   @JsonIgnore
   @OneToOne(mappedBy = "userClient", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
   private Client client;

   @JsonIgnore
   @OneToOne(mappedBy = "userLivreur", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
   private Livreur livreur;

}

