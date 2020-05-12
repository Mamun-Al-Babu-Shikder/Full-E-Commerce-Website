package com.mcubes.controller;

import com.mcubes.data.CommonDataAccess;
import com.mcubes.entity.CartItem;
import com.mcubes.repository.CartRepository;
import com.mcubes.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by A.A.MAMUN on 2/19/2020.
 */
@Controller
public class CartController {

    @Autowired
    private CommonDataAccess commonDataAccess;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping("/account/cart")
    private String viewCartPage(Principal principal, Model model)
    {
        model.addAttribute("login", principal==null ? true : false);
        commonDataAccess.setProductCategoryListIntoModel(model);

        List<CartItem> cartItems = commonDataAccess.fetchCartList(principal);
        model.addAttribute("cartItems", cartItems);



        return "cart";
    }


    @ResponseBody
    @RequestMapping(value = "/add-to-cart", method = RequestMethod.POST)
    private boolean addToCart(Principal principal, Model model, @RequestParam Long pid,
                              @RequestParam(defaultValue = "1") int quantity,
                              @RequestParam(defaultValue = "0") long productColorId,
                              @RequestParam(defaultValue = "0") long productSizeId)
    {
        System.out.println("Product ID : "+pid);
        try{
            if(productRepository.existsById(pid)){

                CartItem cartItem  = new CartItem();
                cartItem.setUserEmail(principal.getName());
                cartItem.setProduct_id(pid);
                cartItem.setProductColorId(productColorId);
                cartItem.setProductSizeId(productSizeId);
                cartItem.setQuantity(quantity);

                CartItem oldCartItem = cartRepository
                        .findCartItemByUserEmailProductIdProductColorIdAndProductSizeId(principal.getName(), pid,
                                productColorId, productSizeId);

                if(oldCartItem!=null){
                    cartItem.setCartItem_id(oldCartItem.getCartItem_id());
                    int totalQuantity = oldCartItem.getQuantity()+quantity;
                    cartItem.setQuantity(totalQuantity);
                }




                cartRepository.save(cartItem);


                return true;

            }

            return false;

        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }




    @RequestMapping(value = "/account/update-cart-item", method = RequestMethod.POST)
    private String updateCartItem(Principal principal, @RequestParam long cartItem_id, @RequestParam int update_quantity )
    {
        System.out.println("# "+cartItem_id+", "+update_quantity);

        try{
            CartItem cartItem = cartRepository.findCartItemByUserEmailAndCartItemId(principal.getName(), cartItem_id);
            if(cartItem!=null && update_quantity>0){
                cartItem.setQuantity(update_quantity);
                cartRepository.save(cartItem);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/account/cart";
    }

    @RequestMapping(value = "/account/delete-cart-item")
    private String deleteCartItem(Principal principal, @RequestParam long cartItem_id) {
        cartRepository.removeCartItemByCartItemIdAndUserEmail(cartItem_id, principal.getName());
        return "redirect:/account/cart";
    }


    @ResponseBody
    @RequestMapping(value = "/fetch-cart-items", method = RequestMethod.POST)
    private List<CartItem> fetchCartItems(Principal principal) {
        return commonDataAccess.fetchCartList(principal);
    }


    @ResponseBody
    @RequestMapping(value = "/remove-cart-item", method = RequestMethod.POST)
    private boolean removeCartItem(Principal principal, @RequestParam long cartItem_id)
    {
        try{
             cartRepository.removeCartItemByCartItemIdAndUserEmail(cartItem_id, principal.getName());
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

}
