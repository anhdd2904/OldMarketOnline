package com.due.oldmarket.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
//@NoArgsConstructor
@RequiredArgsConstructor
public class NotificationDTO {
    private Long id;
    private String message;
    private String status;
    private String type;
    private UserDTO userDTO;
    private ProductDTO productDTO;
}
