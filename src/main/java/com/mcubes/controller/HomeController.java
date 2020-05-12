package com.mcubes.controller;

import com.mcubes.data.CommonDataAccess;
import com.mcubes.entity.*;
import com.mcubes.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by A.A.MAMUN on 2/6/2020.
 */
@Controller
public class HomeController {

    @Autowired
    private CommonDataAccess commonDataAccess;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BlogRepository blogRepository;


    @RequestMapping("/link1")
    private String loadUrl1(){
        return "test";
    }

    @RequestMapping("/link1/link2")
    private String loadUrl2(){
        return "test";
    }

    @RequestMapping("/link3/link4")
    private String loadUrl3(){
        return "test";
    }

    @RequestMapping({"/","/home"})
    public String homePage(Principal principal, Model model)
    {
        model.addAttribute("login", principal==null ? true : false);
        //model.addAttribute("cartList", commonDataAccess.fetchCartList(principal));
        commonDataAccess.setProductCategoryListIntoModel(model);

        List<Product> topDiscountedProducts = productRepository.findTopDiscountedProduct(PageRequest.of(0,8))
                .getContent();
        List<Product> topSellingProducts = productRepository.findBestSellingProduct(PageRequest.of(0,8))
                .getContent();
        List<Product> newArrivalsProducts = productRepository.findNewProduct(PageRequest.of(0,15))
                .getContent();
        List<Product> topRatingProducts = productRepository.findProductsByTopRating(PageRequest.of(0,8))
                .getContent();
        List<Product> trendingProducts = productRepository.findTrendingProduct(PageRequest.of(0,8))
                .getContent();
        List<Product> mostViewedProducts = productRepository.findProductsOrderByViewedDesc(PageRequest
                .of(0,12)).getContent();

        List<Blog> latestBlog = blogRepository.findPageAbleBlog(PageRequest.of(0,3)).getContent();

        model.addAttribute("topDiscountedProducts", topDiscountedProducts);
        model.addAttribute("topSellingProducts", topSellingProducts);
        model.addAttribute("newArrivalsProducts", newArrivalsProducts);
        model.addAttribute("topRatingProducts", topRatingProducts);
        model.addAttribute("trendingProducts", trendingProducts);
        model.addAttribute("mostViewedProducts", mostViewedProducts);
        model.addAttribute("latestBlog", latestBlog);


        return "index";
    }


    @RequestMapping("/search")
    private String searchProduct(@RequestParam String q, Principal principal, Model model){

        model.addAttribute("login", principal==null ? true : false);
        //model.addAttribute("cartList", commonDataAccess.fetchCartList(principal));
        commonDataAccess.setProductCategoryListIntoModel(model);

        model.addAttribute("q", q);
        return "search_result";
    }

    @ResponseBody
    @RequestMapping("/fetch-search-product")
    private Page<Product> fetchSearchProduct(@RequestParam String name, @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "18") int size) {
        return  productRepository.findSearchProductsByName(name, PageRequest.of(page, size));
    }

    @ResponseBody
    @GetMapping("/fetch-recent-viewed-products")
    private Iterable<Product> fetchRecentViewedProducts(@RequestParam("ids[]") Long[] ids){
        //System.out.println(Arrays.stream(ids));
        List<Long> productIds = Arrays.asList(ids);
        return productRepository.findAllById(productIds);
    }


}
