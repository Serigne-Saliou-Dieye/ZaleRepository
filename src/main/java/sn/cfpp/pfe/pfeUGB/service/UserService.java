// package sn.cfpp.pfe.pfeUGB.service;

// import java.util.Collections;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import sn.cfpp.pfe.pfeUGB.model.Client;
// import sn.cfpp.pfe.pfeUGB.model.Livreur;
// import sn.cfpp.pfe.pfeUGB.model.User;
// import sn.cfpp.pfe.pfeUGB.repositories.ClientRepository;
// import sn.cfpp.pfe.pfeUGB.repositories.LivreurRepository;
// import sn.cfpp.pfe.pfeUGB.repositories.UserRepository;
// import sn.cfpp.pfe.pfeUGB.security.Role;

// @Service
// public class UserService implements UserDetailsService {

//     @Autowired
//     private UserRepository userRepository;
    
//     @Autowired
//     private ClientRepository clientRepository;

//     @Autowired
//     private LivreurRepository livreurRepository;

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         User user = userRepository.findByUsername(username)
//             .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));    
//         return new org.springframework.security.core.userdetails.User(
//             user.getUsername(), user.getPassword(),
//             Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
//         );
//     }

//     public void assignEntityBasedOnRole(User user) {
//         if (user.getRole() == Role.R_LIVREUR) {
//             if (livreurRepository.findById(user.getIdUser()).isEmpty()) {
//                 Livreur livreur = new Livreur();
//                 livreur.setUser(user);
//                 livreurRepository.save(livreur);
//             }
//         } else if (user.getRole() == Role.R_CLIENT) {
//             if (clientRepository.findById(user.getIdUser()).isEmpty()) {
//                 Client client = new Client();
//                 client.setUser(user);
//                 clientRepository.save(client);
//             }
//         }
//     }
    
//     public User saveUser(User user) {
//         User savedUser = userRepository.save(user);
//         assignEntityBasedOnRole(savedUser);
//         return savedUser;
//     }

// }
