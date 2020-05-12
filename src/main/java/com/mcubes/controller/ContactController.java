package com.mcubes.controller;

import com.mcubes.data.CommonDataAccess;
import com.mcubes.entity.ContactMessage;
import com.mcubes.repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Date;

/**
 * Created by A.A.MAMUN on 4/4/2020.
 */
@Controller
public class ContactController {

    @Autowired
    private CommonDataAccess commonDataAccess;
    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @RequestMapping("/contact")
    private String loadContactPage(Principal principal, Model model)
    {
        model.addAttribute("login", principal == null ? true : false);
        commonDataAccess.setProductCategoryListIntoModel(model);
        commonDataAccess.setContactBasicInfo(model);

        return "contact";
    }


    @ResponseBody
    @RequestMapping(value = "/send-contact-message", method = RequestMethod.POST)
    private boolean sendContactMessage(@RequestParam String name, @RequestParam String email, @RequestParam String phone,
                                      @RequestParam String message){
        try{
            ContactMessage contactMessage = new ContactMessage();
            contactMessage.setDate(new Date().toLocaleString());
            contactMessage.setName(name);
            contactMessage.setEmail(email);
            contactMessage.setPhone(phone);
            contactMessage.setMessage(message);
            contactMessage.setStatus(ContactMessage.UNSEEN);
            contactMessageRepository.save(contactMessage);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }

}
