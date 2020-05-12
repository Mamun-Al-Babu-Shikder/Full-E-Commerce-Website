package com.mcubes.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by A.A.MAMUN on 3/19/2020.
 */
@Configuration
public class MyWebConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("product_images/**","brand_images/**","other_images/**"
                        ,"blog_images/**")
                .addResourceLocations("file:/C:/Users/A.A.MAMUN/Desktop/ecommerce/product_images/"
                        ,"file:/C:/Users/A.A.MAMUN/Desktop/ecommerce/brand_images/"
                        ,"file:/C:/Users/A.A.MAMUN/Desktop/ecommerce/other_images/"
                        ,"file:/C:/Users/A.A.MAMUN/Desktop/ecommerce/blog_images/");
    }
}
