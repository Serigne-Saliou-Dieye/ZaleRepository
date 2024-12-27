package sn.cfpp.pfe.pfeUGB.security.entite;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

public class UserInfoDetails implements UserDetails {

    private Long id;
    private String email;  // Utilisation de l'email au lieu de username
    private String password;
    private boolean enabled; // Ajoutez un champ pour l'état
    private String role; 
    private String username;
    private List<GrantedAuthority> authorities;

    public UserInfoDetails(UserInfos userInfo) {
        this.id = userInfo.getId(); // Assurez-vous que l'ID est récupéré
        this.email = userInfo.getEmail();  // 'username' devient 'email'
        this.password = userInfo.getPassword();
        this.enabled = userInfo.isEnabled(); // Initialisez l'état ici
        this.username = userInfo.getUsername();
        this.role = userInfo.getRoles().name();


        // Les rôles de l'utilisateur sont directement récupérés sous forme d'énumérations
        this.authorities = userInfo.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))  // Convertir chaque rôle en authority
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

     // Ajoutez un getter pour le rôle
     public String getRole() {
        return role;
    }


    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;  // Retourner l'email comme 'username'
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled; // Retourne l'état depuis l'attribut
    }
}