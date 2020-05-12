package com.mcubes.controller.admin;

import com.mcubes.data.CommonDataAccess;
import com.mcubes.data.KeySets;
import com.mcubes.entity.ContactMessage;
import com.mcubes.entity.KeyAndValue;
import com.mcubes.repository.ContactMessageRepository;
import com.mcubes.repository.KeyAndValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

/**
 * Created by A.A.MAMUN on 4/12/2020.
 */
@Controller
@RequestMapping("admin")
public class AdminContactController {

    @Autowired
    private CommonDataAccess commonDataAccess;
    @Autowired
    private KeyAndValueRepository keyAndValueRepository;
    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @RequestMapping("/contact")
    private String loadContactPage(Model model) {
        commonDataAccess.setContactBasicInfo(model);
        commonDataAccess.setSocialAddress(model);
        return "admin/contact";
    }

    @ResponseBody
    @RequestMapping(value = "/save-contact-info", method = RequestMethod.POST)
    private boolean saveContactInfo(@RequestParam String location, @RequestParam String googleMapLocation,
                                    @RequestParam String phone, @RequestParam String email,
                                    @RequestParam String facebook, @RequestParam String twitter,
                                    @RequestParam String googlePlus, @RequestParam String pinterest,
                                    @RequestParam String instagram)
    {
        try{

//            keyAndValueRepository.save(new KeyAndValue(KeySets.LOCATION, location));
            Iterable<KeyAndValue> iterator = Arrays.asList(
                    new KeyAndValue(KeySets.LOCATION, location),
                    new KeyAndValue(KeySets.GOOGLE_MAP_LOCATION, googleMapLocation),
                    new KeyAndValue(KeySets.PHONE_NUMBER, phone),
                    new KeyAndValue(KeySets.EMAIL_ADDRESS, email),
                    new KeyAndValue(KeySets.FACEBOOK_LINK, facebook),
                    new KeyAndValue(KeySets.TWITTER_LINK, twitter),
                    new KeyAndValue(KeySets.GOOGLE_PLUS_LINK, googlePlus),
                    new KeyAndValue(KeySets.PINTEREST_LINK, pinterest),
                    new KeyAndValue(KeySets.INSTAGRAM_LINK, instagram));

            keyAndValueRepository.saveAll(iterator);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @ResponseBody
    @RequestMapping("/fetch-contact-message")
    private Page<ContactMessage> fetchContactMessage(@RequestParam(defaultValue = "") String status,
                                                     @RequestParam(defaultValue = "0") int page)
    {
        Page<ContactMessage> contactMessagePage = contactMessageRepository.findPageableContactMessage(status,
                PageRequest.of(page, 100));
        return contactMessagePage;
    }


    @ResponseBody
    @RequestMapping(value = "/delete-contact-message", method = RequestMethod.POST)
    private boolean deleteContactMessage(@RequestParam long id)
    {
        try{
            contactMessageRepository.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @ResponseBody
    @RequestMapping("/update-contact-message-status")
    private boolean updateContactMessageStatus(@RequestParam long id, @RequestParam String status){

        try{
            contactMessageRepository.updateContactMessageStatus(status, id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @ResponseBody
    @RequestMapping("/reply-contact-message")
    private boolean replyContactMessage(@RequestParam long id, @RequestParam String to, @RequestParam String subject,
                                        @RequestParam String body){

        try{

            System.out.println("# To : "+to+", Subject : "+subject+", Body : "+body);
            /*
            @ Write email sending code here
             */

            contactMessageRepository.updateContactMessageStatus(ContactMessage.REPLIED, id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
