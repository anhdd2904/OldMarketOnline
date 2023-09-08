package com.due.oldmarket.dto;

import com.due.oldmarket.model.Product;
import com.due.oldmarket.model.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import java.util.List;

@Data
public class BillIdProductRequest {
    private List<Long> idProducts;
    private Long idBill;
    private String status;
    private Long amount;
    private Long totalPrice;
    private String createDate;
    private String address;
    private Product product;
    private User user;
}
