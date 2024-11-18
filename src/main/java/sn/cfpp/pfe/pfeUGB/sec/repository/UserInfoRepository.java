package sn.cfpp.pfe.pfeUGB.sec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sn.cfpp.pfe.pfeUGB.sec.entite.UserInfo;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByEmail(String email); // Use 'email' if that is the correct field for login

    List<UserInfo> findByNameContainingIgnoreCase(String name);
}

