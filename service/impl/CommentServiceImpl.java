package com.due.oldmarket.service.impl;

import com.due.oldmarket.dto.CommentDTO;
import com.due.oldmarket.dto.UserDTO;
import com.due.oldmarket.exception.NoSuchCommentExistsException;
import com.due.oldmarket.exception.NoSuchProductExistsException;
import com.due.oldmarket.model.*;
import com.due.oldmarket.reponsitory.CommentReponsitory;
import com.due.oldmarket.reponsitory.FileReponsitory;
import com.due.oldmarket.reponsitory.ProductReponsitory;
import com.due.oldmarket.reponsitory.UserReponsitory;
import com.due.oldmarket.service.CommentService;
import com.due.oldmarket.service.NotificationService;
import com.due.oldmarket.service.ProductService;
import com.due.oldmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentReponsitory commentReponsitory;
    @Autowired
    UserReponsitory userReponsitory;
    @Autowired
    ProductReponsitory productReponsitory;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    FileReponsitory fileReponsitory;
    @Autowired
    NotificationService notificationService;

    public List<Comment> findAllById(Long idProduct) {
        /*Product product = productService.findById(idProduct);*/
        List<Comment> commentList = commentReponsitory.findAll();
        List<Comment> commentList1 = new ArrayList<>();
        for (Comment comment : commentList) {
            if (comment.getProduct().getIdProduct() == idProduct) {
                commentList1.add(comment);
            }
        }
        return commentList1;

    }

    public String save(Comment comment, Long idProduct, Long idUser) {
        /* User user = userReponsitory.findById(idUser).orElse(null);*/
        User user = userService.findById(idUser);
        /*Product product = productReponsitory.findById(idProduct).orElse(null);*/
        Product product = productService.findById(idProduct);
        comment.setUser(user);
        comment.setProduct(product);
        commentReponsitory.save(comment);


        Notification notification = new Notification();
        notification.setProduct(product);
        notification.setUser(product.getUser());
        notification.setStatus("1");
        notification.setType("comment-"+ idProduct);
        notification.setMessage(user.getFullName() + " vừa bình luận vào bài đăng sản phẩm " + product.getProductName() + " của bạn");

        notificationService.save(notification);


        return "comment successfully";
    }

    public String delete(Long idComment) {
        commentReponsitory.deleteById(idComment);
        return "delete comment successfully";
    }

    public UserDTO convert(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(user.getIdUser());
        userDTO.setAddress(user.getAddress());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setUsername(user.getUsername());
        userDTO.setBirthday(user.getBirthday());
        userDTO.setSex(user.getSex());
        userDTO.setStatus(user.getStatus());

        Set<String> urlImageSet = new HashSet<>();
        for (File file : user.getFile()) {
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("file/downloadFile/")
                    .path(file.getId())
                    .toUriString();
            urlImageSet.add(fileDownloadUri);
        }
        userDTO.setUrlImageSet(urlImageSet);

        return userDTO;
    }

    @Override
    public CommentDTO convertToCommentDTO(Comment data) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setIdComment(data.getIdComment());
        commentDTO.setComment(data.getCommentContent());
        commentDTO.setIdUser(data.getUser().getIdUser());
        commentDTO.setIdProduct(data.getProduct().getIdProduct());
        List<String> idFileList = new ArrayList<>();
        for (File file : data.getFile()) {
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("file/downloadFile/")
                    .path(file.getId())
                    .toUriString();
            idFileList.add(fileDownloadUri);
        }
        commentDTO.setUrlFile(idFileList);
        commentDTO.setUserDTO(convert(data.getUser()));

        return commentDTO;
    }

    @Override
    public void updateById(Long id, String commentContent) {
        Comment comment1 = commentReponsitory.findById(id).orElse(null);
        comment1.setCommentContent(commentContent);
        commentReponsitory.save(comment1);
    }

    @Override
    public CommentDTO findById(Long idComment) {
        Comment comment = commentReponsitory.findById(idComment).orElse(null);
        return convertToCommentDTO(comment);
    }
}
