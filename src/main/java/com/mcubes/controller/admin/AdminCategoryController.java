package com.mcubes.controller.admin;

import com.mcubes.entity.ProductCategory;
import com.mcubes.entity.SubProductCategory;
import com.mcubes.repository.ProductCategoryRepository;
import com.mcubes.repository.SubProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by A.A.MAMUN on 4/12/2020.
 */
@Controller
@RequestMapping("admin")
public class AdminCategoryController {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private SubProductCategoryRepository subProductCategoryRepository;



    @RequestMapping("/category")
    private String loadCategoryPage(){
        return "admin/category";
    }

    @ResponseBody
    @RequestMapping("/fetch-product-category")
    private List<ProductCategory> fetchProductCategory() {
        return (List<ProductCategory>) productCategoryRepository.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/save-product-category", method = RequestMethod.POST)
    private ProductCategory saveProductCategory(@RequestParam String icon, @RequestParam String name){

        try {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setIcon(icon);
            productCategory.setName(name);
            productCategoryRepository.save(productCategory);
            return productCategory;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @ResponseBody
    @RequestMapping(value = "/edit-product-category", method = RequestMethod.POST)
    private boolean editProductCategory(@RequestParam int cid, @RequestParam String name, @RequestParam String icon)
    {
        try{
            productCategoryRepository.updateProductCategory(name, icon, cid);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @ResponseBody
    @RequestMapping(value = "/delete-product-category", method = RequestMethod.POST)
    private boolean deleteProductCategory(@RequestParam int id)
    {
        try{
            // productCategoryRepository.deleteById(id);
            productCategoryRepository.deleteProductCategoriesByCategoryId(id);
            subProductCategoryRepository.deleteSubProductCategoriesByCategoryId(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    @ResponseBody
    @RequestMapping(value = "/save-product-subcategory", method = RequestMethod.POST)
    private SubProductCategory saveSubProductCategory(@RequestParam String name, @RequestParam int categoryId){
        try{
            SubProductCategory subProductCategory = new SubProductCategory();
            subProductCategory.setName(name);
            subProductCategory.setCategory_id(categoryId);
            subProductCategoryRepository.save(subProductCategory);
            return  subProductCategory;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @ResponseBody
    @RequestMapping(value = "/delete-product-subcategory", method = RequestMethod.POST)
    private boolean deleteProductSubCategory(@RequestParam int id)
    {
        try{
            subProductCategoryRepository.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @ResponseBody
    @RequestMapping(value = "/edit-product-subcategory", method = RequestMethod.POST)
    private boolean editProductSubCategory(@RequestParam int cid, @RequestParam int sid, @RequestParam String name)
    {
        try{
            subProductCategoryRepository.save(new SubProductCategory(sid, cid, name));
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


}
