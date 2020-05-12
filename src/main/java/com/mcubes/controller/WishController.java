package com.mcubes.controller;

import com.mcubes.data.CommonDataAccess;
import com.mcubes.entity.Product;
import com.mcubes.entity.ProductImage;
import com.mcubes.entity.Wish;
import com.mcubes.repository.ProductColorRepository;
import com.mcubes.repository.ProductImageRepository;
import com.mcubes.repository.ProductRepository;
import com.mcubes.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by A.A.MAMUN on 2/21/2020.
 */
@Controller
public class WishController {

    @Autowired
    private CommonDataAccess commonDataAccess;

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductColorRepository productColorRepository;

    @RequestMapping("/account/wishlist")
    private String viewWishPage(Principal principal, Model model)
    {
        model.addAttribute("login", principal==null ? true : false);
        commonDataAccess.setProductCategoryListIntoModel(model);

        List<Wish> wishList = null;

        try{

           wishList = wishRepository.findAllByUserEmail(principal.getName());

           for(Wish wish : wishList){

               Product product = productRepository.findProductForWishByProductId(wish.getProduct_id());

               /*
               if(product==null)
                   continue;
               */

               wish.setName(product.getName());
               wish.setPrice(product.getPrice());
               wish.setOld_price(product.getOld_price());
               wish.setImage_url(product.getImage());

               wish.setHasOption(productRepository.findProductVariantByProductId(wish.getProduct_id()).size()!=0);
            //   wish.setHasOption(!(productRepository.findProductColors(wish.getProduct_id()).size()==0  && productRepository.findProductSizes(wish.getProduct_id()).size()==0));

           }

           System.out.println("# "+wishList);

        }catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("wishList", wishList);

        return "wishlist";
    }



    @ResponseBody
    @RequestMapping("/add-to-wishlist")
    private String addToWishList(Principal principal ,@RequestParam long pid)
    {
        if(principal==null){
            return "LOGIN_AT_FIRST";
        }

        if(wishRepository.countByUserEmailAndProductId(principal.getName(), pid)!=0){
            return "ALREADY_EXIST";
        }

        try{
            Wish wish = new Wish();
            wish.setProduct_id(pid);
            wish.setUserEmail(principal.getName());
            wishRepository.save(wish);
            return "SUCCESS";
        }catch (Exception e){
            e.printStackTrace();
            return "FAILED";
        }
    }


    @ResponseBody
    @RequestMapping("/remove_wish")
    private boolean removeWishFromWishList(@RequestParam Long wish_id)
    {
        try {
            wishRepository.deleteById(wish_id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



}
