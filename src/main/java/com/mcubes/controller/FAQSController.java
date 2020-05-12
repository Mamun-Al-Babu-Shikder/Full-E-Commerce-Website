package com.mcubes.controller;

import com.mcubes.data.CommonDataAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * Created by A.A.MAMUN on 4/4/2020.
 */
@Controller
public class FAQSController {

    @Autowired
    private CommonDataAccess commonDataAccess;

    @RequestMapping("/faqs")
    private String loadFAQSPage(Principal principal, Model model)
    {
        model.addAttribute("login", principal == null ? true : false);
        commonDataAccess.setProductCategoryListIntoModel(model);

        return "faqs";
    }

}
