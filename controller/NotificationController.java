package com.due.oldmarket.controller;

import com.due.oldmarket.dto.CommentDTO;
import com.due.oldmarket.dto.NotificationDTO;
import com.due.oldmarket.model.Notification;
import com.due.oldmarket.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(value = "/notification")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    //Lấy ra comment theosanrnr phẩm cuar người bán , gôm có thông báo người mua comment vàgwruiwr yêu cầu mua hàng
    @GetMapping(value = "/findByIdProduct/{idProduct}")
    public ResponseEntity<List<Notification>> findByIdProduct(@PathVariable Long idProduct) {
        List<Notification> notificationList = notificationService.findByIdUserSale(idProduct);
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    //Lấy ra comment theo ID USER nguoiwf mua ngwoguowif bán đã hủy yêu câu hoặc chấp nhận yêu cầu mua hàng
    @GetMapping(value = "/findByIdUser/{idUser}")
    public ResponseEntity<List<Notification>> findByIdUser(@PathVariable Long idUser) {
        List<Notification> notificationList = notificationService.findByIdUser(idUser);
        if (notificationList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    @PutMapping(value = "/updateStatus/{id}")
    public ResponseEntity<NotificationDTO> update(@PathVariable Long id){
        Notification notification = notificationService.update(id);
        NotificationDTO notificationDTO = notificationService.convert(notification);
        return new ResponseEntity<>(notificationDTO,HttpStatus.OK);
    }
}
