package com.mcubes.controller.admin;

import com.mcubes.entity.Blog;
import com.mcubes.entity.BlogComment;
import com.mcubes.repository.BlogCommentRepository;
import com.mcubes.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by A.A.MAMUN on 4/12/2020.
 */
@Controller
@RequestMapping("admin")
public class AdminBlogController {

    public static final String file_path = "C:/Users/A.A.MAMUN/Desktop/ecommerce";

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private BlogCommentRepository blogCommentRepository;

    @RequestMapping("/all-blog")
    private String loadAllBlogPage() {
        return "admin/all-blog";
    }

    @RequestMapping("/add-blog")
    private String loadAddBlog(){
        return "admin/add-blog";
    }

    @RequestMapping("/edit-blog")
    private String loadEditBlogPage(@RequestParam long b, Model model)
    {
        model.addAttribute("editableBlog",blogRepository.findBlogById(b));
        return "admin/edit-blog";
    }

    @RequestMapping("/blog-details")
    private String loadBlogDetailsPage(@RequestParam long b, Model model){
        Blog blog = blogRepository.findBlogById(b);
        model.addAttribute("blog", blog);
        return "admin/blog-details";
    }

    @ResponseBody
    @RequestMapping("/fetch-blog-comments")
    private Page<BlogComment> fetchBlogComments(@RequestParam long blogId, @RequestParam(defaultValue = "0") int page)
    {
        Page<BlogComment> blogComments = blogCommentRepository.findPageAbleBlogComment(blogId, PageRequest
                .of(page, 50));
        return blogComments;
    }

    @ResponseBody
    @RequestMapping("/fetch-all-blog")
    private Page<Blog> fetchAllBlog(@RequestParam(defaultValue = "0") int page)
    {
        Page<Blog> blogPage = blogRepository.findPageAbleBlog(PageRequest.of(page,30));
        return blogPage;
    }


    @ResponseBody
    @RequestMapping("/save-blog")
    private boolean saveBlog(@ModelAttribute Blog blog)
    {
        try{
            String image_name = "/blog_images/blog_image_"+System.currentTimeMillis()+( 9999999 + (long)
                    (Math.random()*9999999))+".jpg";
            MultipartFile imageFile = blog.getImageFile();
            if(imageFile!=null && !imageFile.isEmpty()){
                imageFile.transferTo(new File(file_path + image_name));
            }
            blog.setImage(image_name);
            blogRepository.save(blog);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @ResponseBody
    @RequestMapping("/update-blog")
    private boolean updateBlog(@ModelAttribute Blog blog)
    {
        try{
            MultipartFile imageFile = blog.getImageFile();
            if(imageFile!=null && !imageFile.isEmpty()){
                imageFile.transferTo(new File(file_path + blog.getImage()));
            }
            blogRepository.save(blog);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



    @ResponseBody
    @RequestMapping("/delete-blog")
    private boolean deleteBlog(@RequestParam long id)
    {
        try{
            blogRepository.deleteById(id);
            blogCommentRepository.deleteBlogCommentsByBlogId(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
