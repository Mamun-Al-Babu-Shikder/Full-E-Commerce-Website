package com.mcubes.controller.admin;

import com.mcubes.entity.Brand;
import com.mcubes.entity.ProductColor;
import com.mcubes.entity.ProductSize;
import com.mcubes.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by A.A.MAMUN on 4/12/2020.
 */
@Controller
@RequestMapping("admin")
public class AdminBrandColorSizeController {

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ProductColorRepository productColorRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;

    @RequestMapping("/brand-color-size")
    private String loadBrandColorSizePage(){
        return "admin/brand-color-size";
    }

    @ResponseBody
    @RequestMapping("/fetch-product-brand")
    private List<Brand> fetchAllBrand() {
        return (List<Brand>) brandRepository.findAll();
    }


    @ResponseBody
    @RequestMapping("/fetch-product-color")
    private List<ProductColor> fetchAllColor() {
        return (List<ProductColor>) productColorRepository.findAll();
    }


    @ResponseBody
    @RequestMapping("/fetch-product-size")
    private List<ProductSize> fetchAllSize() {
        return (List<ProductSize>) productSizeRepository.findAll();
    }

    @ResponseBody
    @RequestMapping("/save-product-brand")
    private boolean saveProductBrand(@RequestParam String brandName)
    {
        try{
            brandRepository.save(new Brand(brandName));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @ResponseBody
    @RequestMapping("/update-product-brand")
    private boolean updateProductBrand(@RequestParam int brandId, @RequestParam String brandName)
    {
        try{
            brandRepository.save(new Brand(brandId, brandName));
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    @ResponseBody
    @RequestMapping("/delete-product-brand")
    private String deleteProductBrand(@RequestParam int id)
    {
        try{
            int count = productRepository.countProductsByBrand(brandRepository.findById(id).get());
            if(count==0){
                brandRepository.deleteById(id);
                return "SUCCESS";
            }else{
                return "NOT_DELETE_ABLE";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "ERROR";
        }
    }


    @ResponseBody
    @RequestMapping("/save-product-color")
    private boolean saveProductColor(@RequestParam String colorCode, @RequestParam String colorName)
    {
        try{
            productColorRepository.save(new ProductColor(colorName, colorCode));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @ResponseBody
    @RequestMapping("/update-product-color")
    private boolean updateProductColor(@RequestParam long colorId, @RequestParam String colorName
            , @RequestParam String colorCode)
    {
        try{
            productColorRepository.save(new ProductColor(colorId, colorName, colorCode));
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @ResponseBody
    @RequestMapping("/delete-product-color")
    private String deleteProductColor(@RequestParam long id)
    {
        try{
            int count = productVariantRepository.countProductVariantsByColorId(id);
            if(count==0){
                productColorRepository.deleteById(id);
                return "SUCCESS";
            }else {
                return "NOT_DELETE_ABLE";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "ERROR";
        }
    }





    @ResponseBody
    @RequestMapping("/save-product-size")
    private boolean saveProductSize(@RequestParam String sizeName)
    {
        try{
            productSizeRepository.save(new ProductSize(sizeName));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @ResponseBody
    @RequestMapping("/update-product-size")
    private boolean updateProductSize(@RequestParam  long sizeId, @RequestParam String sizeName)
    {
        try{
            productSizeRepository.save(new ProductSize(sizeId, sizeName));
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @ResponseBody
    @RequestMapping("delete-product-size")
    private String deleteProductSize(@RequestParam long id)
    {
        try{
            int count = productVariantRepository.countProductVariantsBySizeId(id);
            if(count==0) {
                return "SUCCESS";
            }else{
                return "NOT_DELETE_ABLE";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "ERROR";
        }
    }



}
