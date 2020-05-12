package com.mcubes.repository;

import com.mcubes.entity.ProductColor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by A.A.MAMUN on 2/4/2020.
 */
@Transactional
public interface ProductColorRepository extends CrudRepository<ProductColor, Long>
{
    ProductColor findProductColorByProductColorId(long id);


}
