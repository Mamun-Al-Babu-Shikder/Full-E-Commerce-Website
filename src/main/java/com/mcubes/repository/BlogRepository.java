package com.mcubes.repository;

import com.mcubes.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by A.A.MAMUN on 4/11/2020.
 */
@Transactional
public interface BlogRepository extends CrudRepository<Blog, Long> {

    Blog findBlogById(long id);

    @Query("select b from Blog b order by b.id desc")
    Page<Blog> findPageAbleBlog(Pageable pageable);

    @Modifying
    @Query("update Blog b set b.viewed=?1 where b.id=?2")
    void updateBlogView(long viewCount, long blogId);
}
