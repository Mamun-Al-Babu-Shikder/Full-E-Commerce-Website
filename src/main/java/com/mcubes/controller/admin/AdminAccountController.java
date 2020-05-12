package com.mcubes.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by A.A.MAMUN on 4/21/2020.
 */
@Controller
@RequestMapping("/admin")
public class AdminAccountController {

    @RequestMapping(value = "/login")
    private String loadAdminLoginPage() {
        return "admin/login";
    }


}
