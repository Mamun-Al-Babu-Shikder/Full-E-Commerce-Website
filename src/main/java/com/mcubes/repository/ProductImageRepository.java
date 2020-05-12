package com.mcubes.repository;

import com.mcubes.entity.ProductImage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by A.A.MAMUN on 2/3/2020.
 */
@Transactional
public interface ProductImageRepository extends CrudRepository<ProductImage, Long> {


    @Transactional
    @Modifying
    @Query("delete from ProductImage pi where pi.product_id=?1")
    void deleteProductImagesByProductId(long product_id);

    @Query("select pi from ProductImage pi where pi.product_id=?1")
    List<ProductImage> findProductImagesByProductId(long product_id);

}
