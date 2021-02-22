package com.test.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.RootNameLookup;
import com.sun.jersey.core.impl.provider.entity.XMLRootObjectProvider;
import com.test.DBA.ProductRepository;
import com.test.base.AppResponse;
import com.test.entity.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;


import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@Api
@RequestMapping("/testMe")
public class RestfulController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    EntityManager entityManager;
    @Autowired
    JSONPObject jsonpObject;
    @PutMapping("/greet")
    @ApiOperation("put方法，会返回一个字符串对象")
    @ApiImplicitParams(value={@ApiImplicitParam(name="name",value="字符串的值")})
    public AppResponse<String> greetin(String name)
    {
        return new AppResponse<String>(100,true,"Hello,"+name);
    }
    @PostMapping("/save")
    @ApiOperation("PUT方法，用来保存一个产品对象")
    @ApiImplicitParams(value={@ApiImplicitParam(name="Pname",value="产品名" ,required = true),@ApiImplicitParam(name="price",value="产品价格",required = true)})
    public AppResponse<Product> saveProduct(@RequestBody Product p )
    {
        System.out.println(p.getPname());
        return new AppResponse<Product>(100,true,productRepository.save(p));
    }
    @GetMapping("/findOne")
    @ApiOperation("GET方法，用来获取一个产品对象")
    @ApiImplicitParam(name="Pid",value = "产品ID",required = true)
    public AppResponse<Product> findOne(@RequestParam(name="Pid")Integer pid)
    {
        return new AppResponse<>(100, true, productRepository.getOne(pid));
    }
    @PostMapping("/getAll")
    @ApiOperation("POST方法，按照给定参数作为条件进行筛选，并返回符合条件的商品列表")
    @ApiImplicitParams(value = {@ApiImplicitParam(name="Pname",value = "产品名称"),@ApiImplicitParam(name="price_high",value = "筛选产品价格最高值"),@ApiImplicitParam(name="price_low",value="筛选产品价格最小值")})
    public AppResponse<List<Product>> findAll(@RequestBody Map searchMap)
    {

        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery=cb.createQuery(Product.class);
        Root p=criteriaQuery.from(Product.class);
        List<Predicate> predicates=new ArrayList<>();
        predicates.add(cb.equal(p.get("Pname"),"aaa"));
        List<Product> rs=cb.
        if(!(null==searchMap.get("Pname"))&&searchMap.get("Pname").toString().trim()!="")
        {
            System.out.println("名称查找");
            System.out.println(searchMap.get("Pname"));
            if(!(null==searchMap.get("price_low"))&&searchMap.get("price_low").toString().trim()!="")
            {
                System.out.println(">"+searchMap.get("price_low"));
                if(!(null==searchMap.get("price_high"))&&searchMap.get("price_high").toString().trim()!="")
                {
                    System.out.println("价格区间查找");
                    System.out.println("<"+searchMap.get("price_high"));
                    System.out.println((String)searchMap.get("Pname")+","+Double.parseDouble((String)searchMap.get("price_low"))+","+Double.parseDouble((String)searchMap.get("price_high")));
                    return new AppResponse<>(100,true,productRepository.findAllByPnameLikeAndPriceGreaterThanEqualAndPriceLessThanEqual((String)searchMap.get("Pname"),Double.parseDouble((String)searchMap.get("price_low")),Double.parseDouble((String)searchMap.get("price_high"))));

                }
                else{
                    return new AppResponse<>(100,true,productRepository.findAllByPnameLikeAndPriceGreaterThanEqual((String)searchMap.get("Pname"),Double.parseDouble((String)searchMap.get("price_low"))));
                }
            }
            if(!(null==searchMap.get("price_high"))&&searchMap.get("price_high").toString().trim()!="")
            {
                return new AppResponse<>(100,true,productRepository.findAllByPnameLikeAndPriceGreaterThanEqual((String)searchMap.get("Pname"),Double.parseDouble((String)searchMap.get("price_high"))));
            }
            return new AppResponse<>(100,true,productRepository.findAllByPname((String)searchMap.get("Pname")));
        }
        else {
            if(!(null==searchMap.get("price_low"))&&searchMap.get("price_low").toString().trim()!="")
            {
                if(!(null==searchMap.get("price_high"))&&searchMap.get("price_high").toString().trim()!="")
                {
                    return new AppResponse<>(100,true,productRepository.findAllByPriceLessThanEqualAndPriceGreaterThanEqual(Double.parseDouble((String)searchMap.get("price_high")),Double.parseDouble((String)searchMap.get("price_low"))));
                }
                else{
                    return new AppResponse<>(100,true,productRepository.findAllByPriceGreaterThanEqual(Double.parseDouble((String)searchMap.get("price_low"))));
                }
            }
            if(!(null==searchMap.get("price_high"))&&searchMap.get("price_high").toString().trim()!="")
            {
                return new AppResponse<>(100,true,productRepository.findAllByPriceLessThanEqual(Double.parseDouble((String)searchMap.get("price_high"))));
            }
            System.out.println("全查找");
            return new AppResponse<>(100,true,productRepository.findAll());
        }

    }
    @PostMapping("/update")
    @ApiOperation("post方法，用给定的产品信息来完成产品信息的更新")
    @ApiImplicitParams(value = {@ApiImplicitParam(name="Pname",value = "产品名称"),@ApiImplicitParam(name="price_high",value = "筛选产品价格最高值"),@ApiImplicitParam(name="price_low",value="筛选产品价格最小值")})
    public AppResponse<Product> updateOne(@RequestBody Product p)
    {
        return new AppResponse<>(100,true,productRepository.save(p));
    }
    @DeleteMapping("/deleteOne")
    @ApiOperation("delete方法，通过ID删除对应的产品信息")
    @ApiImplicitParam(name="Pid",value="要删除的产品的ID")
    public AppResponse<String> deleteOne(@RequestParam(name="Pid")Integer pid){
        try{
            productRepository.deleteById(pid);
            return new AppResponse<>(100,true,"删除成功");
        }catch(Exception e){
            return new AppResponse<>(200,false,"删除失败");

        }
    }

}
