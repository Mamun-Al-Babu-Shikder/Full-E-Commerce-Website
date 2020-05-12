package com.mcubes.repository;

import com.mcubes.entity.ProductCategory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by A.A.MAMUN on 2/5/2020.
 */
@Transactional
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Integer> {


    @Query("select c from ProductCategory c where c.category_id=?1")
    ProductCategory findProductCategoryByCategoryId(int category_id);

    @Modifying
    @Query("update ProductCategory c set c.name=?1, c.icon=?2 where c.category_id=?3")
    int updateProductCategory(String name, String icon, int id);

    @Query("select c.name from ProductCategory c where c.category_id=?1")
    String findNameOfCategoryById(int category_id);

    @Transactional
    @Modifying
    @Query("delete from ProductCategory c where c.category_id=?1")
    void deleteProductCategoriesByCategoryId(int category_id);
}
