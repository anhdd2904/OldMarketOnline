package com.due.oldmarket.service.impl;

import com.due.oldmarket.dto.ProductDTO;
import com.due.oldmarket.exception.NoSuchProductExistsException;
import com.due.oldmarket.model.*;
import com.due.oldmarket.reponsitory.CategoryReponsitory;
import com.due.oldmarket.reponsitory.FileReponsitory;
import com.due.oldmarket.reponsitory.ProductReponsitory;
import com.due.oldmarket.reponsitory.UserReponsitory;
import com.due.oldmarket.service.FileStorageService;
import com.due.oldmarket.service.ProductService;
import com.due.oldmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductReponsitory productReponsitory;
    @Autowired
    UserReponsitory userReponsitory;
    @Autowired
    CategoryReponsitory categoryReponsitory;
    @Autowired
    FileReponsitory fileReponsitory;

    @Autowired
    UserService userService;
/*    @Autowired
    FileStorageService fileStorageService;*/

    @Override
    public String save(Long idUser, Long idCategory, Product product) {
        /*User user = userReponsitory.findById(iduser).orElse(null);*/
        User user = userService.findById(idUser);
        Category category = categoryReponsitory.findById(idCategory).orElse(null);
/*        Product product1 = productReponsitory.findById(product.getIdProduct()).orElse(null);
        if (product1 == null) {*/
        product.setUser(user);
        product.setCategory(category);
        productReponsitory.save(product);
        return "Product add successfully";
/*        } else {
            throw new ProductAlreadyExistsException("Product already exists!!");
        }*/
    }

    public Product findById(Long id) {
        return productReponsitory.findById(id).orElseThrow(() -> new NoSuchProductExistsException(
                "No Product present with ID = " + id));
    }

    public List<Product> findAll() {
        return productReponsitory.findAll();
    }

    @Override
    public List<Product> findAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Product> productPage = productReponsitory.findAll(pageable);
        List<Product> productList = productPage.getContent();
        return productList;
    }

    public String update(Long idProduct, Product product ) {
        Product product1 = productReponsitory.findById(idProduct).orElseThrow(() -> new NoSuchProductExistsException(
                "No Product present with ID = " + idProduct));
        product1.setDescription(product.getDescription());
        product1.setPrice(product.getPrice());
        product1.setProductName(product.getProductName());
        product1.setTradePark(product.getTradePark());
        product1.setAmount(product.getAmount());
        product1.setStatus(product.getStatus());
//        product1.setCategory(product.getCategory());


            productReponsitory.save(product1);
       /* }
        else {
            Product product2 = findById(idProduct);
            for (File file : fileReponsitory.findAll()){
                if(file.getProduct()==product){
                    fileReponsitory.delete(file);
                }
            productReponsitory.save(product1);
            for (MultipartFile file1: files) {
                fileStorageService.storeFileProduct(file,idProduct);
            }
        }*/
        return "Product update successfully";
    }

    @Override
    public String deteleList(List<Long> id, String param) {
        for (Long idProduct : id) {
            Product product = findById(idProduct);
            product.setStatus(param);
            productReponsitory.save(product);
        }
        return "delete list Product successfully";
    }

    @Override
    public List<Product> findByName(String param, int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        param = param.trim();
        Page<Product> productPage = productReponsitory.findByNameProduct(param, pageable);

        return productPage.getContent();

    }

    @Override
    public List<Product> findByParamProduct(String param, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        param = param.trim();
        Page<Product> productPage = productReponsitory.findByParamProduct(param, pageable);

        return productPage.getContent();
    }

    @Override
    public List<Product> findByIdCategory(Long idCategory, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Product> productPage = productReponsitory.findByIdCategory(idCategory, pageable);
        return productPage.getContent();
    }


    /*    Method này lấy ra những sản phẩm đã đăng bán theo
        điều kiện status( đang chờ admin phê duyệt, đã được bán,bị từ chối)
            status: pending, active, deleted*/

    @Override
    public Set<Product> findByIdUserSalesman(Long idUser, String status) {
        User user = userService.findById(idUser);
        Set<Product> productSet = user.getProducts();
        Set<Product> productSet1 = new HashSet<>();
        for (Product product: productSet){
            if(product.getStatus().equals(status)){
                productSet1.add(product);
            }
        }
        return productSet1;

    }

    @Override
    public ProductDTO convertToProductDTO(Product p) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setIdProduct(p.getIdProduct());
        productDTO.setProductName(p.getProductName());
        productDTO.setPrice(p.getPrice());
        productDTO.setDescription(p.getDescription());
        productDTO.setTradePark(p.getTradePark());
        productDTO.setAmount(p.getAmount());
        productDTO.setIdUser(p.getUser().getIdUser());
        productDTO.setIdCategory(p.getCategory().getIdCategory());
        productDTO.setCategory(p.getCategory());
        List<String> idFileList = new ArrayList<>();
        for (File file : p.getFile()) {

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("file/downloadFile/")
                    .path(file.getId())
                    .toUriString();
            idFileList.add(fileDownloadUri);
        }
        productDTO.setUrlFile(idFileList);
        return productDTO;
    }
}

