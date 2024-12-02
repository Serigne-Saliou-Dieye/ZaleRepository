package sn.cfpp.pfe.pfeUGB.security.entite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
   
    private String username;
    private String email;
    private Roles roles;

}
