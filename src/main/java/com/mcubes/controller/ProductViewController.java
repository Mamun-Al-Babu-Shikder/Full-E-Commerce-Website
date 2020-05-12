package com.mcubes.controller;

import com.mcubes.data.CommonDataAccess;
import com.mcubes.entity.*;
import com.mcubes.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.function.Predicate;

/**
 * Created by A.A.MAMUN on 2/19/2020.
 */
@Controller
public class ProductViewController {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private CommonDataAccess commonDataAccess;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private SubProductCategoryRepository subProductCategoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ProductReviewRepository productReviewRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private ProductColorRepository productColorRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private WishRepository wishRepository;

    @RequestMapping("/products")
    private String fetchProductByCategory( Principal principal,
                                           Model model,
                                           @RequestParam int category,
                                           @RequestParam(defaultValue = "0") Integer subcategory,
                                           @RequestParam(defaultValue = "0") Integer brand,
                                           @RequestParam(defaultValue = "0") Long color,
                                           @RequestParam(defaultValue = "0") Long size,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "DESC") String direction,
                                           @RequestParam(defaultValue = "product_id") String order)
    {
        try {
            model.addAttribute("login", principal == null ? true : false);
            commonDataAccess.setProductCategoryListIntoModel(model);

            if (subcategory <= 0) {
                List<SubProductCategory> subProductCategories = subProductCategoryRepository.findByCategoryId(category);
                model.addAttribute("subProductCategories", subProductCategories);
            } else {
                model.addAttribute("subcategoryName", subProductCategoryRepository
                        .findSubProductCategoryBySubcategoryId(subcategory).getName());
            }

        }catch (Exception e){
            e.printStackTrace();
        }

       // System.out.println("# Category : "+category+", Subcategory : "+subcategory);

        model.addAttribute("category", category);
        model.addAttribute("selected_category", productCategoryRepository.findProductCategoryByCategoryId(category));
        model.addAttribute("subcategory", subcategory);
        model.addAttribute("brand", brand);
        model.addAttribute("color", color);
        model.addAttribute("size", size);
        model.addAttribute("page", page);
        model.addAttribute("direction", direction);
        model.addAttribute("order", order);

        model.addAttribute("productBrands", brandRepository.findAll());
        model.addAttribute("productColors", productColorRepository.findAll());
        model.addAttribute("productSizes", productSizeRepository.findAll());

        return "product_by_category";
    }



    @ResponseBody
    @RequestMapping(value = "/fetch-products", method = RequestMethod.GET)
    private Page<Product> fetchTestProduct(@RequestParam int category,
                                           @RequestParam(defaultValue = "0") Integer subcategory,
                                           @RequestParam(defaultValue = "0") Integer brand,
                                           @RequestParam(defaultValue = "0") Long color,
                                           @RequestParam(defaultValue = "0") Long size,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "DESC") String direction,
                                           @RequestParam(defaultValue = "product_id") String order
                                           ){


        subcategory = subcategory == 0 ? null : subcategory;
        Brand brand1 = brandRepository.findBrandByBrandId(brand);

        Sort.Direction aseOrDse = Sort.Direction.DESC;
        if(direction.equals("ASC")){
            aseOrDse = Sort.Direction.ASC;
        }

        Page<Product> productPage = null;

        if(color==0 && size==0){
            productPage = productRepository.findProductByCategorySubcategoryIdAndBrand(category, subcategory, brand1,
                    PageRequest.of(page,25, Sort.by(aseOrDse, order)));
        }else{

            color = color == 0 ? null : color;
            size = size == 0 ? null :size;

            productPage = productRepository.findProductsByCategorySubcategoryBrandColorAndSize(category, subcategory,
                    brand1, color, size, PageRequest.of(page, 25, Sort.by(aseOrDse, order)));
        }

        return productPage;

    }






    @RequestMapping("/product")
    private String fetchSingleProduct(@RequestParam Long pid, Principal principal, Model model){

        model.addAttribute("login", principal==null ? true : false);
        commonDataAccess.setProductCategoryListIntoModel(model);

        if(principal!=null) {
            if (wishRepository.countByUserEmailAndProductId(principal.getName(), pid) != 0) {
                model.addAttribute("EXIST_AT_WISH_LIST",true);
            }else{
                model.addAttribute("EXIST_AT_WISH_LIST",false);
            }
        }else{
            model.addAttribute("EXIST_AT_WISH_LIST",false);
        }
        /*
        if(productReviews.size()!=0){
            int totalRating = 0;
            for (ProductReview p : productReviews){
                totalRating+=p.getRating();
            }
        }
        */
        List<Product> newProducts = productRepository.findNewProduct(PageRequest.of(0,3)).getContent();
        model.addAttribute("newProducts", newProducts);


        try {

            Product product = productRepository.findProductByProductId(pid);
            //System.out.println("# " + product);
            List<ProductReview> productReviews = productReviewRepository.findAllByProductId(pid);

            if(product!=null) {

                //System.out.println("# " + productReviews);

                List<ProductVariant> productVariants = product.getProductVariants();

                if (productVariants.size() != 0) {

                    Set<Long> colorIds = new HashSet<>();
                    Set<Long> sizeIds = new HashSet<>();

                    //List<ProductColor> productColors = new ArrayList<>();
                   // List<ProductSize> productSizes = new ArrayList<>();

                    for (ProductVariant p : productVariants) {
                        colorIds.add(p.getColorId());
                        sizeIds.add(p.getSizeId());
                    }
                    // System.out.println("# Color IDs : "+colorIds);
                    // System.out.println("# Size IDs : "+sizeIds);

                    if (colorIds.size() != 0)
                        model.addAttribute("productColors", productColorRepository.findAllById(colorIds));
                    if (sizeIds.size() != 0)
                        model.addAttribute("productSizes", productSizeRepository.findAllById(sizeIds));

                    //   System.out.println("# "+productColorRepository.findAllById(colorIds));
                    //   System.out.println("# "+productSizeRepository.findAllById(sizeIds));
                }
                productRepository.updateProductView(product.getViewed()+1, product.getProduct_id());
            }

            model.addAttribute("productReviews", productReviews);
            model.addAttribute("product", product);

            /* fetch related product */
            Integer subcategory_id = product.getSubcategory_id()<1 ? null : product.getSubcategory_id();
            List<Product> relatedProduct = productRepository.findRelatedProduct(product.getCategory_id(),
                    subcategory_id , product.getProduct_id(), PageRequest.of(0,10)).getContent();

            model.addAttribute("relatedProduct", relatedProduct);

        }catch (Exception e){
            e.printStackTrace();
            return "404_error";
        }

        return "product_details";
    }


    @ResponseBody
    @RequestMapping("/fetch-product-reviews")
    private Map<String, Object> fetchProductReviews(@RequestParam long pid, @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "4") int size){
        Map<String, Object> map = new HashMap<>();
        map.put("reviews", productReviewRepository.findPageableProductReview(pid, PageRequest.of(page, size)));
        map.put("rating", productRepository.findProductRatingByProductId(pid));
        return map;
    }


    @ResponseBody
    @RequestMapping("/account/submit-product-review")
    private boolean submitProductReview(Principal principal, @ModelAttribute ProductReview productReview)
    {
        try {
            String email = principal.getName();
            AppUser appUser = appUserRepository.findUserFullNameOnlyByEmail(email);
            productReview.setUserEmail(email);
            productReview.setUserName(appUser.getName());
            productReview.setDate(new Date().toLocaleString());

            System.out.println("# " + productReview);

            productReviewRepository.save(productReview);

            int avgRating = productReviewRepository.findAverageRatingByProductId(productReview.getProductId());
            productRepository.updateProductRating(avgRating, productReview.getProductId());

            return true;
        }catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


}
