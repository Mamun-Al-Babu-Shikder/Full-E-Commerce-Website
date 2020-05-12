package com.mcubes.controller;

import com.mcubes.entity.Brand;
import com.mcubes.entity.Product;
import com.mcubes.entity.ProductCategory;
import com.mcubes.entity.SubProductCategory;
import com.mcubes.model.Student;
import com.mcubes.repository.BrandRepository;
import com.mcubes.repository.ProductCategoryRepository;
import com.mcubes.repository.ProductRepository;
import com.mcubes.repository.SubProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;

/**
 * Created by A.A.MAMUN on 2/3/2020.
 */
@Controller
//@RestController
//@RequestMapping("/test")
public class TestController {

    @Autowired
    ProductCategoryRepository categoryRepository;

    @Autowired
    private SubProductCategoryRepository subProductCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;


    @RequestMapping(value = "/save-stu", method = RequestMethod.POST)
    private String saveStu(@ModelAttribute Student student)
    {
        System.out.println("# "+student.toString());
        return "redirect:link1";
    }






    /*
    @ Operation on Category
     */

    @RequestMapping("/category")
    private List<ProductCategory> fetchProductCategory(@RequestParam(defaultValue = "0") int id){
        if(id!=0){
            List<ProductCategory> productCategories = new ArrayList<>();
            productCategories.add(categoryRepository.findById(id).get());
            return productCategories;
        }
        return (List<ProductCategory>) categoryRepository.findAll();
    }

    @RequestMapping("/save-category")
    private void saveCategory(@RequestBody ProductCategory category){
        categoryRepository.save(category);
    }

    @RequestMapping("/delete-category")
    private void deleteCategory(@RequestParam int id){
        categoryRepository.deleteById(id);
    }



    /*
    @ Operation on Sub Category
     */

    @RequestMapping("/subcategory")
    private List<SubProductCategory> fetchSubProductCategory(@RequestParam(defaultValue = "0") int id){
        if(id!=0){
            List<SubProductCategory> productCategories = new ArrayList<>();
            productCategories.add(subProductCategoryRepository.findById(id).get());
            return productCategories;
        }
        return (List<SubProductCategory>) subProductCategoryRepository.findAll();
    }


    @RequestMapping("/save-subcategory")
    private void saveSubCategory(@RequestBody SubProductCategory subcategory){
        subProductCategoryRepository.save(subcategory);
    }

    @RequestMapping("/delete-subcategory")
    private void deleteSubCategory(@RequestParam int id){
        subProductCategoryRepository.deleteById(id);
    }



    @RequestMapping("/save-product")
    public boolean saveProduct(@RequestBody Product product){



        try {

            //Brand brand = brandRepository.findById(101).get();
           // product.setBrand(brand);
            productRepository.save(product);

            System.out.println("# "+product);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }


    @RequestMapping("/allproducts")
    public List<Product> fetchAllProduct()
    {
        List<Product> products = (List<Product>) productRepository.findAll();
        return products;
    }



    @RequestMapping("/products-by-brand")
    public List<Product> fetchProductByBrand()
    {
        Brand brand = brandRepository.findById(101).get();
        System.out.println("# "+brand.toString());
        List<Product> products = (List<Product>) productRepository.findProductByBrand(brand);
        return products;
    }


    @ResponseBody
    @RequestMapping("pass-array")
    public String passArray(@RequestParam(name = "array[]") Integer[] array){
        for(int a : array){
            System.out.println("val : "+a);
        }
        return array.toString();
    }


    @ResponseBody
    @RequestMapping("set-cookie")
    public String setCookie(@RequestParam String name, @RequestParam String value, HttpServletResponse response)
    {
        try {
            Cookie cookie = new Cookie(name, value);

            cookie.setMaxAge(10);
            cookie.setSecure(false);
            response.addCookie(cookie);
            return "Cookie added successfully";
        }catch (Exception e){
            e.printStackTrace();
            return "Can't add cookie";
        }

    }

    @ResponseBody
    @RequestMapping("get-cookie")
    public String getCookie(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();

        if(cookies!=null){
            String s = "";
            for(Cookie c : cookies){
                s+=c.getName()+", "+c.getValue()+"\n";
            }
            return s;
        }

        return "No Cookies";
    }



}
