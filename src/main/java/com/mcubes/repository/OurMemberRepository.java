package com.mcubes.repository;

import com.mcubes.entity.OurMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by A.A.MAMUN on 4/8/2020.
 */
@Transactional
public interface OurMemberRepository extends CrudRepository<OurMember,Long>{

}
