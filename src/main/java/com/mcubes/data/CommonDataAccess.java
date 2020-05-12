package com.mcubes.data;

import com.mcubes.entity.*;
import com.mcubes.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by A.A.MAMUN on 2/20/2020.
 */
@Component
public class CommonDataAccess {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductColorRepository productColorRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private KeyAndValueRepository keyAndValueRepository;


    /*
    @ Fetch category
    */
    public void setProductCategoryListIntoModel(Model model) {

        List<ProductCategory> productCategories = (List<ProductCategory>) productCategoryRepository.findAll();
        model.addAttribute("productCategories", productCategories);
        List<ProductCategory> shop = new ArrayList<>();
        for (ProductCategory pc : productCategories){
            if(pc.getSubProductCategories().size()>3)
                shop.add(pc);
            if(shop.size()==4)
                break;
        }
        model.addAttribute("shop", shop);
        setSocialAddress(model);
    }

    public void setSocialAddress(Model model){
       // System.out.println("# FB : "+ keyAndValueRepository.findValueByKey(KeySets.FACEBOOK_LINK));
        try {
            model.addAttribute(KeySets.FACEBOOK_LINK, keyAndValueRepository.findValueByKey(KeySets.FACEBOOK_LINK));
            model.addAttribute(KeySets.TWITTER_LINK, keyAndValueRepository.findValueByKey(KeySets.TWITTER_LINK));
            model.addAttribute(KeySets.GOOGLE_PLUS_LINK, keyAndValueRepository.findValueByKey(KeySets.GOOGLE_PLUS_LINK));
            model.addAttribute(KeySets.PINTEREST_LINK, keyAndValueRepository.findValueByKey(KeySets.PINTEREST_LINK));
            model.addAttribute(KeySets.INSTAGRAM_LINK, keyAndValueRepository.findValueByKey(KeySets.INSTAGRAM_LINK));
            model.addAttribute(KeySets.PHONE_NUMBER, keyAndValueRepository.findValueByKey(KeySets.PHONE_NUMBER));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void setContactBasicInfo(Model model) {
        try{
            model.addAttribute(KeySets.LOCATION, keyAndValueRepository.findValueByKey(KeySets.LOCATION));
            model.addAttribute(KeySets.GOOGLE_MAP_LOCATION, keyAndValueRepository.findValueByKey(KeySets.GOOGLE_MAP_LOCATION));
            model.addAttribute(KeySets.PHONE_NUMBER, keyAndValueRepository.findValueByKey(KeySets.PHONE_NUMBER));
            model.addAttribute(KeySets.EMAIL_ADDRESS, keyAndValueRepository.findValueByKey(KeySets.EMAIL_ADDRESS));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<CartItem> fetchCartList(Principal principal)
    {
        if(principal==null){
            return null;
        }

        List<CartItem> cartItemList = cartRepository.findAllByUserEmail(principal.getName());

        for (CartItem cartItem : cartItemList){

            Product product = productRepository.findProductForCartByProductId(cartItem.getProduct_id());
            if(product==null)
                continue;
            cartItem.setName(product.getName());
            cartItem.setImage(product.getImage());

            try {

                ProductVariant productVariant = productVariantRepository
                        .findProductVariantByColorIdAndSizeIdAndProductId(cartItem.getProductColorId()
                                , cartItem.getProductSizeId(), cartItem.getProduct_id());

                if (productVariant != null) {

                    cartItem.setPrice(productVariant.getPrice());
                    cartItem.setCostPerItem(productVariant.getPrice() * cartItem.getQuantity());

                    if (cartItem.getProductColorId() != 0) {
                        cartItem.setColorName(productColorRepository.findById(cartItem.getProductColorId()).get().getColor());
                    }

                    if (cartItem.getProductSizeId() != 0) {
                        cartItem.setSizeName(productSizeRepository.findById(cartItem.getProductSizeId()).get().getSizeName());
                    }

                } else {
                    cartItem.setPrice(product.getPrice());
                    cartItem.setCostPerItem(product.getPrice() * cartItem.getQuantity());
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            /*
            if(cartItem.getProductColorId()!=0){
                cartItem.setColorName(productColorRepository.findById(cartItem.getProductColorId()).get().getColor());
            }

            if(cartItem.getProductSizeId()!=0){
                ProductSize productSize = productSizeRepository.findById(cartItem.getProductSizeId()).get();
                cartItem.setSizeName(productSize.getSizeName());
                cartItem.setPrice(productSize.getPrice());
                cartItem.setCostPerItem(productSize.getPrice()*cartItem.getQuantity());
            }else{
                cartItem.setPrice(product.getPrice());
                cartItem.setCostPerItem(product.getPrice()*cartItem.getQuantity());
            }
            */

        }

       // System.out.println("# "+cartItemList);

        return cartItemList;
    }

}
