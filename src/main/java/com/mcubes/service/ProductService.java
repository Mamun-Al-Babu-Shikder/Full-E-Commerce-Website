package com.mcubes.service;

import com.mcubes.entity.Product;
import com.mcubes.entity.ProductReview;
import com.mcubes.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Created by A.A.MAMUN on 4/13/2020.
 */
@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;
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


    @Transactional(rollbackFor = Exception.class)
    public void saveProduct(Product product)  {
        productRepository.save(product);
//        throw new RuntimeException("Ex...");
        int a = 1/0;
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public void deleteProduct(long pid)
    {
        productRepository.deleteProductFromDatabaseByProductId(pid);
        productImageRepository.deleteProductImagesByProductId(pid);
        productVariantRepository.deleteProductVariantsByProductId(pid);
        productReviewRepository.deleteProductReviewByProductId(pid);
        cartRepository.deleteCartItemByProductId(pid);
        wishRepository.deleteWishesByProductId(pid);
    }

}
