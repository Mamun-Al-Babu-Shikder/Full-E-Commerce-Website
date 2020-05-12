package com.mcubes.controller;

import com.mcubes.data.CommonDataAccess;
import com.mcubes.entity.AppUser;
import com.mcubes.entity.CustomerOrder;
import com.mcubes.repository.AppUserRepository;
import com.mcubes.repository.CustomerOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * Created by A.A.MAMUN on 4/26/2020.
 */
@Controller
public class OrderHistoryController {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private CommonDataAccess commonDataAccess;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @RequestMapping("/account/order-history")
    private String loadOrderHistoryPage(Principal principal, Model model){

        model.addAttribute("login", principal == null ? true : false);
        commonDataAccess.setProductCategoryListIntoModel(model);

        String userName =  appUserRepository.findUserFullNameOnlyByEmail(principal.getName()).getName();
        model.addAttribute("userName", userName);

        return "order_history";
    }


    @RequestMapping("/account/order-details")
    private String loadOrderDetailsPage(Principal principal, Model model, @RequestParam long q)
    {
        model.addAttribute("login", principal == null ? true : false);
        commonDataAccess.setProductCategoryListIntoModel(model);

        String userName =  appUserRepository.findUserFullNameOnlyByEmail(principal.getName()).getName();
        model.addAttribute("userName", userName);

        CustomerOrder customerOrder = customerOrderRepository.findCustomerOrderByUserEmailAndOrderId(principal
                .getName(),q);
        if(customerOrder==null)
            return "error_page";
        model.addAttribute("customerOrder",customerOrder);
        return "order_details";
    }


    @ResponseBody
    @RequestMapping("/account/fetch-order")
    private Page<CustomerOrder> fetchPageableCustomerOrder(Principal principal, @RequestParam(defaultValue = "") String
            status, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size) {
        Page<CustomerOrder>  orderPage = customerOrderRepository.findCustomerOrderForCustomerView(principal.getName(),
               status, PageRequest.of(page, size));
        return orderPage;
    }




}
