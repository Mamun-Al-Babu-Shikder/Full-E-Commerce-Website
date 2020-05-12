package com.mcubes.repository;

import com.mcubes.entity.ProductSize;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by A.A.MAMUN on 2/27/2020.
 */
@Transactional
public interface ProductSizeRepository extends CrudRepository<ProductSize, Long> {

    ProductSize findProductSizeByProductSizeId(long id);

}

