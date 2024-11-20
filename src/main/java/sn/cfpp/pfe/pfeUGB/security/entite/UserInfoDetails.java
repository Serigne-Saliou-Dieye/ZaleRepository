package sn.cfpp.pfe.pfeUGB.security.entite;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserInfoDetails implements UserDetails {

    private String email;  // Utilisation de l'email au lieu de username
    private String password;
    private List<SimpleGrantedAuthority> authorities;

    public UserInfoDetails(UserInfos userInfo) {
        // Assumer que 'email' dans UserInfo représente l'email de l'utilisateur
        this.email = userInfo.getEmail();  // 'username' devient 'email'
        this.password = userInfo.getPassword();

        // Utilisation du stream() de Roles pour récupérer les rôles sous forme d'énumérations
        this.authorities = Roles.stream()
                .filter(role -> userInfo.getRoles().contains(role))  // Filtrer les rôles qui existent dans l'utilisateur
                .map(role -> new SimpleGrantedAuthority(role.name()))  // Convertir chaque rôle en une authority
                .collect(Collectors.toList());
    }

    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;  // Retourner l'email comme 'username'
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
        return true;
    }

    public static UserDetails loadUserByUsername(String email) {
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }
}
