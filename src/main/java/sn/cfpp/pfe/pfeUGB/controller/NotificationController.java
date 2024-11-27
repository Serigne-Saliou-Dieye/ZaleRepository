package sn.cfpp.pfe.pfeUGB.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.management.Notification;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sn.cfpp.pfe.pfeUGB.model.Notifications;
import sn.cfpp.pfe.pfeUGB.repositories.NotificationRepository;
import sn.cfpp.pfe.pfeUGB.statut.StatutNotification;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:3000")

public class NotificationController {

    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @PostMapping
    public Notifications createNotification(@RequestBody Notifications notification) {
        return  notificationRepository.save(notification);
    }

    @GetMapping
    public Iterable<Notifications> getAllNotification(){
        return notificationRepository.findAll(); 
    }

    @GetMapping("/{id}")
    public Optional<Notifications> getCommandeParId(@PathVariable Long id) {
        return notificationRepository.findById(id);
    }

    
    @PutMapping("/{id}")
    public Notifications updateNotification(@PathVariable Long id, @RequestBody Notifications updateNotification){
        return notificationRepository.findById(id)
        .map(notification -> {
            notification = updateNotification;
            return notificationRepository.save(notification);
        })
        .orElseThrow(() -> new RuntimeException("notification non trouvé"));
    }
    
    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable Long id) {
        notificationRepository.deleteById(id);
    }

    @GetMapping("/recherche")
    public ResponseEntity<List<Notifications>> rechercherParDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateNotification) {
        List<Notifications> notifications = notificationRepository.findByDateNotification(dateNotification);  // Utiliser "findByDateCmd" ici
        return new ResponseEntity<>(notifications, HttpStatus.OK);  
    }

    @GetMapping("/statutNotif/{statutNotification}")
    public List<Notifications> getNotificationsByStatut(@PathVariable StatutNotification statutNotification) {
        return notificationRepository.findByStatutNotification(statutNotification);
    }  

}
