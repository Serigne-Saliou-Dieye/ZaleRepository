package sn.cfpp.pfe.pfeUGB.security.entite;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Roles {
 
    ADMIN,
    CLIENT,
    LIVREUR;

     // Méthode pour obtenir un stream de toutes les valeurs de l'énumération
     public static Stream<Roles> stream() {
        return Arrays.stream(Roles.values());  // Retourne un stream de toutes les valeurs de l'énumération
    }
    

   
}
