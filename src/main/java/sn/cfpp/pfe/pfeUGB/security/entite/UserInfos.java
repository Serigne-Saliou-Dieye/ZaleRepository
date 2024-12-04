package sn.cfpp.pfe.pfeUGB.security.entite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class UserInfos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Username is mandatory")
    private String username;
    
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(unique = true) // Garantie au niveau de la colonne
    private String email;

    @NotEmpty(message = "Le mot de passe ne peut pas être vide")
    // @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles roles = Roles.CLIENT;

    @Column(nullable = false)
    private boolean isEnabled = true; // Par défaut, le compte est actif.


   @JsonIgnore
   @OneToOne(mappedBy = "userClient", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
   private Client client;

   @JsonIgnore
   @OneToOne(mappedBy = "userLivreur", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
   private Livreur livreur;

}

