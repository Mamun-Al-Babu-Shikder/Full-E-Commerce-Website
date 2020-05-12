package com.mcubes.repository;

import com.mcubes.entity.Brand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by A.A.MAMUN on 2/11/2020.
 */
@Transactional
public interface BrandRepository extends CrudRepository<Brand, Integer> {

    @Query("select b from Brand b where b.brand_id=?1")
    Brand findBrandByBrandId(int brandId);

}
