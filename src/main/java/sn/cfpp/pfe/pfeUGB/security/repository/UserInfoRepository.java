package sn.cfpp.pfe.pfeUGB.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sn.cfpp.pfe.pfeUGB.security.entite.Roles;
import sn.cfpp.pfe.pfeUGB.security.entite.UserInfos;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfos, Long> {
    Optional<UserInfos> findByEmail(String email); // Use 'email' if that is the correct field for login

    // List<UserInfos> findByRole(Roles roles);

    List<UserInfos> findByUsername(String username);
}

