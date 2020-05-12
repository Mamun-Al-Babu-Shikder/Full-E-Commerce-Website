package com.mcubes.controller.admin;

import com.mcubes.entity.*;
import com.mcubes.repository.*;
import com.mcubes.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * Created by A.A.MAMUN on 4/12/2020.
 */
@Controller
@RequestMapping("admin")
public class AdminProductController {

    public static final String file_path = "C:/Users/A.A.MAMUN/Desktop/ecommerce";


    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private SubProductCategoryRepository subProductCategoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductColorRepository productColorRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private ProductReviewRepository productReviewRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;


    @RequestMapping("/all-product")
    private String loadAllProductPage(Model model, @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "50") int size,
                                      @RequestParam(defaultValue = "") String search) {

        List<ProductCategory> categoryList = (List<ProductCategory>) productCategoryRepository.findAll();
        List<SubProductCategory> subProductCategoryList = (List<SubProductCategory>)
                subProductCategoryRepository.findAll();

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("subProductCategoryList", subProductCategoryList);

        page = page>0 ? page-1 : 0;
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("search", search);

        return "admin/products";
    }

    @RequestMapping("/add-product")
    private String loadAddProductPage(Model model, @RequestParam(defaultValue = "-1") int status)
    {
        commonDataForAddAndEditProduct(model);
        model.addAttribute("status", status);
        return "admin/add-product";
    }

    @RequestMapping("/edit-product")
    private String loadEditProductPage(Model model, @RequestParam long pid)
    {
        commonDataForAddAndEditProduct(model);

        Product product = productRepository.findProductByProductId(pid);
        System.out.println("# Edit Product :\n"+product.toString());
        model.addAttribute("product", product);

        return "admin/edit-product";
    }


    private void commonDataForAddAndEditProduct(Model model){

        List<ProductCategory> categoryList = (List<ProductCategory>) productCategoryRepository.findAll();
        List<ProductColor> productColorList = (List<ProductColor>) productColorRepository.findAll();
        List<ProductSize> productSizeList = (List<ProductSize>) productSizeRepository.findAll();
        List<Brand> brandList = (List<Brand>) brandRepository.findAll();

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("productColorList", productColorList);
        model.addAttribute("productSizeList", productSizeList);
        model.addAttribute("brandList", brandList);
    }


    @ResponseBody
    @RequestMapping("/fetch-products")
    private Page<Product> fetchProducts(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "") String search)
    {
        Page<Product> productPage = productRepository.findProductForAdminPanel(search, PageRequest.of(page,size));
        return productPage;
    }


    @Autowired
    private ProductService productService;

    @ResponseBody
    @RequestMapping(value = "/save-product", method = RequestMethod.POST)
    private boolean saveProduct(@ModelAttribute Product product){

        boolean status = false;

        if(product.getMultipartImage()!=null && !product.getMultipartImage().isEmpty()){

            try{
                String image_name = "product_images/product_"+System.currentTimeMillis()+"_"+( 9999999 + (long)
                        (Math.random()*9999999))+".jpg";
                product.getMultipartImage().transferTo(new File(file_path+"/"+
                        image_name));
                product.setImage("/"+image_name);

                List<ProductImage> productImages = new ArrayList<>();
                MultipartFile multipartFiles[] = product.getMultipartImages();
                for(int i=0;i<multipartFiles.length;i++){
                    MultipartFile image = multipartFiles[i];
                    if(image!=null && !image.isEmpty()) {
                        try {
                            String name = "product_images/product_" + System.currentTimeMillis()+"_"+( 9999999 + (long)
                                    (Math.random()*9999999)) +(i+1)+ "_another_img.jpg";
                            image.transferTo(new File(file_path+"/" + name));
                            productImages.add(new ProductImage("/"+name));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                product.setProductImages(productImages);
                productRepository.save(product);

                System.out.println("# "+product);

                status  = true;
            }catch (Exception e){

                e.printStackTrace();

            }
        }

        return status;
        //return "redirect:/admin/add-product?status="+status;
    }



    @ResponseBody
    @RequestMapping("/update-product-basic-info")
    private boolean updateProduct(@ModelAttribute Product product)
    {
        try {

            if(product.getMultipartImage()!=null && !product.getMultipartImage().isEmpty()){
                product.getMultipartImage().transferTo(new File(file_path+"/"+product
                        .getImage()));
            }

            productRepository.updateProductBasicInfo(product.getImage(), product.getName(),product.getModel(),
                    product.getCategory_id(), product.getSubcategory_id(), product.getActualPrice(), product.getPrice(),
                    product.getOld_price(), product.getStock(), product.getBrand(), product.getDate(),
                    product.isTrending(), product.getDescription(), product.getProduct_id());

            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



    @ResponseBody
    @RequestMapping("/fetch-product-images")
    private List<ProductImage> fetchProductImages(@RequestParam long pid) {
        return productImageRepository.findProductImagesByProductId(pid);
    }

    @ResponseBody
    @RequestMapping("/add-product-image")
    private boolean addProductImage(@RequestParam long pid, @RequestParam MultipartFile productImage)
    {
        try {
            if (productImage != null && !productImage.isEmpty()) {
                String name = "/product_images/product_" + System.currentTimeMillis() + "_" + (9999999 + (long)
                        (Math.random() * 9999999)) + "_another_img.jpg";
                productImage.transferTo(new File(file_path + name));
                productImageRepository.save(new ProductImage(pid, name));
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @ResponseBody
    @RequestMapping("/delete-product-image")
    private boolean deleteProductImage(long id){
        try{
            productImageRepository.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @ResponseBody
    @RequestMapping("/fetch-product-variant")
    private Map<String, Object> fetchProductVariant(@RequestParam long pid) {

        Map<Long,ProductColor> productColorMap = new HashMap<>();
        productColorRepository.findAll().forEach(productColor -> productColorMap.put(productColor.getProductColorId(),
                productColor));

        Map<Long,ProductSize> productSizeMap = new HashMap<>();
        productSizeRepository.findAll().forEach(productSize -> productSizeMap.put(productSize.getProductSizeId(),
                productSize));

        Map<String, Object> map = new HashMap<>();
        map.put("productColorMap", productColorMap);
        map.put("productSizeMap", productSizeMap);
        map.put("productVariant", productVariantRepository.findProductvariantByProductId(pid));
        return map;
    }


    @ResponseBody
    @RequestMapping("/save-product-variant")
    private boolean saveProductVariant(@ModelAttribute ProductVariant productVariant)
    {
        try{
            Long productVariantId = productVariantRepository.findProductVariantIdByProductIdColorIdAndSizeId(
                    productVariant.getProduct_id(), productVariant.getColorId(), productVariant.getSizeId());
            if(productVariant.getColorId()==0 && productVariant.getSizeId()==0)
                return false;
            if(productVariantId==null){
                productVariantRepository.save(productVariant);
            }else{
                productVariant.setProductVariantId(productVariantId);
                productVariantRepository.save(productVariant);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/update-product-variant", method = RequestMethod.POST)
    private boolean updateProductVariant(@ModelAttribute ProductVariant productVariant)
    {
        try{
            productVariantRepository.save(productVariant);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @ResponseBody
    @RequestMapping("/delete-product-variant")
    private boolean deleteProductVariant(@RequestParam long pid, @RequestParam long id)
    {
        try{
            ProductVariant productVariant = productVariantRepository.findById(id).get();
            int count = cartRepository.countCartItemByProductColorIdAndProductSizeIdAndProductId(productVariant.getColorId(),
                    productVariant.getSizeId(), pid);
            if(count!=0)
                return false;
            productVariantRepository.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @RequestMapping("/product-details")
    private String productDetails(@RequestParam long pid, Model model, HttpServletRequest request)
    {

        // System.out.println("# "+ request.getHeader("User-Agent") );

        Product product = productRepository.findById(pid).get();
        List<ProductReview> productReviews = productReviewRepository.findAllByProductId(pid);
        List<ProductColor> productColors = (List<ProductColor>) productColorRepository.findAll();
        List<ProductSize> productSizes = (List<ProductSize>) productSizeRepository.findAll();


        Integer val = cartRepository.findTotalQuantityByProductId(pid);
        int totalCarted = val==null? 0:val;
        int totalWished = wishRepository.countByProductId(pid);

        model.addAttribute("product", product);
        model.addAttribute("categoryName", productCategoryRepository
                .findNameOfCategoryById(product.getCategory_id()));
        model.addAttribute("subcategoryName", subProductCategoryRepository
                .findSubProductCategoryNameById(product.getSubcategory_id()));
        model.addAttribute("productReviews", productReviews);
        model.addAttribute("totalCarted", totalCarted);
        model.addAttribute("totalWished", totalWished);
        model.addAttribute("productColors", productColors);
        model.addAttribute("productSizes", productSizes);

        return "admin/product-details";
    }


    @ResponseBody
    @RequestMapping("/product-delete-validation")
    private String productDeleteValidation(@RequestParam long pid)
    {
        String status = "DELETE_ABLE";
        System.out.println("# PID : "+pid);


        // System.out.println("# "+customerOrderRepository.findCustomerOrderByProductId(pid));
        /*
        int pendingOrderForThisProduct = customerOrderRepository.findCustomerOrderByProductId(pid);
        int totalCartedThisProduct = cartRepository.findTotalCartedProductByProductId(pid);

        if(pendingOrderForThisProduct!=0){
            status = "CAN_NOT_DELETE";
        }else if(totalCartedThisProduct!=0){
            status = "DELETE_WITH_CART_ITEMS";
        }
        */

        return status;
    }


    @ResponseBody
    @RequestMapping(value = "delete-product", method = RequestMethod.POST)
    private boolean deleteProduct(@RequestParam long pid)
    {

        int pendingOrderForThisProduct = customerOrderRepository.findCustomerOrderByProductId(pid);
        if(pendingOrderForThisProduct==0) {
            try {
                // productRepository.deleteById(pid);
                /*
                productRepository.deleteProductFromDatabaseByProductId(pid);
                productImageRepository.deleteProductImagesByProductId(pid);
                productVariantRepository.deleteProductVariantsByProductId(pid);
                productReviewRepository.deleteProductReviewByProductId(pid);
                cartRepository.deleteCartItemByProductId(pid);
                wishRepository.deleteWishesByProductId(pid);
                */
                productService.deleteProduct(pid);

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }else{
            return false;
        }
    }



}
