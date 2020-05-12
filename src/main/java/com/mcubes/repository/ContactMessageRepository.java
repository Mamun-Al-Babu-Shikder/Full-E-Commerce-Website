package com.mcubes.repository;

import com.mcubes.entity.ContactMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by A.A.MAMUN on 4/5/2020.
 */
@Transactional
public interface ContactMessageRepository extends CrudRepository<ContactMessage, Long> {

    @Query("select cm from ContactMessage cm  where cm.status like ?1% order by cm.id desc")
    Page<ContactMessage> findPageableContactMessage(String status, Pageable pageable);

    @Modifying
    @Query("update ContactMessage cm set cm.status=?1 where cm.id=?2")
    void updateContactMessageStatus(String status, long id);

}
