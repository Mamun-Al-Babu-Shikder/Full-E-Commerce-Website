package com.mcubes.controller;

import com.mcubes.data.CommonDataAccess;
import com.mcubes.data.KeySets;
import com.mcubes.entity.OurMember;
import com.mcubes.repository.KeyAndValueRepository;
import com.mcubes.repository.OurMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

/**
 * Created by A.A.MAMUN on 4/4/2020.
 */
@Controller
public class AboutController {

    @Autowired
    private CommonDataAccess commonDataAccess;
    @Autowired
    private KeyAndValueRepository keyAndValueRepository;
    @Autowired
    private OurMemberRepository ourMemberRepository;

    @RequestMapping("/about")
    private String loadAboutPage(Principal principal, Model model)
    {
        model.addAttribute("login", principal == null ? true : false);
        commonDataAccess.setProductCategoryListIntoModel(model);

        model.addAttribute(KeySets.ABOUT_BANNER_IMAGE, keyAndValueRepository
                .findValueByKey(KeySets.ABOUT_BANNER_IMAGE));
        model.addAttribute(KeySets.ABOUT_TITLE, keyAndValueRepository.findValueByKey(KeySets.ABOUT_TITLE));
        model.addAttribute(KeySets.ABOUT_DESCRIPTION, keyAndValueRepository.findValueByKey(KeySets.ABOUT_DESCRIPTION));

        List<OurMember> ourMembers = (List<OurMember>)  ourMemberRepository.findAll();
        model.addAttribute("ourMembers", ourMembers);

        return "about";
    }



}
