package com.mcubes.repository;

import com.mcubes.entity.CartItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by A.A.MAMUN on 2/21/2020.
 */
@Transactional
public interface CartRepository extends CrudRepository<CartItem, Long> {

    List<CartItem> findAllByUserEmail(String email);

    @Transactional
    @Modifying
    @Query("delete from  CartItem c where c.cartItem_id=?1 and c.userEmail=?2")
    void removeCartItemByCartItemIdAndUserEmail(long cartItemId, String userEmail);

    @Modifying
    void removeCartItemsByUserEmail(String userEmail);

    @Modifying
    void removeAllByUserEmail(String userEmail);

    @Query("select c from CartItem c where c.userEmail=?1 and  c.product_id=?2")
    CartItem findCartItemByUserEmailAndProductId(String userEmail, long productId);

    @Query("select c from CartItem c where c.userEmail=?1 and c.product_id=?2 and c.productColorId=?3 and c.productSizeId=?4")
    CartItem findCartItemByUserEmailProductIdProductColorIdAndProductSizeId(String userEmail, long productId,
                                                                            long productColorId, long productSizeId);

    @Query("select c from CartItem c where c.userEmail=?1 and  c.cartItem_id=?2")
    CartItem findCartItemByUserEmailAndCartItemId(String userEmail, long cartItemId);


    @Query("SELECT sum(quantity) from CartItem ci WHERE ci.product_id=?1")
    Integer findTotalQuantityByProductId(long product_id);

    @Transactional
    @Modifying
    @Query("delete from CartItem c where c.product_id=?1")
    void deleteCartItemByProductId(long product_id);

    @Query("select count(c) from CartItem c where c.productColorId=?1 and c.productSizeId=?2 and c.product_id=?3")
    int countCartItemByProductColorIdAndProductSizeIdAndProductId(long colorId, long sizeId, long product_id);

    int countCartItemByUserEmail(String email);

}
