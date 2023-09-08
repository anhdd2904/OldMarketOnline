package com.due.oldmarket.service;

import com.due.oldmarket.dto.NotificationDTO;
import com.due.oldmarket.model.Notification;

import java.util.List;

public interface NotificationService {
    void save (Notification notification);
    List<Notification> findByIdUser (Long idUser);
    List<Notification> findByIdUserSale (Long idUserSale);
    Notification update (Long id);
    NotificationDTO convert (Notification notification);
}
