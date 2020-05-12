package com.mcubes.repository;

import com.mcubes.entity.ProductVariant;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by A.A.MAMUN on 2/29/2020.
 */
@Transactional
public interface ProductVariantRepository extends CrudRepository<ProductVariant, Long> {

    ProductVariant findProductVariantByColorIdAndSizeId(long colorId, long sizeId);

    //@Query("select pv from ProductVariant pv left join Product p on p.product_id=31 where pv.colorId=?1 and pv.sizeId=?2")
    @Query("select pv from ProductVariant pv where pv.colorId=?1 and pv.sizeId=?2 and pv.product_id=?3")
    ProductVariant findProductVariantByColorIdAndSizeIdAndProductId(long colorId, long sizeId, long product_id);

    @Query("select new ProductVariant(pv.actualPrice, pv.price, pv.old_price, pv.stock) from ProductVariant  pv where pv.colorId=?1 and pv.sizeId=?2 and pv.product_id=?3")
    ProductVariant findProductVariantForOrder(long colorId, long sizeId, long productId);

    @Transactional
    @Modifying
    @Query("delete from ProductVariant pv where pv.product_id=?1")
    void deleteProductVariantsByProductId(long product_id);

    int countProductVariantsByColorId(long colorId);

    int countProductVariantsBySizeId(long sizeId);

    @Transactional
    @Query("select pv.stock from ProductVariant pv where pv.product_id=?1 and pv.colorId=?2 and pv.sizeId=?3")
    int findStockByProductIdColorIdAndSizeId(long product_id, long colorId, long sizeId);

    @Transactional
    @Modifying
    @Query("update ProductVariant pv set pv.stock=?4 where pv.product_id=?1 and pv.colorId=?2 and pv.sizeId=?3")
    void updateStockByProductIdColorIdAndSizeId(long product_id, long colorId, long sizeId, int stock);

    @Query("select pv from ProductVariant pv where pv.product_id=?1")
    List<ProductVariant> findProductvariantByProductId(long product_id);

    @Query("select pv.productVariantId from ProductVariant pv where pv.product_id=?1 and pv.colorId=?2 and pv.sizeId=?3")
    Long findProductVariantIdByProductIdColorIdAndSizeId(long product_id, long colorId, long sizeId);


    @Query("select pv.colorId from ProductVariant pv where pv.product_id=?1 and pv.sizeId=?2")
    Set<Long> findProductColorIdsByProductIdAndSizeId(long product_id, long sizeId);


}
