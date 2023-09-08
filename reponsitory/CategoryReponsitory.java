package com.due.oldmarket.reponsitory;

import com.due.oldmarket.model.Category;
import com.due.oldmarket.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryReponsitory extends JpaRepository<Category,Long> {

}
