package com.mcubes.controller.admin;

import com.mcubes.data.KeySets;
import com.mcubes.entity.KeyAndValue;
import com.mcubes.entity.OurMember;
import com.mcubes.repository.KeyAndValueRepository;
import com.mcubes.repository.OurMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by A.A.MAMUN on 4/12/2020.
 */
@Controller
@RequestMapping("/admin")
public class AdminAboutController {

    public static final String file_path = "C:/Users/A.A.MAMUN/Desktop/ecommerce";

    @Autowired
    private KeyAndValueRepository keyAndValueRepository;
    @Autowired
    private OurMemberRepository ourMemberRepository;


    @RequestMapping("/about")
    private String loadAboutPage(Model model){

        model.addAttribute(KeySets.ABOUT_BANNER_IMAGE, keyAndValueRepository
                .findValueByKey(KeySets.ABOUT_BANNER_IMAGE));
        model.addAttribute(KeySets.ABOUT_TITLE, keyAndValueRepository.findValueByKey(KeySets.ABOUT_TITLE));
        model.addAttribute(KeySets.ABOUT_DESCRIPTION, keyAndValueRepository.findValueByKey(KeySets.ABOUT_DESCRIPTION));

        return "admin/about";
    }


    @ResponseBody
    @RequestMapping(value = "/update-about-banner-image", method = RequestMethod.POST)
    private boolean updateAboutBannerImage(@RequestParam MultipartFile bannerImage)
    {
        try{

            String imageName = "/other_images/"+"about_banner_image.jpg";
            if(bannerImage!=null && !bannerImage.isEmpty()) {
                bannerImage.transferTo(new File(file_path + imageName));

            /*
            InputStream is = bannerImage.getInputStream();
            OutputStream os = new FileOutputStream(file_path+imageName);
            int len = 0;
            byte[] b = new byte[is.available()];
            while ((len=is.read(b))!=-1){
                os.write(b,0,len);
            }
            os.close();
            is.close();
            */

                keyAndValueRepository.save(new KeyAndValue(KeySets.ABOUT_BANNER_IMAGE, imageName));
                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/update-about-title-and-description", method = RequestMethod.POST)
    private boolean updateAboutTitleAndDescription(@RequestParam String title, @RequestParam String description){
        try{
            List<KeyAndValue> keyAndValues = Arrays.asList(
                    new KeyAndValue(KeySets.ABOUT_TITLE,title),
                    new KeyAndValue(KeySets.ABOUT_DESCRIPTION, description)
            );
            keyAndValueRepository.saveAll(keyAndValues);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @ResponseBody
    @RequestMapping("/fetch-member")
    private List<OurMember> fetchMembers(){
        List<OurMember> ourMembers = (List<OurMember>)  ourMemberRepository.findAll();
        return ourMembers;
    }

    @ResponseBody
    @RequestMapping(value = "/save-member", method = RequestMethod.POST)
    private OurMember saveMember(@ModelAttribute OurMember ourMember)
    {
        try{
            String image_name = "/other_images/member_"+System.currentTimeMillis()+"_"+( 9999999 + (long)
                    (Math.random()*9999999))+".jpg";
            MultipartFile imageFile = ourMember.getImageFile();
            if(imageFile!=null && !imageFile.isEmpty()){
                imageFile.transferTo(new File(file_path + image_name));
            }
            ourMember.setImage(image_name);
            ourMemberRepository.save(ourMember);
            ourMember.setImageFile(null);
            return ourMember;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/update-member", method = RequestMethod.POST)
    private OurMember updateMemberInfo(@ModelAttribute OurMember ourMember){
        try{
           // System.out.println("# "+ourMember);
           // System.out.println("# "+ourMember.getImageFile().isEmpty());

            MultipartFile imageFile = ourMember.getImageFile();
            if(imageFile!=null && !imageFile.isEmpty()){
                imageFile.transferTo(new File(file_path + ourMember.getImage()));
            }
            ourMemberRepository.save(ourMember);
            ourMember.setImageFile(null);
            return ourMember;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete-member", method = RequestMethod.POST)
    private boolean deleteMember(@RequestParam long id){
        try {
            ourMemberRepository.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
