package com.mcubes.repository;

import com.mcubes.entity.ProductReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by A.A.MAMUN on 3/1/2020.
 */
@Transactional
public interface ProductReviewRepository extends CrudRepository<ProductReview, Long> {

    List<ProductReview> findAllByProductId(long productId);

    @Transactional
    @Modifying
    void deleteProductReviewByProductId(long product_id);

    @Query("select pr from ProductReview pr where pr.productId=?1 order by pr.productReviewId desc")
    Page<ProductReview> findPageableProductReview(long product_id, Pageable pageable);

    @Query("select avg(pr.rating) from ProductReview pr where pr.productId=?1")
    int findAverageRatingByProductId(long product_id);

}
