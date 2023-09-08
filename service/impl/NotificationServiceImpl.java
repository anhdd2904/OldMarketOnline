package com.due.oldmarket.service.impl;

import com.due.oldmarket.dto.NotificationDTO;
import com.due.oldmarket.dto.ProductDTO;
import com.due.oldmarket.model.Comment;
import com.due.oldmarket.model.Notification;
import com.due.oldmarket.model.Product;
import com.due.oldmarket.reponsitory.NotificationReponsitory;
import com.due.oldmarket.service.NotificationService;
import com.due.oldmarket.service.ProductService;
import com.due.oldmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    NotificationReponsitory notificationReponsitory;
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Override
    public void save(Notification notification) {
        notificationReponsitory.save(notification);
    }

    @Override
    public List<Notification> findByIdUser(Long idUser) {
        List<Notification> notificationList = notificationReponsitory.findAll();
        List<Notification> notificationList1 = new ArrayList<>();
        for (Notification notification : notificationList) {
            if (notification.getUser() != null && notification.getUser().getIdUser() == idUser) {
                notificationList1.add(notification);
                notificationReponsitory.save(notification);
            }
        }
        return notificationList1;
    }

    // lấy thông báo theo id ngừi ba
    @Override
    public List<Notification> findByIdUserSale(Long idUser) {
        List<Notification> notificationList = notificationReponsitory.findAll();
        List<Notification> notificationList1 = new ArrayList<>();
        for (Notification notification : notificationList) {
            if (notification.getProduct() != null && notification.getProduct().getUser().getIdUser() == idUser) {
                notificationList1.add(notification);
                notificationReponsitory.save(notification);
            }
        }
        return notificationList1;
    }

    @Override
    public Notification update(Long id) {
        Notification notification = notificationReponsitory.findById(id).orElse(null);
        notification.setStatus("0");
        notificationReponsitory.save(notification);
        Notification notification1 = notificationReponsitory.findById(id).orElse(null);

        return notification1;
    }

    @Override
    public NotificationDTO convert(Notification notification) {

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        if (notification.getProduct() != null) {
            ProductDTO product = productService.convertToProductDTO(notification.getProduct());
            notificationDTO.setProductDTO(product);
        }else {
            notificationDTO.setProductDTO(null);
        }
        notificationDTO.setUserDTO(userService.convert(notification.getUser()));
        notificationDTO.setStatus(notification.getStatus());
        notificationDTO.setType(notification.getType());
        notificationDTO.setMessage(notification.getMessage());
        return notificationDTO;
    }
}
