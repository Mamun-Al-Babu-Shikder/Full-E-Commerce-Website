package com.mcubes.repository;

import com.mcubes.entity.BlogComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by A.A.MAMUN on 4/11/2020.
 */
@Transactional
public interface BlogCommentRepository extends CrudRepository<BlogComment, Long>{

    @Query("select c from BlogComment c where c.blogId=?1 order by c.id desc ")
    Page<BlogComment> findPageAbleBlogComment(long blogId, Pageable pageable);

    //@Query("select count(c) from BlogComment c where c.blogId=?1")
    long countBlogCommentByBlogId(long blogId);

    void deleteBlogCommentsByBlogId(long blogId);
}
