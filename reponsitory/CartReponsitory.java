package com.due.oldmarket.reponsitory;

import com.due.oldmarket.model.Cart;
import com.due.oldmarket.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartReponsitory extends JpaRepository<Cart,Long> {
    @Query(value = "SELECT * FROM cart WHERE   id_user LIKE %?1% AND id_product LIKE %?2%",nativeQuery = true)
    Cart findByIdUserIdPrduct(Long idUser, Long idProduct);
}
