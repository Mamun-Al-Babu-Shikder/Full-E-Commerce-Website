package com.mcubes.controller;

import com.mcubes.data.CommonDataAccess;
import com.mcubes.entity.AppUser;
import com.mcubes.entity.Blog;
import com.mcubes.entity.BlogComment;
import com.mcubes.entity.Product;
import com.mcubes.repository.AppUserRepository;
import com.mcubes.repository.BlogCommentRepository;
import com.mcubes.repository.BlogRepository;
import com.mcubes.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
 * Created by A.A.MAMUN on 4/11/2020.
 */
@Controller
public class BlogController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CommonDataAccess commonDataAccess;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private BlogCommentRepository blogCommentRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @RequestMapping("/blog")
    private String loadBlogPage(Principal principal, Model model, @RequestParam(defaultValue = "0") int page)
    {
        model.addAttribute("login", principal == null ? true : false);
        commonDataAccess.setProductCategoryListIntoModel(model);

        Page<Blog> blogPage = blogRepository.findPageAbleBlog(PageRequest.of(Math.abs(page), 9));
        model.addAttribute("blogPage", blogPage);

        List<Product> newProducts = productRepository.findNewProduct(PageRequest.of(0,3)).getContent();
        model.addAttribute("newProducts", newProducts);

        List<Blog> newBlogList = blogRepository.findPageAbleBlog(PageRequest.of(0,3)).getContent();
        model.addAttribute("newBlogList", newBlogList);

        return "blog";
    }

    @RequestMapping("/single-blog")
    private String loadSingleBlogPage(Principal principal, Model model, @RequestParam long b)
    {
        model.addAttribute("login", principal == null ? true : false);
        commonDataAccess.setProductCategoryListIntoModel(model);
        Blog blog = blogRepository.findBlogById(b);
        blogRepository.updateBlogView(blog.getViewed()+1,blog.getId());
        model.addAttribute("blog", blog);

        List<Product> newProducts = productRepository.findNewProduct(PageRequest.of(0,3)).getContent();
        model.addAttribute("newProducts", newProducts);

        List<Blog> newBlogList = blogRepository.findPageAbleBlog(PageRequest.of(0,3)).getContent();
        model.addAttribute("newBlogList", newBlogList);

        return "single_blog";
    }

    @ResponseBody
    @RequestMapping("/fetch-blog-comments")
    private Page<BlogComment> fetchBlogComments(@RequestParam long blogId, @RequestParam(defaultValue = "0") int page)
    {
        Page<BlogComment> blogComments = blogCommentRepository.findPageAbleBlogComment(blogId, PageRequest
                .of(page, 5));
        return blogComments;
    }

    @ResponseBody
    @RequestMapping("/post-blog-comment")
    private boolean postBlogComment(@RequestParam long blogId, @RequestParam String comment, Principal principal)
    {
        try{
            AppUser appUser = appUserRepository.findById(principal.getName()).get();
            String date =  new Date().toLocaleString();
            BlogComment blogComment = new BlogComment(blogId, appUser.getEmail(), appUser.getName(), date, comment);
            blogCommentRepository.save(blogComment);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



}
