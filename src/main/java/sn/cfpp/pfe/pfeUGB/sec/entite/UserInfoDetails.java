package sn.cfpp.pfe.pfeUGB.sec.entite;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

public class UserInfoDetails implements UserDetails {

    private String username;
    private String password;
    private List<SimpleGrantedAuthority> authorities;

    public UserInfoDetails(UserInfo userInfo) {
        // Assumer que 'name' dans UserInfo représente le nom d'utilisateur
        this.username = userInfo.getName();  // Assuming 'name' is used as 'username'
        this.password = userInfo.getPassword();

        // Supposons que userInfo.getRoles() renvoie une chaîne contenant des rôles séparés par des virgules
        this.authorities = List.of(userInfo.getRoles().split(","))
                .stream()
                .map(role -> new SimpleGrantedAuthority(((String) role).trim()))  // Enlever les espaces blancs autour du rôle
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
        return username;
    }

    // Méthodes supplémentaires héritées de UserDetails

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
}

