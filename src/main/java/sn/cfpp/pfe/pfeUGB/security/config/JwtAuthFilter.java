package sn.cfpp.pfe.pfeUGB.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sn.cfpp.pfe.pfeUGB.security.entite.UserInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
    private final JwtService jwtService;
    private final UserInfoService userDetailsService;

    @Autowired
    public JwtAuthFilter(JwtService jwtService, @Lazy UserInfoService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            try {
                String username = jwtService.extractUsername(jwt);
                logger.info("Token JWT reçu pour l'utilisateur : {}", username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (jwtService.validateToken(jwt, userDetails)) {
                        // Créer un objet d'authentification avec les détails de la requête
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        // Enrichir les détails d'authentification avec les informations de la requête
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        logger.info("Authentification réussie pour l'utilisateur : {}", username);
                    } else {
                        logger.warn("Token invalide pour l'utilisateur : {}", username);
                    }
                }
            } catch (Exception e) {
                logger.error("Erreur lors de la validation du JWT : {}", e.getMessage());
            }
        } else {
            logger.warn("Aucun token ou token invalide.");
        }

        // Continuer la chaîne de filtres
        filterChain.doFilter(request, response);
    }
}