package com.due.oldmarket.service.impl;

import com.due.oldmarket.dto.UserDTO;
import com.due.oldmarket.exception.NoSuchProductExistsException;
import com.due.oldmarket.exception.NoSuchUserExistsException;
import com.due.oldmarket.model.File;
import com.due.oldmarket.model.User;
import com.due.oldmarket.reponsitory.UserReponsitory;
import com.due.oldmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserReponsitory userReponsitory;

    public List<User> findAll() {
        return userReponsitory.findAll();
    }

    public List<User> findByParam(String param) {
        return userReponsitory.findByParam(param);
    }

    public void save(User user) {
        userReponsitory.save(user);
    }

    public void deleteById(Long idUser) {
        User user = findById(idUser);
        user.setStatus("deleted");
        userReponsitory.save(user);
    }

    public User updateById(Long idUser, User user) {
        User data = userReponsitory.findById(idUser).orElse(null);
        data.setAddress(user.getAddress());
        data.setBirthday(user.getBirthday());
        data.setEmail(user.getEmail());
        data.setFullName(user.getFullName());
        data.setPassword(encoder.encode(user.getPassword()));
        data.setSex(user.getSex());
        data.setUsername(user.getUsername());
        data.setPhoneNumber(user.getPhoneNumber());
        userReponsitory.save(data);
        return data;
    }

    public User findById(Long idUser) {
        return userReponsitory.findById(idUser).orElseThrow(() -> new NoSuchUserExistsException(
                "No User present with ID = " + idUser));
    }

    public User findByUsernamOrEmailAut(String param) {
        return userReponsitory.findByUsernamOrEmailAut(param);
    }
    public UserDTO convert (User user){
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
}
