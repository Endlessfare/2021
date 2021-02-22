package com.test.mapper;

import com.test.entity.Product;

import java.util.List;

public interface ProductMapper {
    Product findByPname(String Pname);
    List<Product> findAllByPname(String Pname);
}
