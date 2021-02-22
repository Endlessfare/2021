package com.test.DBA;

import com.test.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product findByPname(String Pname);
    List<Product> findAllByPname(String Pname);
    List<Product> findAllByPnameLike(String Pname);
    List<Product> findAllByPnameLikeAndPriceGreaterThanEqual(String Pname,Double Price);
    List<Product> findAllByPnameLikeAndPriceLessThanEqual(String Pname,Double Price);
    List<Product> findAllByPriceGreaterThanEqual(Double price);
    List<Product> findAllByPriceLessThanEqual(Double price);
    List<Product> findAllByPriceLessThanEqualAndPriceGreaterThanEqual(Double price_high,Double price_low);
    List<Product> findAllByPnameAndPriceGreaterThanEqualAndPriceLessThanEqual(String Pname,Double price_low,Double price_high);

    List<Product> findAllByPnameLikeAndPriceGreaterThanEqualAndPriceLessThanEqual(String pname, double price_low, double price_high);
}
