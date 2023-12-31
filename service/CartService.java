package com.due.oldmarket.service;

import com.due.oldmarket.model.Cart;

import java.util.List;

public interface CartService {

    String save (Cart cart, Long idUser, Long idProduct);
    List<Cart> findById (Long idUser);
    String delete (Long idCart);
    Cart update (Long idCart, Long amount);
    Cart findByIdCart (Long id);
    Cart findByIdUserIdPrduct(Long idUser, Long idProduct);
}
