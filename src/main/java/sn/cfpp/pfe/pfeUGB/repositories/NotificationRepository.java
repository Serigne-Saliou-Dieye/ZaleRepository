package sn.cfpp.pfe.pfeUGB.repositories;


import java.util.Date;
import java.util.List;

import javax.management.Notification;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.cfpp.pfe.pfeUGB.model.Notifications;
import sn.cfpp.pfe.pfeUGB.statut.StatutNotification;

public interface NotificationRepository extends JpaRepository<Notifications, Long>{
    List<Notifications> findByDateNotification(Date dateNotification);
    List<Notifications> findByStatutNotification(StatutNotification statutNotification);


}
