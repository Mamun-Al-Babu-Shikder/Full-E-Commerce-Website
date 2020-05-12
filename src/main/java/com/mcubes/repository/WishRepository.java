package com.mcubes.repository;

import com.mcubes.entity.Wish;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by A.A.MAMUN on 2/21/2020.
 */
@Transactional
public interface WishRepository extends CrudRepository<Wish, Long> {

    List<Wish> findAllByUserEmail(String email);

    @Query("select count(w) from Wish w where w.userEmail=?1 and w.product_id=?2")
    int countByUserEmailAndProductId(String userEmail, long pid);

    @Query("select count(w) from Wish w where w.product_id=?1")
    int countByProductId(long product_id);

    @Modifying
    @Query("delete from Wish w where w.product_id=?1")
    void deleteWishesByProductId(long product_id);

    int countWishByUserEmail(String email);
}
