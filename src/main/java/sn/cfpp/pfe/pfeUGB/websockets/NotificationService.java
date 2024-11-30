package sn.cfpp.pfe.pfeUGB.websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendNotification(String message) {
        System.out.println("Notification envoyée : " + message); // Log de vérification
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }

}
