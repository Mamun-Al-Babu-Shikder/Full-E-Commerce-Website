package com.mcubes.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by A.A.MAMUN on 4/12/2020.
 */
@Controller
@RequestMapping("admin")
public class AdminDashboardController {

    @RequestMapping({"/dashboard"})
    private String adminDashboard()
    {
        System.out.println("# inside dashboard.......");
        return "admin/index";
    }
}
