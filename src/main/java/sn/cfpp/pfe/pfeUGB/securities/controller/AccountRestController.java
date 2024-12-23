// package sn.cfpp.pfe.pfeUGB.securities.controller;

// import java.util.List;

// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import lombok.Data;
// import sn.cfpp.pfe.pfeUGB.securities.AppRole;
// import sn.cfpp.pfe.pfeUGB.securities.entities.AppUser;
// import sn.cfpp.pfe.pfeUGB.securities.service.AccountService;

// @RestController
// @RequestMapping("/api/auth")
// public class AccountRestController {
//     private AccountService accountService;

//     public AccountRestController(AccountService accountService) {
//         this.accountService = accountService;
//     }

//     @GetMapping()
//     public List<AppUser> appUsers(){
//         return accountService.listUsers();
//     }

//     @PostMapping("/users")
//     public AppUser saveUser(@RequestBody AppUser appUser){
//         return accountService.addNewUser(appUser);
//     }

//     @PostMapping("/roles")
//     public AppRole saveRole(@RequestBody AppRole appRole){
//         return accountService.addNewRole(appRole);
//     }

//     @PostMapping("/addRoleToUser")
//     public void addRoleToUser(@RequestBody RoleUserForm roleUserForm){
//         accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRoleName());
//     }

// }

// @Data
// class RoleUserForm{
//     private String username;
//     private String roleName;
// }
