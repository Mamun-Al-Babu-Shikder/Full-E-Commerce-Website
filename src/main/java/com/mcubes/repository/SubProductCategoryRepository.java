package com.mcubes.repository;

import com.mcubes.entity.SubProductCategory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * Created by A.A.MAMUN on 2/5/2020.
 */
@Transactional
public interface SubProductCategoryRepository extends CrudRepository<SubProductCategory, Integer> {

    @Query("select s from SubProductCategory s where s.subcategory_id=?1")
    SubProductCategory findSubProductCategoryBySubcategoryId(int subcategory_id);

    @Query("select s from SubProductCategory s where s.category_id=?1")
    List<SubProductCategory> findByCategoryId(Integer category_id);

    @Query("select s.name from SubProductCategory s where s.subcategory_id=?1")
    String findSubProductCategoryNameById(int subcategory_id);

    @Transactional
    @Modifying
    @Query("delete from SubProductCategory s where s.category_id=?1")
    void deleteSubProductCategoriesByCategoryId(int category_id);
}
