package com.mcubes.controller;

import com.mcubes.data.CommonDataAccess;
import com.mcubes.data.KeySets;
import com.mcubes.entity.*;
import com.mcubes.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by A.A.MAMUN on 3/10/2020.
 */
@Controller
@RequestMapping("admin")
public class AdminPanelController {

    public static final String file_path = "C:/Users/A.A.MAMUN/Desktop/ecommerce";

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private SubProductCategoryRepository subProductCategoryRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ProductColorRepository productColorRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductReviewRepository productReviewRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private ContactMessageRepository contactMessageRepository;
    @Autowired
    private KeyAndValueRepository keyAndValueRepository;
    @Autowired
    private CommonDataAccess commonDataAccess;
    @Autowired
    private OurMemberRepository ourMemberRepository;








    /*
    @ CRUD operation upon product category
     */



    /*
    @ CRUD operation upon product brand, color and size
     */



    /*
    @ CRUD operation upon products
     */



    /*
    @ CRUD operation upon contact
     */




    /*
    @ CRUD operation upon about
     */


    /*
    @ CRUD operation upon contact message
     */



    @RequestMapping("/test")
    private String test(){
        return "admin/test";
    }

}
