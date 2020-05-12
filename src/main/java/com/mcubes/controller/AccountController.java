package com.mcubes.controller;

import com.mcubes.data.CommonDataAccess;
import com.mcubes.entity.AppUser;
import com.mcubes.entity.ProductCategory;
import com.mcubes.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * Created by A.A.MAMUN on 2/17/2020.
 */
@Controller
public class AccountController {

    @Autowired
    private CommonDataAccess commonDataAccess;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private CartRepository cartRepository;

    @RequestMapping("/login")
    private String loadLoginPage(Principal principal, Model model, HttpServletRequest httpServletRequest){

        model.addAttribute("login", principal==null ? true : false);
        commonDataAccess.setProductCategoryListIntoModel(model);

        return "login";
    }

    @RequestMapping("/create_account")
    private String loadRegistrationPage(Principal principal, Model model){

        model.addAttribute("login", principal==null ? true : false);
        commonDataAccess.setProductCategoryListIntoModel(model);


        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    private String userRegistration(@ModelAttribute AppUser appUser, Principal principal, Model model){

        model.addAttribute("login", principal==null ? true : false);
        commonDataAccess.setProductCategoryListIntoModel(model);

        System.out.println("# "+appUser.toString());

        if(appUserRepository.countByEmail(appUser.getEmail())==0){

            try{
                appUserRepository.save(appUser);
            }catch (Exception e){
                e.printStackTrace();
            }

        }else{
            model.addAttribute("isUserExist",true);
        }




        return "registration";
    }




    @RequestMapping("/account/profile")
    private String loadProfilePage(Principal principal, Model model){

        model.addAttribute("login", principal==null ? true : false);
        commonDataAccess.setProductCategoryListIntoModel(model);

        AppUser appUser = appUserRepository.findById(principal.getName()).get();

        model.addAttribute("appUser",appUser);

        int count_wishlist = wishRepository.countWishByUserEmail(principal.getName());
        int count_cartitem = cartRepository.countCartItemByUserEmail(principal.getName());
        int count_order = customerOrderRepository.countCustomerOrderByUserEmail(principal.getName());

        model.addAttribute("count_wishlist", count_wishlist>9 ? "9+" : "0"+count_wishlist);
        model.addAttribute("count_cartitem", count_cartitem>9 ? "9+" : "0"+count_cartitem);
        model.addAttribute("count_order", count_order>9 ? "9+" : "0"+count_order);

        return "my_profile";
    }


    @RequestMapping("/account/update_user")
    private String updateAppUser(Principal principal, @ModelAttribute AppUser appUser)
    {
        System.out.println("# "+appUser.toString());

        appUserRepository.updateAppUserInformationByEmail(appUser.getFirst_name(), appUser.getLast_name(),
                appUser.getPhone(), appUser.getDob(), appUser.getGender(), appUser.getAddress(), appUser.getCompany(),
                appUser.getCity(), appUser.getPostal_code(), appUser.getFacebookProfileLink(), appUser.getTwitterProfileLink(),
                appUser.getInstagramProfileLink(), principal.getName());

        return "redirect:/account/profile";
    }


    @ResponseBody
    @RequestMapping("/account/update-password")
    private int updateUserPassword(Principal principal, @RequestParam String oldPassword,
                                       @RequestParam String newPassword)
    {
        try{
            if( oldPassword.equals(appUserRepository.findAppUserPasswordByEmail(principal.getName()))) {
                appUserRepository.updateAppUserPasswordByEmail(newPassword, principal.getName());
                return 1;
            }
            return 2;
        }catch (Exception ex){
            ex.printStackTrace();
            return 3;
        }
    }



}
