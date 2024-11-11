// package sn.cfpp.pfe.pfeUGB.model;

// import jakarta.persistence.CascadeType;
// import jakarta.persistence.Entity;
// import jakarta.persistence.EnumType;
// import jakarta.persistence.Enumerated;
// import jakarta.persistence.OneToOne;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import sn.cfpp.pfe.pfeUGB.security.Role;

// @Entity
// @Data
// @AllArgsConstructor
// @NoArgsConstructor
// public class User {
//     private Long idUser;
//     private String username;
//     private String password;

//     @Enumerated(EnumType.STRING)
//     private Role role = Role.R_CLIENT;

//     @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//     private Client client;

//     @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//     private Livreur livreur;


//     // public User() {
//     // }


//     // public User(Long idUser, String username, String password, Role role, Client client, Livreur livreur) {
//     //     this.idUser = idUser;
//     //     this.username = username;
//     //     this.password = password;
//     //     this.role = role;
//     //     this.client = client;
//     //     this.livreur = livreur;
//     // }


//     // public Long getIdUser() {
//     //     return this.idUser;
//     // }

//     // public void setIdUser(Long idUser) {
//     //     this.idUser = idUser;
//     // }

//     // public String getUsername() {
//     //     return this.username;
//     // }

//     // public void setUsername(String username) {
//     //     this.username = username;
//     // }

//     // public String getPassword() {
//     //     return this.password;
//     // }

//     // public void setPassword(String password) {
//     //     this.password = password;
//     // }

//     // public Role getRole() {
//     //     return this.role;
//     // }

//     // public void setRole(Role role) {
//     //     this.role = role;
//     // }

//     // public Client getClient() {
//     //     return this.client;
//     // }

//     // public void setClient(Client client) {
//     //     this.client = client;
//     // }

//     // public Livreur getLivreur() {
//     //     return this.livreur;
//     // }

//     // public void setLivreur(Livreur livreur) {
//     //     this.livreur = livreur;
//     // }


// }
