package com.mcubes.controller.admin;

import com.mcubes.data.CommonDataAccess;
import com.mcubes.data.KeySets;
import com.mcubes.entity.AppUser;
import com.mcubes.entity.CustomerOrder;
import com.mcubes.repository.AppUserRepository;
import com.mcubes.repository.CustomerOrderRepository;
import com.mcubes.repository.KeyAndValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by A.A.MAMUN on 4/14/2020.
 */
@Controller
@RequestMapping("admin")
public class AdminOrderController {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private KeyAndValueRepository keyAndValueRepository;


    @RequestMapping("/order-history")
    private String loadOrderHistoryPage()
    {
        return "admin/order-history";
    }


    @ResponseBody
    @RequestMapping("/fetch-customer-order")
    private Page<CustomerOrder> fetchCustomerOrder(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "50") int data_size,
                                                   @RequestParam(defaultValue = "") String search,
                                                   @RequestParam(defaultValue = "") String status)
    {
        Page<CustomerOrder> customerOrderPage = customerOrderRepository
                .findPageableCustomerOrder(search, status, PageRequest.of(page,data_size));

        //System.out.println("# "+customerOrderPage.getContent().toString());

        return customerOrderPage;
    }

    @RequestMapping("/order-details")
    private String loadOrderDetailsPage(Model model, @RequestParam long order_id)
    {

        CustomerOrder customerOrder = customerOrderRepository.findCustomerOrderByOrderId(order_id);
        model.addAttribute("customerOrder", customerOrder);
        AppUser appUser = appUserRepository.findById(customerOrder.getUserEmail()).get();
        model.addAttribute("appUser", appUser);
        model.addAttribute(KeySets.PHONE_NUMBER, keyAndValueRepository.findValueByKey(KeySets.PHONE_NUMBER));
        model.addAttribute(KeySets.EMAIL_ADDRESS, keyAndValueRepository.findValueByKey(KeySets.EMAIL_ADDRESS));


        return "admin/order-details";
    }

    @ResponseBody
    @RequestMapping(value = "/delete-order", method = RequestMethod.POST)
    private boolean deleteCustomerOrder(@RequestParam long orderId)
    {

        try {
            String status = customerOrderRepository.findCustomerOrderStatusByOrderId(orderId);
            if(status.equals(CustomerOrder.PENDING)) {
                return false;
            }else{
                customerOrderRepository.deleteById(orderId);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
