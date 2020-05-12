package com.mcubes.repository;

import com.mcubes.entity.*;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by A.A.MAMUN on 2/3/2020.
 */
@Transactional
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("select p from Product p where p.product_id=?1")
    Product findProductByProductId(long id);

    List<Product> findProductByBrand(Brand brand);

    int countProductsByBrand(Brand brand);


    /* Fetch Pageable Product
    @Query("select p from Product p where p.category_id=?1")
    Page<Product> findProductByCategoryId(int category_id, Pageable pageable);

    @Query("select p from Product p where p.category_id=?1 and p.subcategory_id=?2")
    Page<Product> findProductByCategoryIdAndSubcategoryId(int category_id, int subcategory_id, Pageable pageable);
    */

    /* Fetch search product */
    @Query("select p from Product p where p.name like %?1%")
    Page<Product> findSearchProductsByName(String name, Pageable pageable);

    /* Fetch product by category, subcategory and brand */
    @Query("select p from Product p where p.category_id=:category and (:subcategory is null or p.subcategory_id=:subcategory) and (:brand is null or p.brand=:brand)")
    Page<Product> findProductByCategorySubcategoryIdAndBrand(@Param("category") int category_id, @Param("subcategory") Integer subcategory_id, @Param("brand") Brand brand, Pageable pageable);

    @Query("select p from Product p where p.category_id=?1 and (?2 is null or p.subcategory_id=?2) and (?3 is null or p.brand=?3)")
    Page<Product> findProductByCategorySubcategoryIdAndBrand2(int category_id, Integer subcategory_id, Brand brand, Pageable pageable);

    /* Fetch product by category, subcategory, brand, color and size */
    @Query("select p from Product p where p.product_id in(select distinct pv.product_id from ProductVariant pv where ( :color is null or pv.colorId=:color) and (:size is null or pv.sizeId=:size) ) and p.category_id=:category and (:subcategory is null or p.subcategory_id=:subcategory) and (:brand is null or p.brand=:brand)")
    Page<Product> findProductsByCategorySubcategoryBrandColorAndSize(@Param("category") int category_id, @Param("subcategory") Integer subcategory_id, @Param("brand") Brand brand, @Param("color") Long color, @Param("size") Long size, Pageable pageable);


    /* Fetch related product */
    @Query("select p from Product p where p.category_id=?1 and (?2 is null or p.subcategory_id=?2) and not p.product_id=?3 order by p.product_id asc")
    Page<Product> findRelatedProduct(int category_id, Integer subcategory_id, long product_id, Pageable pageable);

    /* Fetch product by category and color
    @Query("select p from Product p where p.product_id in(select distinct pv.product_id from ProductVariant pv where ( :color is null or pv.colorId=:color) ) and p.category_id=:category")
    Page<Product> findProductsByCategoryAndColor(@Param("category") int category_id,@Param("color") Long color, Pageable pageable);
    */

    /* Fetch product by category, subcategory and color
    @Query("select p from Product p where p.product_id in(select distinct pv.product_id from ProductVariant pv where pv.colorId=?3) and p.category_id=?1 and p.subcategory_id=?2")
    Page<Product> findProductsByCategorySubcategoryAndColor(int category_id, int subcategory_id, long color, Pageable pageable);
    */


    /* Fetch product by category and size
    @Query("select p from Product p where p.product_id in(select distinct pv.product_id from ProductVariant pv where pv.sizeId=?2) and p.category_id=?1")
    Page<Product> findProductsByCategoryAndSize(int category_id, long color, Pageable pageable);
    */

    /* Fetch product by category, subcategory and size
    @Query("select p from Product p where p.product_id in(select distinct pv.product_id from ProductVariant pv where pv.sizeId=?3) and p.category_id=?1 and p.subcategory_id=?2")
    Page<Product> findProductsByCategorySubcategoryAndSize(int category_id, int subcategory_id, long color, Pageable pageable);
    */


    /* Fetch product by category, color and size
    @Query("select p from Product p where p.product_id in(select distinct pv.product_id from ProductVariant pv where pv.colorId=?2 or pv.sizeId=?3) and p.category_id=?1")
    Page<Product> findProductsByCategoryAndSize(int category_id, long color, long size, Pageable pageable);
    */

    /* Fetch product by category, subcategory, color and size
    @Query("select p from Product p where p.product_id in(select distinct pv.product_id from ProductVariant pv where pv.colorId=?3 or pv.sizeId=?4) and p.category_id=?1 and p.subcategory_id=?2")
    Page<Product> findProductsByCategorySubcategoryAndSize(int category_id, int subcategory_id, long color, long size, Pageable pageable);
    */


    /* Data Fetch For Wish Controller */
    @Query("select new Product(p.name, p.price, p.old_price, p.image) from Product p where p.product_id=?1")
    Product findProductForWishByProductId(long id);

    @Query("select p.productVariants from Product p where p.product_id=?1")
    List<ProductVariant> findProductVariantByProductId(long id);
    /*
    @Query("select p.productColors from Product p where p.product_id=?1")
    List<ProductColor> findProductColors(Long id);

    @Query("select p.productSizes from Product p where p.product_id=?1")
    List<ProductSize> findProductSizes(Long id);
    */


    @Query("select new Product(p.name, p.price, p.image) from Product p where p.product_id=?1")
    Product findProductForCartByProductId(long id);


    /* Data Fetch For Order */
    @Query("select new Product(p.image, p.name, p.actualPrice, p.price, p.old_price, p.stock) from Product p where  p.product_id=?1")
    Product findProductForOrder(long id);

    /*
    @Query(value = "SELECT product_image.image_url FROM product JOIN product_image ON product.product_id = product_image.product_id WHERE product.product_id = ?1 ")
    List<String> findProductImageByProductId(long id);
    */

    /*
    @Query(value = "SELECT product_image.image_url FROM product JOIN product_image ON product.product_id = product_image.product_id WHERE product.product_id = ?1 limit 1", nativeQuery = true)
    String findProductImageByProductId(long id);
    */


    @Query("select p from Product p where p.name like %?1% order by p.product_id desc")
    Page<Product> findProductForAdminPanel(String search, Pageable pageable);

    @Query("select p from Product p where p.name like %?1% order by p.product_id desc")
    Page<Product> customFilterQuery(String category_id, Pageable pageable);




    /*
    @ fetch top best selling products
     */
    @Query("select p from Product p order by p.sell desc")
    Page<Product> findBestSellingProduct(Pageable pageable);

    /*
    @ fetch new products
     */
    @Query("select p from Product p order by p.product_id desc")
    Page<Product> findNewProduct(Pageable pageable);

    /*
    @ fetch top rating products
     */
    @Query("select p from Product p where p.rating>1 order by p.rating desc")
    Page<Product> findProductsByTopRating(Pageable pageable);

    /*
    @ fetch trending products
     */
    @Query("select p from Product p where p.trending=true order by p.product_id desc")
    Page<Product> findTrendingProduct(Pageable pageable);

    /*
    @ fetch top discounted product
     */
    @Query("select p from Product p order by (p.price - p.old_price)/p.old_price ")
    Page<Product> findTopDiscountedProduct(Pageable pageable);

    /*
    @ fetch most viewed product
     */
    @Query("select p from Product p order by p.viewed desc")
    Page<Product> findProductsOrderByViewedDesc(Pageable pageable);


    /*
    @ update product view
     */
    @Modifying
    @Query("update Product p set p.viewed=?1 where p.product_id=?2")
    void updateProductView(long value, long product_id);

   // @Transactional
    @Modifying
    @Query("delete from Product p where p.product_id=?1")
    void deleteProductFromDatabaseByProductId(long product_id);


    /* fetch stock by product id */
    @Transactional
    @Query("select p.stock from Product p where p.product_id=?1")
    long findProductStockByProductId(long product_id);

    /* update product stock */
    @Transactional
    @Modifying
    @Query("update Product p set p.stock=?2 where p.product_id=?1")
    void updateProductStockByProductId(long product_id, long stock);


    /* update product basic info */
    @Transactional
    @Modifying
    @Query("update Product p set p.image=?1, p.name=?2, p.model=?3, p.category_id=?4, p.subcategory_id=?5, " +
            "p.actualPrice=?6, p.price=?7, p.old_price=?8, p.stock=?9, p.brand=?10, p.date=?11, p.trending=?12," +
            "p.description=?13 where p.product_id=?14")
    void updateProductBasicInfo(String image, String name, String model, int category, int subcategory,
                                double actual_price, double price, double old_price, long stock, Brand brand,
                                String date, boolean trending, String description, long product_id);



    @Transactional
    @Modifying
    @Query("update Product p set p.rating=?1 where p.product_id=?2")
    void updateProductRating(int rating, long product_id);


    @Query("select p.rating from Product p where p.product_id=?1")
    int findProductRatingByProductId(long product_id);


}

