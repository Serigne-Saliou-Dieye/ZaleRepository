// package sn.cfpp.pfe.pfeUGB.securities.entities;

// import java.util.ArrayList;
// import java.util.Collection;

// import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.fasterxml.jackson.annotation.JsonProperty;

// import jakarta.persistence.*;
// import lombok.*;
// import sn.cfpp.pfe.pfeUGB.securities.AppRole;

// @Entity
// @Data
// @AllArgsConstructor
// @NoArgsConstructor
// public class AppUser {
//     @Id 
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//     private String username;
//     @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//     @JsonIgnore
//     private String password;
//     @ManyToMany(fetch = FetchType.EAGER)
//     private Collection<AppRole> appRoles = new ArrayList<>();

// }
